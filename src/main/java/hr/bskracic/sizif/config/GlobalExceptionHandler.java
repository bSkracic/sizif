package hr.bskracic.sizif.config;

import hr.bskracic.sizif.controller.dto.ApiError;
import hr.bskracic.sizif.controller.dto.XmlValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@EnableWebMvc
@Component
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({XmlValidationException.class})
    public ResponseEntity<ApiError> handleSAXError(XmlValidationException xve) {
        return new ResponseEntity<>(new ApiError(xve.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
