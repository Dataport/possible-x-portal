package eu.possiblex.portal.persistence.dao;

import eu.possiblex.portal.business.entity.ParticipantRegistrationRequestBE;
import eu.possiblex.portal.business.entity.credentials.px.PxExtendedLegalParticipantCredentialSubject;
import eu.possiblex.portal.persistence.entity.daps.OmejdnConnectorCertificateEntity;

import java.util.List;

public interface ParticipantRegistrationRequestDAO {
    void saveParticipantRegistrationRequest(PxExtendedLegalParticipantCredentialSubject request);

    void acceptRegistrationRequest(String id);

    void rejectRegistrationRequest(String id);

    void deleteRegistrationRequest(String id);

    void completeRegistrationRequest(String id, OmejdnConnectorCertificateEntity certificate, String vpLink);

    List<ParticipantRegistrationRequestBE> getAllParticipantRegistrationRequests();

}
