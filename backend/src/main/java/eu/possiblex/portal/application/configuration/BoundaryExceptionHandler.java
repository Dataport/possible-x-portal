package eu.possiblex.portal.application.configuration;

import eu.possiblex.portal.application.entity.ErrorResponseTO;
import eu.possiblex.portal.business.entity.exception.ParticipantComplianceException;
import eu.possiblex.portal.business.entity.exception.RegistrationRequestCreationException;
import eu.possiblex.portal.business.entity.exception.RegistrationRequestProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
@Slf4j
public class BoundaryExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponseTO> handleRegistrationRequestCreationException(
        RegistrationRequestCreationException e) {

        logError(e);
        return new ResponseEntity<>(new ErrorResponseTO("Failed to process registration request.", e.getMessage()),
            UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponseTO> handleRegistrationRequestProcessingException(
        RegistrationRequestProcessingException e) {

        logError(e);
        return new ResponseEntity<>(new ErrorResponseTO("Failed to create registration request.", e.getMessage()),
            CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponseTO> handleParticipantComplianceException(ParticipantComplianceException e) {

        logError(e);
        return new ResponseEntity<>(
            new ErrorResponseTO("Compliance was not attested for this participant.", e.getMessage()),
            UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponseTO> handleUnknownException(Exception e) {

        logError(e);
        return new ResponseEntity<>(new ErrorResponseTO("An unknown error occurred."), INTERNAL_SERVER_ERROR);
    }

    private void logError(Exception e) {

        log.error("Caught boundary exception: {}", e.getClass().getName(), e);
    }
}
