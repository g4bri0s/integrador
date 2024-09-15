package meli.integrador.dto;

import lombok.Getter;
import lombok.Setter;
import meli.integrador.model.User;

@Setter
@Getter
public class UserResponse {

    private String id;
    private String name;
    private String email;
    private String userType;

    public UserResponse() {
    }

    public UserResponse(String id, String name, String email, String userType) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.userType = userType;
    }

    public UserResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.userType = user.getUserType().getValue();
    }
}
