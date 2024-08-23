package br.unipar.banner.infra;

import br.unipar.banner.exceptions.ImageNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ImageNotFoundException.class)
    private ResponseEntity<String> imageNotfoundHandler(ImageNotFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Image not found.");
    }

}
