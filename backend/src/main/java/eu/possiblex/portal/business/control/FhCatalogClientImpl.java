package eu.possiblex.portal.business.control;

import com.apicatalog.jsonld.JsonLd;
import com.apicatalog.jsonld.JsonLdError;
import com.apicatalog.jsonld.document.JsonDocument;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.possiblex.portal.business.entity.credentials.px.PxExtendedLegalParticipantCredentialSubject;
import eu.possiblex.portal.business.entity.exception.ParticipantNotFoundException;
import eu.possiblex.portal.business.entity.fh.FhCatalogIdResponse;
import eu.possiblex.portal.utilities.LogUtils;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.io.StringReader;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class FhCatalogClientImpl implements FhCatalogClient {

    private final TechnicalFhCatalogClient technicalFhCatalogClient;

    private final ObjectMapper objectMapper;

    public FhCatalogClientImpl(@Autowired TechnicalFhCatalogClient technicalFhCatalogClient,
        @Autowired ObjectMapper objectMapper) {

        this.technicalFhCatalogClient = technicalFhCatalogClient;
        this.objectMapper = objectMapper;
    }

    private static JsonDocument getFrameByType(List<String> type, Map<String, String> context) {

        JsonObjectBuilder contextBuilder = Json.createObjectBuilder();
        context.forEach(contextBuilder::add);

        JsonArrayBuilder typeArrayBuilder = Json.createArrayBuilder();
        type.forEach(typeArrayBuilder::add);

        return JsonDocument.of(
            Json.createObjectBuilder().add("@context", contextBuilder.build()).add("@type", typeArrayBuilder.build())
                .build());
    }

    @Override
    public FhCatalogIdResponse addParticipantToCatalog(PxExtendedLegalParticipantCredentialSubject cs) {

        log.info("sending to catalog: {}", LogUtils.serializeObjectToJson(cs));

        FhCatalogIdResponse catalogParticipantId;
        try {
            catalogParticipantId = technicalFhCatalogClient.addParticipantToFhCatalog(cs);
        } catch (Exception e) {
            log.error("error when trying to send participant to catalog!", e);
            throw e;
        }
        log.info("got participant id: {}", catalogParticipantId.getId());
        return catalogParticipantId;
    }

    @Override
    public PxExtendedLegalParticipantCredentialSubject getParticipantFromCatalog(String participantId)
        throws ParticipantNotFoundException {

        log.info("fetching participant for fh catalog ID " + participantId);
        String offerJsonContent;
        try {
            offerJsonContent = technicalFhCatalogClient.getParticipantFromCatalog(participantId);
        } catch (WebClientResponseException e) {
            if (e.getStatusCode().value() == 404) {
                throw new ParticipantNotFoundException("no FH Catalog participant found with ID " + participantId);
            }
            throw e;
        }
        log.info("answer for fh catalog ID: " + offerJsonContent);

        try {
            JsonDocument input = JsonDocument.of(new StringReader(offerJsonContent));
            JsonDocument offeringFrame = getFrameByType(PxExtendedLegalParticipantCredentialSubject.TYPE,
                PxExtendedLegalParticipantCredentialSubject.CONTEXT);
            JsonObject framedOffering = JsonLd.frame(input, offeringFrame).get();

            return objectMapper.readValue(framedOffering.toString(), PxExtendedLegalParticipantCredentialSubject.class);
        } catch (JsonLdError | JsonProcessingException e) {
            throw new RuntimeException("failed to parse fh catalog participant json: " + offerJsonContent, e);
        }
    }
}
