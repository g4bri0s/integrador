package meli.integrador.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Post not found")
public class PostNotFoundException extends Exception{
    public PostNotFoundException(Long id) {
        super("Post not found with id: " + id);
    }
}
