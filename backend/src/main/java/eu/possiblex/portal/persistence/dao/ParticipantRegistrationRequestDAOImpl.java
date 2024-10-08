package eu.possiblex.portal.persistence.dao;

import eu.possiblex.portal.business.entity.ParticipantRegistrationRequestBE;
import eu.possiblex.portal.business.entity.credentials.px.PxExtendedLegalParticipantCredentialSubject;
import eu.possiblex.portal.business.entity.did.ParticipantDidBE;
import eu.possiblex.portal.persistence.control.ParticipantRegistrationEntityMapper;
import eu.possiblex.portal.persistence.entity.DidDataEntity;
import eu.possiblex.portal.persistence.entity.ParticipantRegistrationRequestEntity;
import eu.possiblex.portal.persistence.entity.RequestStatus;
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
    @Override
    public void saveParticipantRegistrationRequest(PxExtendedLegalParticipantCredentialSubject request) {

        ParticipantRegistrationRequestEntity entity = participantRegistrationEntityMapper.pxExtendedLegalParticipantCsToEntity(
            request);
        entity.setStatus(RequestStatus.NEW);
        log.info("Saving participant registration request: {}", entity);

        participantRegistrationRequestRepository.save(entity);
    }

    @Override
    public List<ParticipantRegistrationRequestBE> getAllParticipantRegistrationRequests() {

        log.info("Getting all participant registration requests");
        return participantRegistrationRequestRepository.findAll().stream()
            .map(participantRegistrationEntityMapper::entityToParticipantRegistrationRequestBe).toList();
    }

    @Transactional
    @Override
    public void acceptRegistrationRequest(String id) {

        log.info("Accepting participant registration request: {}", id);
        ParticipantRegistrationRequestEntity entity = participantRegistrationRequestRepository.findByName(id);
        if (entity != null) {
            if (entity.getStatus() == RequestStatus.COMPLETED) {
                log.error("Cannot accept completed participant registration request: {}", id);
                throw new RuntimeException("Cannot accept completed participant registration request: " + id);
            } else {
                entity.setStatus(RequestStatus.ACCEPTED);
            }
        } else {
            log.error("(Accept) Participant not found: {}", id);
            throw new RuntimeException("Participant not found: " + id);
        }
    }

    @Transactional
    @Override
    public void rejectRegistrationRequest(String id) {

        log.info("Rejecting participant registration request: {}", id);
        ParticipantRegistrationRequestEntity entity = participantRegistrationRequestRepository.findByName(id);
        if (entity != null) {
            if (entity.getStatus() == RequestStatus.COMPLETED) {
                log.error("Cannot reject completed participant registration request: {}", id);
                throw new RuntimeException("Cannot reject completed participant registration request: " + id);
            } else {
                entity.setStatus(RequestStatus.REJECTED);
            }
        } else {
            log.error("(Reject) Participant not found: {}", id);
            throw new RuntimeException("Participant not found: " + id);
        }
    }

    @Transactional
    @Override
    public void deleteRegistrationRequest(String id) {

        log.info("Deleting participant registration request: {}", id);
        ParticipantRegistrationRequestEntity entity = participantRegistrationRequestRepository.findByName(id);
        if (entity != null) {
            if (entity.getStatus() == RequestStatus.COMPLETED) {
                log.error("Cannot delete completed participant registration request: {}", id);
                throw new RuntimeException("Cannot delete completed participant registration request: " + id);
            } else {
                participantRegistrationRequestRepository.delete(entity);
            }
        } else {
            log.error("(Delete) Participant not found: {}", id);
            throw new RuntimeException("Participant not found: " + id);
        }
    }

    @Transactional
    @Override
    public void completeRegistrationRequest(String id) {

        log.info("Completing participant registration request: {}", id);
        ParticipantRegistrationRequestEntity entity = participantRegistrationRequestRepository.findByName(id);
        if (entity != null) {
            entity.setStatus(RequestStatus.COMPLETED);
        } else {
            log.error("(Complete) Participant not found: {}", id);
            throw new RuntimeException("Participant not found: " + id);
        }
    }

    @Transactional
    @Override
    public void storeRegistrationRequestDid(String id, ParticipantDidBE to) {

        ParticipantRegistrationRequestEntity entity = participantRegistrationRequestRepository.findByName(id);
        if (entity != null) {
            DidDataEntity didData = new DidDataEntity();
            didData.setDid(to.getDid());
            didData.setVerificationMethod(to.getVerificationMethod());
            entity.setDidData(didData);
        } else {
            log.error("(Set Did) Participant not found: {}", id);
            throw new RuntimeException("Participant not found: " + id);
        }
    }
}
