package eu.possiblex.portal.business.entity.credentials.px;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import eu.possiblex.portal.application.entity.credentials.serialization.StringDeserializer;
import eu.possiblex.portal.application.entity.credentials.serialization.StringSerializer;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true, value = { "type", "@type", "@context" }, allowGetters = true)
@NoArgsConstructor
@AllArgsConstructor
public class CatalogGxLegalRegistrationNumberCredentialSubject {
    @Getter(AccessLevel.NONE)
    public static final String TYPE_NAMESPACE = "gx";

    @Getter(AccessLevel.NONE)
    public static final String TYPE_CLASS = "legalRegistrationNumber";

    @Getter(AccessLevel.NONE)
    public static final String TYPE = TYPE_NAMESPACE + ":" + TYPE_CLASS;

    @Getter(AccessLevel.NONE)
    public static final Map<String, String> CONTEXT = Map.of(TYPE_NAMESPACE,
        "https://registry.lab.gaia-x.eu/development/api/trusted-shape-registry/v1/shapes/jsonld/trustframework#", "xsd",
        "http://www.w3.org/2001/XMLSchema#");

    private String id;

    @JsonProperty("gx:EORI")
    @JsonSerialize(using = StringSerializer.class)
    @JsonDeserialize(using = StringDeserializer.class)
    private String eori;

    @JsonProperty("gx:vatID")
    @JsonSerialize(using = StringSerializer.class)
    @JsonDeserialize(using = StringDeserializer.class)
    private String vatID;

    @JsonProperty("gx:leiCode")
    @JsonSerialize(using = StringSerializer.class)
    @JsonDeserialize(using = StringDeserializer.class)
    private String leiCode;

    @JsonProperty("type")
    public String getType() {

        return TYPE;
    }

    @JsonProperty("@context")
    public Map<String, String> getContext() {

        return CONTEXT;
    }
}
