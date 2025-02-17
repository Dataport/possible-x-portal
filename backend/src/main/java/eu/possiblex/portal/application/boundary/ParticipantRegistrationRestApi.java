/*
 *  Copyright 2024-2025 Dataport. All rights reserved. Developed as part of the POSSIBLE project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package eu.possiblex.portal.application.boundary;

import eu.possiblex.portal.application.entity.CreateRegistrationRequestTO;
import eu.possiblex.portal.application.entity.RegistrationRequestEntryTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    void registerParticipant(@Valid @RequestBody CreateRegistrationRequestTO request);

    /**
     * GET request for retrieving registration requests for the given pagination request.
     *
     * @return TO with list of registration requests
     */
    @GetMapping(value = "/request", produces = MediaType.APPLICATION_JSON_VALUE)
    Page<RegistrationRequestEntryTO> getRegistrationRequests(
        @PageableDefault(sort = { "name" }) Pageable paginationRequest);

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