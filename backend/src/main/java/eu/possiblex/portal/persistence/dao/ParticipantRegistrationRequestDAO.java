package eu.possiblex.portal.persistence.dao;

import eu.possiblex.portal.business.entity.PxExtendedLegalparticipantCredentialSubject;

import java.util.List;

public interface ParticipantRegistrationRequestDAO {
    void saveParticipantRegistrationRequest(PxExtendedLegalparticipantCredentialSubject request);

    List<PxExtendedLegalparticipantCredentialSubject> getAllParticipantRegistrationRequests();
}
