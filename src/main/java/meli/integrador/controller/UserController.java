package meli.integrador.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import meli.integrador.dto.UserRequest;
import meli.integrador.dto.UserResponse;
import meli.integrador.exception.UserAlreadyExistException;
import meli.integrador.exception.UserNotFoundByIdException;
import meli.integrador.exception.UserNotFoundByEmailException;
import meli.integrador.model.User;
import meli.integrador.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private Validator validator;

    @PostMapping
    public UserResponse createUser(@Valid @RequestBody UserRequest userRequest) throws UserAlreadyExistException {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        validator.validate(userRequest);
        return userService.createUser(userRequest);
    }

    @GetMapping
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    public Page<UserResponse> getAllUsers(@RequestParam(defaultValue = "0") int pag, @RequestParam(defaultValue = "20") int size) {
        return userService.getAllUsers(pag, size);
    }

    @GetMapping("/{string}")
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    public User getUser(@PathVariable String string) throws UserNotFoundByIdException, UserNotFoundByEmailException {
        if(string.contains("@")){
           return userService.getUserByEmail(string);
        }
           return userService.getUserById(string);
    }

    @PutMapping("/{string}")
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    public UserResponse updateUser(@PathVariable String string, @Valid @RequestBody UserRequest userRequest) throws UserNotFoundByIdException, UserNotFoundByEmailException {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        if(string.contains("@")){
            return userService.updateUserByEmail(userRequest, string);
        }
        return userService.updateUserById(userRequest, string);
    }

    @DeleteMapping("/{string}")
    @ResponseStatus(code = HttpStatus.OK)
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    public void deleteUser(@PathVariable String string) throws UserNotFoundByIdException, UserNotFoundByEmailException {
        if(string.contains("@")){
            userService.deleteUserByEmail(string);
        } else {
            userService.deleteUserById(string);
        }
    }

    @PostMapping("/{userId}/follow/{idFollowed}")
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    public void followUser(@PathVariable String userId, @PathVariable String idFollowed) throws UserNotFoundByIdException {
        userService.followUser(userId, idFollowed);
    }

    @DeleteMapping("/{userId}/unfollow/{idFollowed}")
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    public void unfollowUser(@PathVariable String userId, @PathVariable String idFollowed) throws UserNotFoundByIdException {
        userService.unfollowUser(userId, idFollowed);
    }

    @GetMapping("/{userId}/followers/count")
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    public Integer getFollowersCount(@PathVariable String userId) throws UserNotFoundByIdException {
        return userService.getFollowersCount(userId);
    }

    @GetMapping("/{userId}/followers/list")
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    public Page<UserResponse> getFollowersList(@PathVariable String userId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) throws UserNotFoundByIdException {
        return userService.getFollowers(userId, page, size);
    }

}
