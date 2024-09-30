package eu.possiblex.portal.business.control;

import eu.possiblex.portal.application.entity.AddressTO;
import eu.possiblex.portal.application.entity.RegistrationNumberTO;
import eu.possiblex.portal.application.entity.RegistrationRequestItemTO;
import eu.possiblex.portal.application.entity.credentials.gx.datatypes.GxVcard;
import eu.possiblex.portal.application.entity.credentials.gx.participants.GxLegalRegistrationNumberCredentialSubject;
import eu.possiblex.portal.business.entity.PossibleParticipantBE;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ParticipantRegistrationServiceMapper {

    AddressTO gxVcardToAddressTO(GxVcard gxVcard);

    RegistrationNumberTO legalRegistrationNumberToRegistrationNumberTO(
         GxLegalRegistrationNumberCredentialSubject legalRegistrationNumber);

    @Mapping(target = "status", source="status")
    RegistrationRequestItemTO possibleParticipantBEToRegistrationRequestListTO(PossibleParticipantBE possibleParticipantBE);
}
