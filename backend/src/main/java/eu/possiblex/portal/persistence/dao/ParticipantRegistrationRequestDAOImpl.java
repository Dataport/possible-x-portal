package eu.possiblex.portal.persistence.dao;

import eu.possiblex.portal.business.entity.PossibleParticipantBE;
import eu.possiblex.portal.persistence.control.ParticipantRegistrationEntityMapper;
import eu.possiblex.portal.persistence.entity.ParticipantRegistrationRequestEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
@Slf4j
public class ParticipantRegistrationRequestDAOImpl implements ParticipantRegistrationRequestDAO {
    private final ParticipantRegistrationRequestRepository participantRegistrationRequestRepository;

    private final ParticipantRegistrationEntityMapper participantRegistrationEntityMapper;

    public ParticipantRegistrationRequestDAOImpl(
        @Autowired ParticipantRegistrationRequestRepository participantRegistrationRequestRepository,
        @Autowired ParticipantRegistrationEntityMapper participantRegistrationEntityMapper) {

        this.participantRegistrationRequestRepository = participantRegistrationRequestRepository;
        this.participantRegistrationEntityMapper = participantRegistrationEntityMapper;
    }

    @Transactional
    public void saveParticipantRegistrationRequest(PossibleParticipantBE request) {

        ParticipantRegistrationRequestEntity entity = participantRegistrationEntityMapper.possibleParticipantBEToEntity(
            request);

        log.info("Saving participant registration request: {}", entity);

        participantRegistrationRequestRepository.save(entity);
    }

    @Transactional
    public List<PossibleParticipantBE> getAllParticipantRegistrationRequests() {

        log.info("Getting all participant registration requests");
        return participantRegistrationRequestRepository.findAll().stream().map(
            participantRegistrationEntityMapper::entityToPossibleParticipantBE).toList();
    }

    @Transactional
    public void acceptRegistrationRequest(String id) {
        log.info("Accepting participant registration request: {}", id);
        ParticipantRegistrationRequestEntity entity = participantRegistrationRequestRepository.findByName(id);
        if (entity != null) {
            entity.setStatus("Accepted");
            participantRegistrationRequestRepository.save(entity);
        } else {
            log.error("(Accept) Participant not found: {}", id);
            throw new RuntimeException("Participant not found: " + id);
        }
    }

    @Transactional
    public void rejectRegistrationRequest(String id) {
        log.info("Rejecting participant registration request: {}", id);
        ParticipantRegistrationRequestEntity entity = participantRegistrationRequestRepository.findByName(id);
        if (entity != null) {
            entity.setStatus("Rejected");
            participantRegistrationRequestRepository.save(entity);
        } else {
            log.error("(Reject) Participant not found: {}", id);
            throw new RuntimeException("Participant not found: " + id);
        }
    }

    @Transactional
    public void deleteRegistrationRequest(String id) {
        log.info("Deleting participant registration request: {}", id);
        ParticipantRegistrationRequestEntity entity = participantRegistrationRequestRepository.findByName(id);
        if (entity != null) {
            participantRegistrationRequestRepository.delete(entity);
        } else {
            log.error("(Delete) Participant not found: {}", id);
            throw new RuntimeException("Participant not found: " + id);
        }
    }
}
