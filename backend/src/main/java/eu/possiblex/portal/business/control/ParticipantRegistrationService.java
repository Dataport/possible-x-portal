package eu.possiblex.portal.business.control;


import eu.possiblex.portal.application.entity.RegistrationRequestItemTO;
import eu.possiblex.portal.business.entity.credentials.px.PxExtendedLegalParticipantCredentialSubject;


import java.util.List;

public interface ParticipantRegistrationService {
  
    /**
     * Given a registration request, process and store it for later use.
     *
     * @param cs registration request
     */
    void registerParticipant(PxExtendedLegalParticipantCredentialSubject cs);

    /**
     * Get all registration requests
     *
     * @return list of registration requests
     */
    List<RegistrationRequestItemTO> getAllParticipantRegistrationRequests();

    void acceptRegistrationRequest(String id);

    void rejectRegistrationRequest(String id);

    void deleteRegistrationRequest(String id);
}
