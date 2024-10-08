package eu.possiblex.portal.persistence.dao;

import eu.possiblex.portal.business.entity.ParticipantRegistrationRequestBE;
import eu.possiblex.portal.business.entity.credentials.px.PxExtendedLegalParticipantCredentialSubject;
import eu.possiblex.portal.business.entity.did.ParticipantDidBE;

import java.util.List;

public interface ParticipantRegistrationRequestDAO {
    /**
     * Save a participant registration request.
     *
     * @param request registration request
     */
    void saveParticipantRegistrationRequest(PxExtendedLegalParticipantCredentialSubject request);

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

    /**
     * Given a registration request id, complete the registration request.
     *
     * @param id registration request id
     */
    void completeRegistrationRequest(String id);

    /**
     * Given an existing registration request, store the corresponding DID data.
     *
     * @param id registration request id
     * @param to DID data
     */
    void storeRegistrationRequestDid(String id, ParticipantDidBE to);

    /**
     * Get a list of all registration requests.
     *
     * @return list of registration requests
     */
    List<ParticipantRegistrationRequestBE> getAllParticipantRegistrationRequests();

}
