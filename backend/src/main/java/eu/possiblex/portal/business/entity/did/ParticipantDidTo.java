package eu.possiblex.portal.business.entity.did;

import lombok.Data;

@Data
public class ParticipantDidTo {
    private String did;

    private String verificationMethod;
}
