package eu.possiblex.portal.business.control;

import eu.possiblex.portal.business.entity.PossibleParticipantBE;

public interface ParticipantRegistrationService {

    /**
     * Given a registration request BE, process and store it for later use.
     *
     * @param be request BE
     */
    void registerParticipant(PossibleParticipantBE be);
}
