package eu.possiblex.portal.application.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnvironmentTO {
    @Schema(description = "URL of the catalog UI ", example = "https://possible.fokus.fraunhofer.de/")
    private String catalogUiUrl;
}
