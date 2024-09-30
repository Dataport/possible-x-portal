package eu.possiblex.portal.business.control;

import eu.possiblex.portal.application.entity.RegistrationRequestListTO;
import eu.possiblex.portal.business.entity.PxExtendedLegalparticipantCredentialSubject;

import java.util.List;

public interface ParticipantRegistrationService {

    void registerParticipant(PxExtendedLegalparticipantCredentialSubject be);

    /**
     * Get all registration requests
     *
     * @return list of registration requests
     */
    List<RegistrationRequestListTO> getAllParticipantRegistrationRequests();
}
