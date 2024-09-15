package meli.integrador.model;

import meli.integrador.common.UserStrategy;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    public void testNoArgsConstructor() {
        User user = new User();
        assertNotNull(user);
    }

    @Test
    public void testAllArgsConstructor() {
        User user = new User("123.456.789-00", "John Doe", "john.doe@example.com", "password123", UserStrategy.SELLER);
        assertEquals("123.456.789-00", user.getCpf());
        assertEquals("John Doe", user.getName());
        assertEquals("john.doe@example.com", user.getEmail());
        assertEquals("password123", user.getPassword());
        assertEquals(UserStrategy.SELLER, user.getUserType());
    }

    @Test
    public void testSettersAndGetters() {
        User user = new User();
        user.setId("1");
        user.setCpf("123.456.789-00");
        user.setName("Jane Doe");
        user.setEmail("jane.doe@example.com");
        user.setPassword("password321");
        user.setUserType(UserStrategy.CLIENT);

        assertEquals("1", user.getId());
        assertEquals("123.456.789-00", user.getCpf());
        assertEquals("Jane Doe", user.getName());
        assertEquals("jane.doe@example.com", user.getEmail());
        assertEquals("password321", user.getPassword());
        assertEquals(UserStrategy.CLIENT, user.getUserType());
    }
}

