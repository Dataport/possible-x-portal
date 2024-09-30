package eu.possiblex.portal.business.control;

import eu.possiblex.portal.application.entity.RegistrationRequestListTO;
import eu.possiblex.portal.business.entity.credentials.px.PxExtendedLegalParticipantCredentialSubject;

import java.util.List;

public interface ParticipantRegistrationService {

    void registerParticipant(PxExtendedLegalParticipantCredentialSubject be);

    /**
     * Get all registration requests
     *
     * @return list of registration requests
     */
    List<RegistrationRequestListTO> getAllParticipantRegistrationRequests();
}
