package eu.possiblex.portal.business.control;

import eu.possiblex.portal.application.entity.RegistrationRequestListTO;
import eu.possiblex.portal.business.entity.PxExtendedLegalparticipantCredentialSubject;

import java.util.List;

public class ParticipantRegistrationServiceMock implements ParticipantRegistrationService {
    @Override
    public void registerParticipant(PxExtendedLegalparticipantCredentialSubject be) {
        // request worked
    }

    @Override
    public List<RegistrationRequestListTO> getAllParticipantRegistrationRequests() {

        return List.of();
    }
}
