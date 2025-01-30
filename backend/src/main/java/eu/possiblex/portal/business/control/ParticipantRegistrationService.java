package eu.possiblex.portal.business.control;

import eu.possiblex.portal.application.entity.GetRegistrationRequestsResponseTO;
import eu.possiblex.portal.application.entity.RegistrationRequestEntryTO;
import eu.possiblex.portal.application.entity.SortField;
import eu.possiblex.portal.application.entity.SortOrder;
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
     * Get registration requests for a given page number and page size sorted by sort field and sort order.
     *
     * @return TO with list of registration requests
     */
    GetRegistrationRequestsResponseTO getParticipantRegistrationRequests(int pageNumber, int pageSize, SortField sortField, SortOrder sortOrder);

    /**
     * Get a registration request by did
     *
     * @param did DID
     * @return registration request
     */
    RegistrationRequestEntryTO getParticipantRegistrationRequestByDid(String did);

    /**
     * Given a registration request id, accept the registration request.
     *
     * @param id registration request id
     */
    void acceptRegistrationRequest(String id);

    /**
     * Given a registration request id, reject the registration request.
     *
     * @param id registration request id
     */
    void rejectRegistrationRequest(String id);

    /**
     * Given a registration request id, delete the registration request.
     *
     * @param id registration request id
     */
    void deleteRegistrationRequest(String id);
}
