package eu.possiblex.portal.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "participant_registration_request")
public class ParticipantRegistrationRequestEntity {
    @Id
    private Long id;

    @Column(name = "registration_payload", columnDefinition = "json")
    private String registrationPayload;

}
