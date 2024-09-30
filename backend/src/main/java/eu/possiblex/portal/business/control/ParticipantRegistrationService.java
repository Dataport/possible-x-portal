package eu.possiblex.portal.business.control;

import eu.possiblex.portal.application.entity.RegistrationRequestItemTO;
import eu.possiblex.portal.business.entity.PossibleParticipantBE;

import java.util.List;

public interface ParticipantRegistrationService {

    void registerParticipant(PossibleParticipantBE be);

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
