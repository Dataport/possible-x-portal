package eu.possiblex.portal.application.boundary;

import eu.possiblex.portal.application.configuration.AppConfigurer;
import eu.possiblex.portal.application.configuration.BoundaryExceptionHandler;
import eu.possiblex.portal.application.control.ParticipantRegistrationRestApiMapper;
import eu.possiblex.portal.application.entity.CreateRegistrationRequestTO;
import eu.possiblex.portal.application.entity.credentials.gx.datatypes.GxVcard;
import eu.possiblex.portal.application.entity.credentials.gx.datatypes.NodeKindIRITypeId;
import eu.possiblex.portal.application.entity.credentials.gx.participants.GxLegalParticipantCredentialSubject;
import eu.possiblex.portal.application.entity.credentials.gx.participants.GxLegalRegistrationNumberCredentialSubject;
import eu.possiblex.portal.application.entity.credentials.px.participants.PxParticipantExtensionCredentialSubject;
import eu.possiblex.portal.business.control.ParticipantRegistrationService;
import eu.possiblex.portal.business.control.ParticipantRegistrationServiceFake;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ParticipantRegistrationRestApiImpl.class)
@ContextConfiguration(classes = { ParticipantRegistrationRestApiTest.TestConfig.class,
    ParticipantRegistrationRestApiImpl.class, BoundaryExceptionHandler.class, AppConfigurer.class })
@AutoConfigureMockMvc
class ParticipantRegistrationRestApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ParticipantRegistrationService participantRegistrationService;

    @Test
    void registerParticipant() throws Exception {

        CreateRegistrationRequestTO to = buildValidRegistrationRequest();

        reset(participantRegistrationService);
        this.mockMvc.perform(post("/registration/request").content(RestApiHelper.asJsonString(to))
            .contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());

        verify(participantRegistrationService).registerParticipant(any());
    }

    // todo add failing requests

    @WithMockUser(username = "admin")
    @Test
    void getRegistrationRequests() throws Exception {

        reset(participantRegistrationService);
        this.mockMvc.perform(get("/registration/request")).andDo(print()).andExpect(status().isOk());

        verify(participantRegistrationService).getParticipantRegistrationRequests(any());
    }

    @WithMockUser(username = "admin")
    @Test
    void getRegistrationRequestByDid() throws Exception {

        reset(participantRegistrationService);
        this.mockMvc.perform(get("/registration/request/someDid")).andDo(print()).andExpect(status().isOk());

        verify(participantRegistrationService).getParticipantRegistrationRequestByDid("someDid");
    }

    @WithMockUser(username = "admin")
    @Test
    void acceptRegistrationRequest() throws Exception {

        reset(participantRegistrationService);
        this.mockMvc.perform(post("/registration/request/validId/accept")).andDo(print()).andExpect(status().isOk());

        verify(participantRegistrationService).acceptRegistrationRequest("validId");
    }

    @WithMockUser(username = "admin")
    @Test
    void rejectRegistrationRequest() throws Exception {

        reset(participantRegistrationService);
        this.mockMvc.perform(post("/registration/request/validId/reject")).andDo(print()).andExpect(status().isOk());

        verify(participantRegistrationService).rejectRegistrationRequest("validId");
    }

    @WithMockUser(username = "admin")
    @Test
    void deleteRegistrationRequest() throws Exception {

        reset(participantRegistrationService);
        this.mockMvc.perform(delete("/registration/request/validId")).andDo(print()).andExpect(status().isOk());

        verify(participantRegistrationService).deleteRegistrationRequest("validId");
    }

    private CreateRegistrationRequestTO buildValidRegistrationRequest() {

        GxVcard address = new GxVcard();
        address.setStreetAddress("Example Street 123");
        address.setLocality("Berlin");
        address.setPostalCode("12345");
        address.setCountryCode("DE");
        address.setCountrySubdivisionCode("DE-BE");
        GxLegalParticipantCredentialSubject participantCs = new GxLegalParticipantCredentialSubject();
        participantCs.setName("name");
        participantCs.setLegalAddress(address);
        participantCs.setLegalRegistrationNumber(List.of(new NodeKindIRITypeId("someId")));
        participantCs.setHeadquarterAddress(address);
        GxLegalRegistrationNumberCredentialSubject registrationNumberCs = new GxLegalRegistrationNumberCredentialSubject();
        registrationNumberCs.setLeiCode("894500MQZ65CN32S9A66");
        PxParticipantExtensionCredentialSubject participantExtensionCs = new PxParticipantExtensionCredentialSubject();
        participantExtensionCs.setMailAddress("example@example.com");

        CreateRegistrationRequestTO to = new CreateRegistrationRequestTO();
        to.setParticipantCs(participantCs);
        to.setRegistrationNumberCs(registrationNumberCs);
        to.setParticipantExtensionCs(participantExtensionCs);

        return to;
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        public ParticipantRegistrationService participantRegistrationService() {

            return Mockito.spy(new ParticipantRegistrationServiceFake());
        }

        @Bean
        public ParticipantRegistrationRestApiMapper participantCredentialMapper() {

            return Mappers.getMapper(ParticipantRegistrationRestApiMapper.class);
        }

    }

}
