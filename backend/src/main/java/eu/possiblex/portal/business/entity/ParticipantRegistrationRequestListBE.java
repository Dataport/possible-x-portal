package eu.possiblex.portal.business.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantRegistrationRequestListBE {
    List<ParticipantRegistrationRequestBE> registrationRequests;

    long totalNumberOfRegistrationRequests;
}
