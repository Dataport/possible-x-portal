package eu.possiblex.portal.persistence.dao;


import eu.possiblex.portal.business.entity.credentials.px.PxExtendedLegalParticipantCredentialSubject;


import java.util.List;

public class ParticipantRegistrationRequestDAOMock implements ParticipantRegistrationRequestDAO {

    @Override
    public List<PxExtendedLegalParticipantCredentialSubject> getAllParticipantRegistrationRequests() {

        return List.of();
    }

    @Override
    public void saveParticipantRegistrationRequest(PxExtendedLegalParticipantCredentialSubject request) {

    }

    @Override
    public void acceptRegistrationRequest(String id) {
        // request worked
    }

    @Override
    public void rejectRegistrationRequest(String id) {
        // request worked
    }

    @Override
    public void deleteRegistrationRequest(String id) {
        // request worked
    }
}
