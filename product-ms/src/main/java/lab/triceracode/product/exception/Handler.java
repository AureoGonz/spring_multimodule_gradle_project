package lab.triceracode.product.exception;

import lab.triceracode.core.exception.ConstraintParametersException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.stream.Collectors;

@ControllerAdvice
public class Handler {

    @ExceptionHandler({ConstraintParametersException.class, MethodArgumentNotValidException.class, ConstraintViolationException.class})
    protected ResponseEntity<?> badRequest(Exception exception) {
        if (exception instanceof MethodArgumentNotValidException)
            return ResponseEntity.badRequest().body(((MethodArgumentNotValidException) exception)
                    .getBindingResult()
                    .getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList()));
        if (exception instanceof ConstraintParametersException)
            return ResponseEntity.badRequest().body(((ConstraintParametersException) exception).getMessages());
        if(exception instanceof  ConstraintViolationException)
            System.out.println("CVE");
        return ResponseEntity.badRequest().body(Collections.singletonList(""));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<?> notFoundEntity(EntityNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler
    protected ResponseEntity<?> exception(Exception exception){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getClass().toString() +"\n"+ exception.getMessage());
    }

}
