package eu.possiblex.portal.business.entity.exception;

public class RegistrationRequestCreationException extends RuntimeException {
    public RegistrationRequestCreationException(String message) {

        super(message);
    }

    public RegistrationRequestCreationException(String message, Exception e) {

        super(message, e);
    }
}