package social.media.socialMedia.exception;


import jakarta.persistence.EntityExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import social.media.socialMedia.exception.event.UserEventPublisherException;
import social.media.socialMedia.exception.user.UserCreationException;
import social.media.socialMedia.exception.user.UserDeletionException;
import social.media.socialMedia.exception.user.UserUpdateException;

@ControllerAdvice
public class GlobalExceptionHandler {

    //TODO: should I even use this???
    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<String> handleEntityExistsException(EntityExistsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    //TODO: should I use this for read exceptions?
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserCreationException.class)
    public ResponseEntity<String> handleUserCreationException(UserCreationException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(UserUpdateException.class)
    public ResponseEntity<String> handleUserUpdateException(UserUpdateException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(UserDeletionException.class)
    public ResponseEntity<String> handleUserDeletionException(UserDeletionException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }


    //event publish exception
    @ExceptionHandler(UserEventPublisherException.class)
    public ResponseEntity<String> handleUserEventPublisherException(UserEventPublisherException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // handle any other exceptions
    //!Note this should be the last handler and ideally never be thrown
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}