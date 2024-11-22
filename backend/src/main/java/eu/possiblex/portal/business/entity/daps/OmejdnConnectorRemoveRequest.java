package eu.possiblex.portal.business.entity.daps;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OmejdnConnectorRemoveRequest {
    @JsonProperty("client_name")
    private String client_name;
}
