package meli.integrador.service;

import meli.integrador.dto.UserRequest;
import meli.integrador.dto.UserResponse;
import meli.integrador.exception.UserAlreadyExistException;
import meli.integrador.exception.UserNotFoundByIdException;
import meli.integrador.exception.UserNotFoundByEmailException;
import meli.integrador.model.User;
import meli.integrador.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    public void testGetAllUsers() {
//        UserRequest userRequest1 = new UserRequest("48913037866", "nome1", "bonitoemail1@email.com", "senha123", "CLIENT");
//        UserRequest userRequest2 = new UserRequest("48914037866", "nome2", "bonitoemail2@email.com", "senha123", "SELLER");
//        List<User> users = Arrays.asList(userRequest1.toUser(), userRequest2.toUser());
//        when(userRepository.findAll()).thenReturn(users);
//
//        List<UserResponse> userResponses = userService.getAllUsers();
//
//        assertNotNull(userResponses);
//        assertEquals(2, userResponses.size());
//        verify(userRepository).findAll();
//    }

    @Test
    public void testUpdateUserByIdNotFound() {
        UserRequest userRequest = mock(UserRequest.class);
        when(userRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundByIdException.class, () -> {
            userService.updateUserById(userRequest, "1");
        });

        verify(userRepository).findById("1");
    }

    @Test
    public void testGetUserByIdFound() throws UserNotFoundByIdException {
        UserRequest userRequest = new UserRequest("48913037866", "nome", "bonitoemail@email.com", "senha123", "CLIENT");
        User user = userRequest.toUser();
        when(userRepository.findById("1")).thenReturn(Optional.of(user));

        UserResponse userResponse = userService.getUserById("1");

        assertNotNull(userResponse);
        verify(userRepository).findById("1");
    }

    @Test
    public void testGetUserByIdNotFound() {
        when(userRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundByIdException.class, () -> {
            userService.getUserById("1");
        });

        verify(userRepository).findById("1");
    }

    @Test
    public void testGetUserByEmailFound() throws UserNotFoundByEmailException {
        UserRequest userRequest = new UserRequest("48913037866", "nome", "bonitoemail@email.com", "senha123", "CLIENT");
        User user = userRequest.toUser();
        when(userRepository.findByEmail("\"bonitoemail@email.com")).thenReturn(user);

        UserResponse userResponse = userService.getUserByEmail("\"bonitoemail@email.com");

        assertNotNull(userResponse);
        verify(userRepository).findByEmail("\"bonitoemail@email.com");
    }

    @Test
    public void testGetUserByEmailNotFound() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(null);

        assertThrows(UserNotFoundByEmailException.class, () -> {
            userService.getUserByEmail("test@example.com");
        });

        verify(userRepository).findByEmail("test@example.com");
    }

    @Test
    public void testDeleteUserByIdFound() throws UserNotFoundByIdException {
        User user = new User();
        when(userRepository.findById("1")).thenReturn(Optional.of(user));

        userService.deleteUserById("1");

        verify(userRepository).findById("1");
        verify(userRepository).delete(user);
    }

    @Test
    public void testDeleteUserByIdNotFound() {
        when(userRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundByIdException.class, () -> {
            userService.deleteUserById("1");
        });

        verify(userRepository).findById("1");
    }

    @Test
    public void testDeleteUserByEmailFound() throws UserNotFoundByEmailException {
        User user = new User();
        when(userRepository.findByEmail("test@example.com")).thenReturn(user);

        userService.deleteUserByEmail("test@example.com");

        verify(userRepository).findByEmail("test@example.com");
        verify(userRepository).delete(user);
    }

    @Test
    public void testDeleteUserByEmailNotFound() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(null);

        assertThrows(UserNotFoundByEmailException.class, () -> {
            userService.deleteUserByEmail("test@example.com");
        });

        verify(userRepository).findByEmail("test@example.com");
    }

    @Test
    public void testGetFollowersCount() throws UserNotFoundByIdException {
        User user = new User();
        when(userRepository.findById("1")).thenReturn(Optional.of(user));
        when(userRepository.countFollowers("1")).thenReturn(0);

        int count = userService.getFollowersCount("1");

        assertEquals(0, count);
        verify(userRepository).findById("1");
        verify(userRepository).countFollowers("1");
    }
}