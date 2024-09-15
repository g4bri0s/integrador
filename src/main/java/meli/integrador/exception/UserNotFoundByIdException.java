package meli.integrador.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "User not found")
public class UserNotFoundByIdException extends Exception{

        public UserNotFoundByIdException(String id) {
            super(String.format("User with ID %s not found", id));
        }
}
