package eu.possiblex.portal.persistence.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.possiblex.portal.business.entity.PossibleParticipantBE;
import eu.possiblex.portal.persistence.entity.ParticipantRegistrationRequestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
public class ParticipantRegistrationRequestDAOImpl implements ParticipantRegistrationRequestDAO {
    private final ParticipantRegistrationRequestRepository participantRegistrationRequestRepository;

    private final ObjectMapper objectMapper;

    public ParticipantRegistrationRequestDAOImpl(
        @Autowired ParticipantRegistrationRequestRepository participantRegistrationRequestRepository,
        @Autowired ObjectMapper objectMapper) {

        this.participantRegistrationRequestRepository = participantRegistrationRequestRepository;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public void saveParticipantRegistrationRequest(PossibleParticipantBE request) {

        String jsonPayload;
        try {
            jsonPayload = objectMapper.writeValueAsString(request);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize request", e);
        }

        ParticipantRegistrationRequestEntity entity = new ParticipantRegistrationRequestEntity();
        entity.setRegistrationPayload(jsonPayload);

        participantRegistrationRequestRepository.save(entity);
    }
}
