package eu.possiblex.portal.business.control;

import eu.possiblex.portal.application.entity.RegistrationRequestItemTO;
import eu.possiblex.portal.business.entity.PossibleParticipantBE;

import java.util.List;

public class ParticipantRegistrationServiceMock implements ParticipantRegistrationService {
    @Override
    public void registerParticipant(PossibleParticipantBE be) {
        // request worked
    }

    @Override
    public List<RegistrationRequestItemTO> getAllParticipantRegistrationRequests() {

        return List.of();
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
