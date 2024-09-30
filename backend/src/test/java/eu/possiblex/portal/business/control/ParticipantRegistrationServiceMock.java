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
}
