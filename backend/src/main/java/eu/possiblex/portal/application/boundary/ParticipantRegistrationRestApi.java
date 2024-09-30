package eu.possiblex.portal.application.boundary;

import eu.possiblex.portal.application.entity.RegistrationRequestListTO;
import eu.possiblex.portal.application.entity.RegistrationRequestTO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/registration")
public interface ParticipantRegistrationRestApi {
    @PostMapping(value = "/request", produces = MediaType.APPLICATION_JSON_VALUE)
    void registerParticipant(@RequestBody RegistrationRequestTO request);

    /**
     * Get all registration requests
     * @return list of registration requests
     */
    @GetMapping(value = "/request", produces = MediaType.APPLICATION_JSON_VALUE)
    List<RegistrationRequestListTO> getAllRegistrationRequests();

    @PostMapping(value = "/request/{id}/accept", produces = MediaType.APPLICATION_JSON_VALUE)
    void acceptRegistrationRequest(@PathVariable String id);

    @PostMapping(value = "/request/{id}/reject", produces = MediaType.APPLICATION_JSON_VALUE)
    void rejectRegistrationRequest(@PathVariable String id);

    @DeleteMapping(value = "/request/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    void deleteRegistrationRequest(@PathVariable String id);

}