package nl.wessel.juice.Controller;

import nl.wessel.juice.Exception.BadRequestException;
import nl.wessel.juice.Exception.RecordNotFoundException;
import nl.wessel.juice.Exception.UsernameNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = RecordNotFoundException.class)
    public ResponseEntity<Object> exception(RecordNotFoundException exception) {

        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<Object> badRequest(BadRequestException bad) {
        return new ResponseEntity<>(bad.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = UsernameNotFoundException.class)
    public ResponseEntity<Object> username(UsernameNotFoundException user) {
        return new ResponseEntity<>(user.getMessage(), HttpStatus.NOT_FOUND);
    }

}