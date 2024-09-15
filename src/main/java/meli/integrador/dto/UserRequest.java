package meli.integrador.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import meli.integrador.annotations.Cpf;
import meli.integrador.annotations.Email;
import meli.integrador.annotations.Name;
import meli.integrador.annotations.Password;
import meli.integrador.common.UserStrategy;
import meli.integrador.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Setter
@Data
public class UserRequest{

    @Cpf
    private String cpf;
    @Name
    private String name;
    @Email
    private String email;
    @Password
    private String password;
    private String userType;

    public UserRequest() {
    }

    public UserRequest(String cpf, String name, String email, String password ,String userType) {
        this.cpf = cpf;
        this.name = name;
        this.email = email;
        this.password = password;
        this.userType = userType;
    }

    public User toUser() {
        return new User(this.getCpf(), this.getName(), this.getEmail(), this.getPassword(), UserStrategy.valueOf(this.getUserType()));
    }

    public User updateUser(User user, PasswordEncoder passwordEncoder) {
        if (this.getName() != null) {
            user.setName(this.getName());
        }
        if (this.getEmail() != null) {
            user.setEmail(this.getEmail());
        }
        if(this.getPassword() != null){
            user.setPassword(passwordEncoder.encode(this.getPassword()));
        }
        if(this.getUserType() != null){
            user.setUserType(UserStrategy.valueOf(this.getUserType()));
        }
        return user;
    }
}
