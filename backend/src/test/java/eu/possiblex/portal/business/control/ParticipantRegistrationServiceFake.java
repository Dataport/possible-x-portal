package eu.possiblex.portal.business.control;

import eu.possiblex.portal.application.entity.GetRegistrationRequestsResponseTO;
import eu.possiblex.portal.application.entity.RegistrationRequestEntryTO;
import eu.possiblex.portal.business.entity.credentials.px.PxExtendedLegalParticipantCredentialSubject;

public class ParticipantRegistrationServiceFake implements ParticipantRegistrationService {
    @Override
    public void registerParticipant(PxExtendedLegalParticipantCredentialSubject cs) {
        // request worked
    }

    @Override
    public GetRegistrationRequestsResponseTO getParticipantRegistrationRequests(int pageNumber, int pageSize,
        String sortField, String sortOrder) {

        return new GetRegistrationRequestsResponseTO();
    }

    @Override
    public RegistrationRequestEntryTO getParticipantRegistrationRequestByDid(String did) {

        return null;
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
