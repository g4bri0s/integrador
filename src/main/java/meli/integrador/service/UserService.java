package meli.integrador.service;

import meli.integrador.dto.UserRequest;
import meli.integrador.dto.UserResponse;
import meli.integrador.exception.InvalidFollowException;
import meli.integrador.exception.UserAlreadyExistException;
import meli.integrador.exception.UserNotFoundByIdException;
import meli.integrador.exception.UserNotFoundByEmailException;
import meli.integrador.model.User;
import meli.integrador.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserResponse createUser(UserRequest userRequest) throws UserAlreadyExistException {
        User user = userRequest.toUser();
        Optional<User> userOptional = userRepository.findByCpfOrEmail(user.getCpf(), user.getEmail());
        if (userOptional.isPresent()) {
            throw new UserAlreadyExistException(user.getCpf(), user.getEmail());
        }
            String hashPwd = passwordEncoder.encode(user.getPassword());
            user.setPassword(hashPwd);
            User savedUser = userRepository.save(user);
            return new UserResponse(savedUser);
    }

    public Page<UserResponse> getAllUsers(int pag, int size) {
        Pageable pageable = PageRequest.of(pag, size);
        return userRepository.findAll(pageable).map(UserResponse::new);
    }

    public UserResponse updateUserById(UserRequest userRequest, String id) throws UserNotFoundByIdException {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userRequest.updateUser(userOptional.get(), passwordEncoder);
            userRepository.save(user);
            return new UserResponse(user);
        }
        throw new UserNotFoundByIdException(id);
    }

    public User getUserById(String id) throws UserNotFoundByIdException {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        throw new UserNotFoundByIdException(id);
    }

    public UserResponse updateUserByEmail(UserRequest userRequest, String email) throws UserNotFoundByEmailException {
        Optional<User> userOptional = Optional.ofNullable(userRepository.findByEmail(email));
        if (userOptional.isPresent()) {
            User user = userRequest.updateUser(userOptional.get(), passwordEncoder);
            userRepository.save(user);
            return new UserResponse(user);
        }
        throw new UserNotFoundByEmailException(email);
    }

    public User getUserByEmail(String email) throws UserNotFoundByEmailException {
        Optional<User> userOptional = Optional.ofNullable(userRepository.findByEmail(email));
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        throw new UserNotFoundByEmailException(email);
    }

    public void deleteUserById(String id) throws UserNotFoundByIdException {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            userRepository.delete(userOptional.get());
        } else {
            throw new UserNotFoundByIdException(id);
        }
    }

    public void deleteUserByEmail(String email) throws UserNotFoundByEmailException {
        Optional<User> userOptional = Optional.ofNullable(userRepository.findByEmail(email));
        if (userOptional.isPresent()) {
            userRepository.delete(userOptional.get());
        } else {
            throw new UserNotFoundByEmailException(email);
        }
    }

    public void followUser(String id, String sellerId) throws UserNotFoundByIdException {
        Optional<User> userOptional = userRepository.findById(id);
        Optional<User> sellerOptional = userRepository.findById(sellerId);
        if (userOptional.isPresent() && sellerOptional.isPresent()){
            if (!id.equals(sellerId) && !isFollowing(id, sellerId) && sellerOptional.get().isSeller()) {
                User user = userOptional.get();
                User seller = sellerOptional.get();
                userRepository.followUser(user.getId(), seller.getId());
            } else {
                throw new InvalidFollowException(sellerId);
            }
        } else {
            throw new UserNotFoundByIdException(sellerId);
        }
    }

    public void unfollowUser(String id, String sellerId) throws UserNotFoundByIdException {
        Optional<User> userOptional = userRepository.findById(id);
        Optional<User> sellerOptional = userRepository.findById(sellerId);
        if (userOptional.isPresent() && sellerOptional.isPresent()) {
            User user = userOptional.get();
            User seller = sellerOptional.get();
            int rows = userRepository.unfollowUser(user.getId(), seller.getId());
            if (rows == 0) {
                throw new InvalidFollowException(sellerId);
            }
        } else {
            throw new UserNotFoundByIdException(sellerId);
        }
    }

    public Page<UserResponse> getFollowers(String id, int page, int size) throws UserNotFoundByIdException {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            Pageable pageable = PageRequest.of(page, size);
            return userRepository.getFollowers(id, pageable).map(UserResponse::new);
        } else {
            throw new UserNotFoundByIdException(id);
        }
    }

    public int getFollowersCount(String id) throws UserNotFoundByIdException {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            return userRepository.countFollowers(id);
        } else {
            throw new UserNotFoundByIdException(id);
        }
    }

    public UserResponse findByEmail(String email) {
        Optional<User> userOptional = Optional.ofNullable(userRepository.findByEmail(email));
        if (userOptional.isPresent()) {
            return new UserResponse(userOptional.get());
        }
        return null;
    }

    public boolean isFollowing(String id, String sellerId) {
        return userRepository.isFollowing(id, sellerId) == 1;
    }
}
