package eu.possiblex.portal.persistence.dao;

import eu.possiblex.portal.application.entity.credentials.gx.datatypes.GxVcard;
import eu.possiblex.portal.application.entity.credentials.gx.participants.GxLegalRegistrationNumberCredentialSubject;
import eu.possiblex.portal.business.entity.credentials.px.PxExtendedLegalParticipantCredentialSubject;
import eu.possiblex.portal.persistence.control.ParticipantRegistrationEntityMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@Transactional
class ParticipantRegistrationRequestDAOTest {
    @SpyBean
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

        participantRegistrationRequestDAO.saveParticipantRegistrationRequest(participant);
        participantRegistrationRequestDAO.acceptRegistrationRequest("validName");
        verify(participantRegistrationRequestRepository, times(2)).save(any());
    }

    @Test
    void rejectAndDeleteRegistrationRequest() {

        PxExtendedLegalParticipantCredentialSubject participant = getParticipant();

        participantRegistrationRequestDAO.saveParticipantRegistrationRequest(participant);
        participantRegistrationRequestDAO.rejectRegistrationRequest("validName");
        participantRegistrationRequestDAO.deleteRegistrationRequest("validName");
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

        return PxExtendedLegalParticipantCredentialSubject.builder().id("validId").legalRegistrationNumber(
                new GxLegalRegistrationNumberCredentialSubject("validEori", "validVatId", "validLeiCode"))
            .headquarterAddress(vcard).name("validName").description("validDescription").build();
    }
}
