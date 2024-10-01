package eu.possiblex.portal.business.control;

import eu.possiblex.portal.application.entity.RegistrationRequestWithStatusTO;
import eu.possiblex.portal.business.entity.credentials.px.PxExtendedLegalParticipantCredentialSubject;
import eu.possiblex.portal.persistence.dao.ParticipantRegistrationRequestDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ParticipantRegistrationServiceImpl implements ParticipantRegistrationService {

    private final ParticipantRegistrationRequestDAO participantRegistrationRequestDAO;

    private final ParticipantRegistrationServiceMapper participantRegistrationServiceMapper;

    public ParticipantRegistrationServiceImpl(
        @Autowired ParticipantRegistrationRequestDAO participantRegistrationRequestDAO,
        @Autowired ParticipantRegistrationServiceMapper participantRegistrationServiceMapper) {

        this.participantRegistrationRequestDAO = participantRegistrationRequestDAO;
        this.participantRegistrationServiceMapper = participantRegistrationServiceMapper;
    }

    /**
     * Given a registration request BE, process and store it for later use.
     *
     * @param cs request
     */
    @Override
    public void registerParticipant(PxExtendedLegalParticipantCredentialSubject cs) {

        log.info("Processing participant registration: {}", cs);

        participantRegistrationRequestDAO.saveParticipantRegistrationRequest(cs);
    }

    @Override
    public List<RegistrationRequestWithStatusTO> getAllParticipantRegistrationRequests() {

        log.info("Processing retrieval of all participant registration requests");

        return participantRegistrationRequestDAO.getAllParticipantRegistrationRequests().stream()
            .map(participantRegistrationServiceMapper::participantRegistrationRequestBEToRegistrationRequestWithStatusTO)
            .toList();
    }

    @Override
    public void acceptRegistrationRequest(String id) {

        log.info("Processing acceptance of participant: {}", id);

        participantRegistrationRequestDAO.acceptRegistrationRequest(id);
    }

    @Override
    public void rejectRegistrationRequest(String id) {

        log.info("Processing rejection of participant: {}", id);

        participantRegistrationRequestDAO.rejectRegistrationRequest(id);
    }

    @Override
    public void deleteRegistrationRequest(String id) {

        log.info("Processing deletion of participant: {}", id);

        participantRegistrationRequestDAO.deleteRegistrationRequest(id);
    }
}
