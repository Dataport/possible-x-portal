package eu.possiblex.portal.business.control;

import eu.possiblex.portal.application.entity.RegistrationRequestItemTO;
import eu.possiblex.portal.application.entity.credentials.gx.datatypes.GxVcard;
import eu.possiblex.portal.application.entity.credentials.gx.participants.GxLegalRegistrationNumberCredentialSubject;
import eu.possiblex.portal.business.entity.credentials.px.PxExtendedLegalParticipantCredentialSubject;
import eu.possiblex.portal.persistence.control.ParticipantRegistrationEntityMapper;
import eu.possiblex.portal.persistence.dao.ParticipantRegistrationRequestDAO;
import eu.possiblex.portal.persistence.dao.ParticipantRegistrationRequestDAOImpl;
import eu.possiblex.portal.persistence.dao.ParticipantRegistrationRequestRepository;
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
    @MockBean
    private ParticipantRegistrationRequestDAO participantRegistrationRequestDao;

    @Autowired
    private ParticipantRegistrationService participantRegistrationService;

    @Test
    void registerParticipant() {
        PxExtendedLegalParticipantCredentialSubject participant = getParticipant();
        participantRegistrationService.registerParticipant(participant);
        verify(participantRegistrationRequestDao).saveParticipantRegistrationRequest(any());
    }

    @Test
    void getAllParticipantRegistrationRequests() {
        PxExtendedLegalParticipantCredentialSubject participant = getParticipant();

        when(participantRegistrationRequestDao.getAllParticipantRegistrationRequests()).thenReturn(List.of(participant));
        List<RegistrationRequestItemTO> list = participantRegistrationService.getAllParticipantRegistrationRequests();
        assertEquals(1, list.size());

        verify(participantRegistrationRequestDao).getAllParticipantRegistrationRequests();
    }

    @Test
    void acceptRegistrationRequest() {
        participantRegistrationService.acceptRegistrationRequest("validId");
        verify(participantRegistrationRequestDao).acceptRegistrationRequest("validId");
    }

    @Test
    void rejectRegistrationRequest() {
        participantRegistrationService.rejectRegistrationRequest("validId");
        verify(participantRegistrationRequestDao).rejectRegistrationRequest("validId");
    }

    @Test
    void deleteRegistrationRequest() {
        participantRegistrationService.deleteRegistrationRequest("validId");
        verify(participantRegistrationRequestDao).deleteRegistrationRequest("validId");
    }

    private PxExtendedLegalParticipantCredentialSubject getParticipant() {
        GxVcard vcard = new GxVcard();
        vcard.setCountryCode("validCountryCode");
        vcard.setCountrySubdivisionCode("validSubdivisionCode");
        vcard.setStreetAddress("validStreetAddress");
        vcard.setLocality("validLocality");
        vcard.setPostalCode("validPostalCode");

        return PxExtendedLegalParticipantCredentialSubject.builder().id("validId")
                .legalRegistrationNumber(new GxLegalRegistrationNumberCredentialSubject("validEori", "validVatId", "validLeiCode"))
                .headquarterAddress(vcard)
                .name("validName")
                .description("validDescription")
                .build();
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
