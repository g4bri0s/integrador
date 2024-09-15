package meli.integrador.service;

import meli.integrador.dto.AuthRequest;
import meli.integrador.middleware.JwtAuthService;
import meli.integrador.model.User;
import meli.integrador.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private JwtAuthService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public Optional<String> authenticateUser(Optional<AuthRequest> login) {

        var credentials = new UsernamePasswordAuthenticationToken(login.get().getEmail(), login.get().getPassword());

        Authentication authentication = authenticationManager.authenticate(credentials);

        if (authentication.isAuthenticated()) {
            Optional<User> user =
                    Optional.ofNullable(repository.findByEmail(login.get().getEmail()));
            if (user.isPresent()) {
                String response = generateToken(login.get().getEmail());
                return Optional.of(response);
            }
        }
        return Optional.empty();
    }

    private String generateToken(String username) {
        return "Bearer " + jwtService.generateToken(username);
    }
}