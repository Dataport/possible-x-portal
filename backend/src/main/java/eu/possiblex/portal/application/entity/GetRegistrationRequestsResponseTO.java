package eu.possiblex.portal.application.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetRegistrationRequestsResponseTO {
    List<RegistrationRequestEntryTO> registrationRequests;

    long totalNumberOfRegistrationRequests;
}
