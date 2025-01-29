package eu.possiblex.portal.application.boundary;

import eu.possiblex.portal.application.entity.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/registration")
public interface ParticipantRegistrationRestApi {
    /**
     * POST request for sending a participant registration request, processing it and storing it for later use.
     *
     * @param request participant registration request
     */
    @PostMapping(value = "/request", produces = MediaType.APPLICATION_JSON_VALUE)
    void registerParticipant(@RequestBody CreateRegistrationRequestTO request);

    /**
     * GET request for retrieving all registration requests.
     *
     * @return list of registration requests
     */
    @GetMapping(value = "/request", produces = MediaType.APPLICATION_JSON_VALUE)
    GetRegistrationRequestsResponseTO getRegistrationRequests(
        @RequestParam(value = "page", defaultValue = "0") int pageNumber,
        @RequestParam(value = "size", defaultValue = "10") int pageSize,
        @RequestParam(value = "sortField", required = false) SortField sortField,
        @RequestParam(value = "sortOrder", required = false) SortOrder sortOrder);

    /**
     * GET request for retrieving a specific registration requests by did.
     *
     * @param did DID
     * @return registration request
     */
    @GetMapping(value = "/request/{did}", produces = MediaType.APPLICATION_JSON_VALUE)
    RegistrationRequestEntryTO getRegistrationRequestByDid(@PathVariable String did);

    /**
     * POST request for accepting a registration request.
     *
     * @param id registration request id
     */
    @PostMapping(value = "/request/{id}/accept", produces = MediaType.APPLICATION_JSON_VALUE)
    void acceptRegistrationRequest(@PathVariable String id);

    /**
     * POST request for rejecting a registration request.
     *
     * @param id registration request id
     */
    @PostMapping(value = "/request/{id}/reject", produces = MediaType.APPLICATION_JSON_VALUE)
    void rejectRegistrationRequest(@PathVariable String id);

    /**
     * DELETE request for deleting a registration request.
     *
     * @param id registration request id
     */
    @DeleteMapping(value = "/request/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    void deleteRegistrationRequest(@PathVariable String id);

}