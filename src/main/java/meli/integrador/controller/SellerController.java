package meli.integrador.controller;

import jakarta.validation.Valid;
import meli.integrador.dto.UserRequest;
import meli.integrador.dto.UserResponse;
import meli.integrador.exception.UserAlreadyExistException;
import meli.integrador.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;

@RestController
@RequestMapping("/seller")
public class SellerController {

    @Autowired
    private UserService userService;

    @PostMapping
    public UserResponse createSeller (@Valid @RequestBody UserRequest userRequest) throws UserAlreadyExistException {
        userRequest.setUserType("SELLER");
        Optional<UserResponse> user = Optional.ofNullable(userService.findByEmail(userRequest.getEmail()));
        if(user.isPresent()){
            throw new UserAlreadyExistException(userRequest.getCpf(), userRequest.getEmail());
        }
        return userService.createUser(userRequest);
    }
}