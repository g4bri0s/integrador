package meli.integrador.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "User already exists")
public class UserAlreadyExistException extends Exception{

    public UserAlreadyExistException(String cpf, String email) {
        super(String.format("User with CPF %s or email %s already exists", cpf, email));
    }
}
