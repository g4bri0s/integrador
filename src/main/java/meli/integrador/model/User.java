package meli.integrador.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import meli.integrador.common.UserStrategy;
import org.springframework.lang.NonNull;

@Data
@Entity
@Getter
@Setter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private String id;

    @NonNull
    @Column(unique = true)
    private String cpf;

    @NonNull
    private String name;

    @NonNull
    @Column(unique = true)
    private String email;

    @NonNull
    private String password;

    @NonNull
    @Column(name = "user_type")
    @Enumerated(EnumType.STRING)
    private UserStrategy userType;

    public User() {
    }

    public User(String cpf, String name, String email, String password, UserStrategy userType){
        this.cpf = cpf;
        this.name = name;
        this.email = email;
        this.password = password;
        this.userType = userType;
    }

    public boolean isSeller() {
        return this.userType.equals(UserStrategy.SELLER);
    }
}
