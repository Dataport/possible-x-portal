package eu.possiblex.portal.business.control;

import eu.possiblex.portal.application.entity.RegistrationRequestEntryTO;
import eu.possiblex.portal.business.entity.ParticipantMetadataBE;
import eu.possiblex.portal.business.entity.ParticipantRegistrationRequestBE;
import eu.possiblex.portal.business.entity.credentials.px.PxExtendedLegalParticipantCredentialSubject;
import eu.possiblex.portal.business.entity.daps.OmejdnConnectorCertificateBE;
import eu.possiblex.portal.business.entity.daps.OmejdnConnectorCertificateRequest;
import eu.possiblex.portal.business.entity.did.ParticipantDidCreateRequestBE;
import eu.possiblex.portal.business.entity.exception.RegistrationRequestException;
import eu.possiblex.portal.persistence.control.ParticipantRegistrationEntityMapper;
import eu.possiblex.portal.persistence.dao.ParticipantRegistrationRequestDAO;
import eu.possiblex.portal.persistence.dao.ParticipantRegistrationRequestDAOFake;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ContextConfiguration(classes = { ParticipantRegistrationServiceTest.TestConfig.class,
    ParticipantRegistrationServiceImpl.class })
class ParticipantRegistrationServiceTest {
    @Autowired
    private ParticipantRegistrationRequestDAO participantRegistrationRequestDao;

    @Autowired
    private OmejdnConnectorApiClient omejdnConnectorApiClient;

    @Autowired
    private DidWebServiceApiClient didWebServiceApiClient;

    @Autowired
    private ParticipantRegistrationService participantRegistrationService;

    @Test
    void registerParticipant() {

        reset(participantRegistrationRequestDao);
        reset(omejdnConnectorApiClient);
        reset(didWebServiceApiClient);

        PxExtendedLegalParticipantCredentialSubject participant = getParticipantCs();
        ParticipantMetadataBE metadata = getParticipantMetadata();
        participantRegistrationService.registerParticipant(participant, metadata);
        verify(participantRegistrationRequestDao).saveParticipantRegistrationRequest(any(), any());
    }

    @Test
    void registerParticipantAlreadyExists() {

        reset(participantRegistrationRequestDao);
        reset(omejdnConnectorApiClient);
        reset(didWebServiceApiClient);

        PxExtendedLegalParticipantCredentialSubject participant = getParticipantCs();
        participant.setName(ParticipantRegistrationRequestDAOFake.EXISTING_ID);
        ParticipantMetadataBE metadata = getParticipantMetadata();

        assertThrows(RegistrationRequestException.class, () -> {
            participantRegistrationService.registerParticipant(participant, metadata);
        });

        verify(participantRegistrationRequestDao).getParticipantRegistrationRequest(ParticipantRegistrationRequestDAOFake.EXISTING_ID);
        verify(participantRegistrationRequestDao, times(0)).saveParticipantRegistrationRequest(any(), any());
    }

    @Test
    void getAllParticipantRegistrationRequests() {

        reset(participantRegistrationRequestDao);
        reset(omejdnConnectorApiClient);
        reset(didWebServiceApiClient);

        List<RegistrationRequestEntryTO> list = participantRegistrationService.getAllParticipantRegistrationRequests();
        assertEquals(1, list.size());

        verify(participantRegistrationRequestDao).getAllParticipantRegistrationRequests();
    }

    @Test
    void acceptRegistrationRequest() {

        reset(participantRegistrationRequestDao);
        reset(omejdnConnectorApiClient);
        reset(didWebServiceApiClient);

        PxExtendedLegalParticipantCredentialSubject participant = getParticipantCs();
        ParticipantMetadataBE metadata = getParticipantMetadata();
        participantRegistrationService.registerParticipant(participant, metadata);

        ArgumentCaptor<OmejdnConnectorCertificateBE> certificateCaptor = ArgumentCaptor.forClass(OmejdnConnectorCertificateBE.class);
        participantRegistrationService.acceptRegistrationRequest(participant.getName());
        verify(participantRegistrationRequestDao).acceptRegistrationRequest(participant.getName());
        verify(participantRegistrationRequestDao).completeRegistrationRequest(participant.getName());
        verify(participantRegistrationRequestDao).storeRegistrationRequestDaps(any(String.class), certificateCaptor.capture());
        verify(didWebServiceApiClient).generateDidWeb(new ParticipantDidCreateRequestBE(participant.getName()));
        verify(omejdnConnectorApiClient).addConnector(new OmejdnConnectorCertificateRequest(DidWebServiceApiClientFake.EXAMPLE_DID));

        OmejdnConnectorCertificateBE certificate = certificateCaptor.getValue();
        assertEquals(DidWebServiceApiClientFake.EXAMPLE_DID, certificate.getClientName());
        assertNotNull(certificate.getKeystore());
        assertNotNull(certificate.getClientId());
        assertNotNull(certificate.getPassword());        
    }

    @Test
    void rejectRegistrationRequest() {

        reset(participantRegistrationRequestDao);
        reset(omejdnConnectorApiClient);
        reset(didWebServiceApiClient);

        participantRegistrationService.rejectRegistrationRequest("validId");
        verify(participantRegistrationRequestDao).rejectRegistrationRequest("validId");
    }

    @Test
    void deleteRegistrationRequest() {

        reset(participantRegistrationRequestDao);
        reset(omejdnConnectorApiClient);
        reset(didWebServiceApiClient);

        participantRegistrationService.deleteRegistrationRequest("validId");
        verify(participantRegistrationRequestDao).deleteRegistrationRequest("validId");
    }

    private PxExtendedLegalParticipantCredentialSubject getParticipantCs() {

        ParticipantRegistrationRequestBE be = ParticipantRegistrationRequestDAOFake.getExampleParticipant();

        return PxExtendedLegalParticipantCredentialSubject.builder().id("validId")
            .legalRegistrationNumber(be.getLegalRegistrationNumber()).headquarterAddress(be.getHeadquarterAddress())
            .legalAddress(be.getLegalAddress()).name(be.getName()).description(be.getDescription()).build();
    }

    private ParticipantMetadataBE getParticipantMetadata() {
        return ParticipantMetadataBE.builder().emailAddress("example@address.com").build();
    }

    // Test-specific configuration to provide mocks
    @TestConfiguration
    static class TestConfig {
        @Bean
        public ParticipantRegistrationEntityMapper participantRegistrationEntityMapper() {

            return Mappers.getMapper(ParticipantRegistrationEntityMapper.class);
        }

        @Bean
        public ParticipantRegistrationServiceMapper participantRegistrationServiceMapper() {

            return Mappers.getMapper(ParticipantRegistrationServiceMapper.class);
        }

        @Bean
        public OmejdnConnectorApiClient dapsConnectorApiClient() {

            return Mockito.spy(new OmejdnConnectorApiClientFake());
        }

        @Bean
        public DidWebServiceApiClient didWebServiceApiClient() {

            return Mockito.spy(new DidWebServiceApiClientFake());
        }

        @Bean
        public ParticipantRegistrationRequestDAO participantRegistrationRequestDAO() {

            return Mockito.spy(new ParticipantRegistrationRequestDAOFake());
        }
    }
}
