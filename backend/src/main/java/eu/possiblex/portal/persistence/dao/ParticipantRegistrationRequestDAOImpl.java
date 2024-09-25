package eu.possiblex.portal.persistence.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.possiblex.portal.business.entity.PossibleParticipantBE;
import eu.possiblex.portal.persistence.control.ParticipantRegistrationEntityMapper;
import eu.possiblex.portal.persistence.entity.ParticipantRegistrationRequestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
public class ParticipantRegistrationRequestDAOImpl implements ParticipantRegistrationRequestDAO {
    private final ParticipantRegistrationRequestRepository participantRegistrationRequestRepository;

    private final ObjectMapper objectMapper;

    private final ParticipantRegistrationEntityMapper participantRegistrationEntityMapper;

    public ParticipantRegistrationRequestDAOImpl(
        @Autowired ParticipantRegistrationRequestRepository participantRegistrationRequestRepository,
        @Autowired ObjectMapper objectMapper,
        @Autowired ParticipantRegistrationEntityMapper participantRegistrationEntityMapper) {

        this.participantRegistrationRequestRepository = participantRegistrationRequestRepository;
        this.objectMapper = objectMapper;
        this.participantRegistrationEntityMapper = participantRegistrationEntityMapper;
    }

    @Transactional
    public void saveParticipantRegistrationRequest(PossibleParticipantBE request) {

        // TODO map BE to entity
        ParticipantRegistrationRequestEntity entity = new ParticipantRegistrationRequestEntity();
        participantRegistrationRequestRepository.save(entity);
    }
}
