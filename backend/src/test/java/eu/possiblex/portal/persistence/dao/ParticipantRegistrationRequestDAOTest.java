package eu.possiblex.portal.persistence.dao;

import eu.possiblex.portal.application.entity.credentials.gx.datatypes.GxVcard;
import eu.possiblex.portal.application.entity.credentials.gx.participants.GxLegalRegistrationNumberCredentialSubject;
import eu.possiblex.portal.business.entity.credentials.px.PxExtendedLegalParticipantCredentialSubject;
import eu.possiblex.portal.persistence.control.ParticipantRegistrationEntityMapper;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ParticipantRegistrationRequestDAOTest {
    @MockBean
    private ParticipantRegistrationRequestRepository participantRegistrationRequestRepository;

    @Autowired
    private ParticipantRegistrationRequestDAO participantRegistrationRequestDAO;

    @Autowired
    private ParticipantRegistrationEntityMapper participantRegistrationEntityMapper;

    @Test
    void saveParticipantRegistrationRequest() {
        PxExtendedLegalParticipantCredentialSubject participant = getParticipant();
        participantRegistrationRequestDAO.saveParticipantRegistrationRequest(participant);
        verify(participantRegistrationRequestRepository).save(any());
    }

    @Test
    void getAllParticipantRegistrationRequests() {
        participantRegistrationRequestDAO.getAllParticipantRegistrationRequests();
        verify(participantRegistrationRequestRepository).findAll();
    }

    @Test
    void acceptRegistrationRequest() {
        PxExtendedLegalParticipantCredentialSubject participant = getParticipant();

        when(participantRegistrationRequestRepository.findByName("validId")).thenReturn(participantRegistrationEntityMapper.pxExtendedLegalParticipantCsToEntity(participant));
        participantRegistrationRequestDAO.saveParticipantRegistrationRequest(participant);
        participantRegistrationRequestDAO.acceptRegistrationRequest("validId");
        verify(participantRegistrationRequestRepository, times(2)).save(any());
    }

    @Test
    void rejectAndDeleteRegistrationRequest() {
        PxExtendedLegalParticipantCredentialSubject participant = getParticipant();

        when(participantRegistrationRequestRepository.findByName("validId")).thenReturn(participantRegistrationEntityMapper.pxExtendedLegalParticipantCsToEntity(participant));
        participantRegistrationRequestDAO.saveParticipantRegistrationRequest(participant);
        participantRegistrationRequestDAO.rejectRegistrationRequest("validId");
        participantRegistrationRequestDAO.deleteRegistrationRequest("validId");
        verify(participantRegistrationRequestRepository, times(2)).save(any());

        verify(participantRegistrationRequestRepository).delete(any());
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
    }
}
