package hr.bskracic.sizif.controller.dto;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class XmlValidationException extends RuntimeException {
    public XmlValidationException(String message) {
        super(message);
    }
}
