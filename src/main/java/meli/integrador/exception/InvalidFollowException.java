package meli.integrador.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Post not found")
public class InvalidFollowException extends RuntimeException{
    public InvalidFollowException(String id) {
        super("Invalid follow with id:" + id);
    }
}
