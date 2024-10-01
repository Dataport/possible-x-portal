package eu.possiblex.portal.business.control;

import eu.possiblex.portal.application.entity.RegistrationRequestItemTO;
import eu.possiblex.portal.application.entity.credentials.gx.datatypes.GxVcard;
import eu.possiblex.portal.application.entity.credentials.gx.participants.GxLegalParticipantCredentialSubject;
import eu.possiblex.portal.application.entity.credentials.gx.participants.GxLegalRegistrationNumberCredentialSubject;
import eu.possiblex.portal.business.entity.PossibleParticipantBE;
import eu.possiblex.portal.persistence.control.ParticipantRegistrationEntityMapper;
import eu.possiblex.portal.persistence.dao.ParticipantRegistrationRequestDAOImpl;
import eu.possiblex.portal.persistence.dao.ParticipantRegistrationRequestRepository;
import eu.possiblex.portal.persistence.entity.ParticipantRegistrationRequestEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ContextConfiguration(classes = { ParticipantRegistrationServiceTest.TestConfig.class,
    ParticipantRegistrationServiceImpl.class, ParticipantRegistrationRequestDAOImpl.class })
class ParticipantRegistrationServiceTest {

    // TODO add test with in-memory database
    @MockBean
    private ParticipantRegistrationRequestRepository participantRegistrationRequestRepository;

    @Autowired
    private ParticipantRegistrationService participantRegistrationService;

    @Autowired
    private ParticipantRegistrationEntityMapper participantRegistrationEntityMapper;

    @Test
    void registerParticipant() {
        GxVcard vcard = new GxVcard();
        vcard.setCountryCode("validCountryCode");
        vcard.setCountrySubdivisionCode("validSubdivisionCode");
        vcard.setStreetAddress("validStreetAddress");
        vcard.setLocality("validLocality");
        vcard.setPostalCode("validPostalCode");

        PossibleParticipantBE participant = PossibleParticipantBE.builder().id("validId")
                .legalRegistrationNumber(new GxLegalRegistrationNumberCredentialSubject("validEori", "validVatId", "validLeiCode"))
                .headquarterAddress(vcard)
                .name("validName")
                .description("validDescription")
                .build();
        participantRegistrationService.registerParticipant(participant);
        RegistrationRequestItemTO registeredParticipant = participantRegistrationService.getAllParticipantRegistrationRequests().get(0);
        assertEquals("validName", registeredParticipant.getName());
        assertEquals("validDescription", registeredParticipant.getDescription());
        assertEquals("NEW", registeredParticipant.getStatus());
    }

    @Test
    void getAllParticipantRegistrationRequests() {
        when(participantRegistrationRequestRepository.findAll()).thenReturn(Collections.emptyList());

        List<RegistrationRequestItemTO> list = participantRegistrationService.getAllParticipantRegistrationRequests();
        assertTrue(list.isEmpty());

        verify(participantRegistrationRequestRepository).findAll();
    }

    // Test-specific configuration to provide mocks
    @TestConfiguration
    static class TestConfig {
        @Bean
        public ParticipantRegistrationEntityMapper participantRegistrationEntityMapper() {

            return Mappers.getMapper(ParticipantRegistrationEntityMapper.class);
        }
        @Bean
        public ParticipantRegistrationServiceMapper participantRegistrationServiceMapper() {

            return Mappers.getMapper(ParticipantRegistrationServiceMapper.class);
        }
    }
}
