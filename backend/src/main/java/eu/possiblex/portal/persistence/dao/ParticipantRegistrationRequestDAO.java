package eu.possiblex.portal.persistence.dao;

import eu.possiblex.portal.business.entity.PxExtendedLegalParticipantCredentialSubject;

import java.util.List;

public interface ParticipantRegistrationRequestDAO {
    void saveParticipantRegistrationRequest(PxExtendedLegalParticipantCredentialSubject request);

    List<PxExtendedLegalParticipantCredentialSubject> getAllParticipantRegistrationRequests();
}
