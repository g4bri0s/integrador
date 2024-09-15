package meli.integrador.common;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserStrategyTest {

    @Test
    public void testGetValue() {
        assertEquals("client", UserStrategy.CLIENT.getValue());
        assertEquals("seller", UserStrategy.SELLER.getValue());
    }

    @Test
    public void testEnumValues() {
        UserStrategy[] strategies = UserStrategy.values();
        assertEquals(2, strategies.length);
        assertEquals(UserStrategy.CLIENT, strategies[0]);
        assertEquals(UserStrategy.SELLER, strategies[1]);
    }

    @Test
    public void testEnumValueOf() {
        assertEquals(UserStrategy.CLIENT, UserStrategy.valueOf("CLIENT"));
        assertEquals(UserStrategy.SELLER, UserStrategy.valueOf("SELLER"));
    }
}

