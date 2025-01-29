package eu.possiblex.portal.application.boundary;

import eu.possiblex.portal.application.control.ParticipantRegistrationRestApiMapper;
import eu.possiblex.portal.application.entity.*;
import eu.possiblex.portal.business.control.ParticipantRegistrationService;
import eu.possiblex.portal.business.entity.credentials.px.PxExtendedLegalParticipantCredentialSubject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ParticipantRegistrationRestApiImpl implements ParticipantRegistrationRestApi {

    private final ParticipantRegistrationService participantRegistrationService;

    private final ParticipantRegistrationRestApiMapper participantRegistrationRestApiMapper;

    public ParticipantRegistrationRestApiImpl(@Autowired ParticipantRegistrationService participantRegistrationService,
        @Autowired ParticipantRegistrationRestApiMapper participantRegistrationRestApiMapper) {

        this.participantRegistrationService = participantRegistrationService;
        this.participantRegistrationRestApiMapper = participantRegistrationRestApiMapper;
    }

    /**
     * Process and store a registration request for a participant.
     *
     * @param request participant registration request
     */
    @Override
    public void registerParticipant(@RequestBody CreateRegistrationRequestTO request) {

        log.info("Received participant registration request: {}", request);
        PxExtendedLegalParticipantCredentialSubject cs = participantRegistrationRestApiMapper.credentialSubjectsToExtendedLegalParticipantCs(
            request);

        participantRegistrationService.registerParticipant(cs);
    }

    /**
     * Get all registration requests.
     *
     * @return list of registration requests
     */
    @Override
    public GetRegistrationRequestsResponseTO getRegistrationRequests(
        @RequestParam(value = "page", defaultValue = "0") int pageNumber,
        @RequestParam(value = "size", defaultValue = "10") int pageSize,
        @RequestParam(value = "sortField", defaultValue = "ORGANIZATION_NAME") String sortField,
        @RequestParam(value = "sortOrder", defaultValue = "ASC") String sortOrder) {

        String sortFieldString = sortField != null ? SortField.fromString(sortField).getValue() : null;
        String sortOrderString = sortOrder != null ? SortOrder.fromString(sortOrder).getValue() : null;

        log.info(
            "Received request to get participant registration requests for page: {} and size: {} with sort field: {} and sort order: {}",
            pageNumber, pageSize, sortField, sortOrder);

        return participantRegistrationService.getParticipantRegistrationRequests(pageNumber, pageSize,
            sortFieldString, sortOrderString);

    }

    @Override
    public RegistrationRequestEntryTO getRegistrationRequestByDid(@PathVariable String did) {

        log.info("Received request to get registration request for did {}", did);

        return participantRegistrationService.getParticipantRegistrationRequestByDid(did);
    }

    /**
     * Accept a registration request.
     *
     * @param id registration request id
     */
    @Override
    public void acceptRegistrationRequest(@PathVariable String id) {

        log.info("Received request to accept participant: {}", id);
        participantRegistrationService.acceptRegistrationRequest(id);
    }

    /**
     * Reject a registration request.
     *
     * @param id registration request id
     */
    @Override
    public void rejectRegistrationRequest(@PathVariable String id) {

        log.info("Received request to reject participant: {}", id);
        participantRegistrationService.rejectRegistrationRequest(id);
    }

    /**
     * Delete a registration request.
     *
     * @param id registration request id
     */
    @Override
    public void deleteRegistrationRequest(@PathVariable String id) {

        log.info("Received request to delete participant: {}", id);
        participantRegistrationService.deleteRegistrationRequest(id);
    }
}