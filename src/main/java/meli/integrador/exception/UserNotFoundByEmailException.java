package meli.integrador.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "User not found")
public class UserNotFoundByEmailException extends Exception{

    public UserNotFoundByEmailException(String email) {
        super(String.format("User with email %s not found", email));
    }
}
