package meli.integrador.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Product not found")
public class ProductNotFound extends Exception{
    public ProductNotFound(Long id) {
        super("Product not found with id: " + id);
    }
}
