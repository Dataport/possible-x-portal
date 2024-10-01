package eu.possiblex.portal.business.control;


import eu.possiblex.portal.application.entity.RegistrationRequestWithStatusTO;
import eu.possiblex.portal.business.entity.credentials.px.PxExtendedLegalParticipantCredentialSubject;


import java.util.List;

public class ParticipantRegistrationServiceMock implements ParticipantRegistrationService {
    @Override
    public void registerParticipant(PxExtendedLegalParticipantCredentialSubject be) {
        // request worked
    }

    @Override
    public List<RegistrationRequestWithStatusTO> getAllParticipantRegistrationRequests() {

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
