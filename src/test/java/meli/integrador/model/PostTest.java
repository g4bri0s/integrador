package meli.integrador.model;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

public class PostTest {

    @Test
    public void testNoArgsConstructor() {
        Post post = new Post();
        assertNotNull(post);
    }

    @Test
    public void testAllArgsConstructor() {
        User user = new User();
        Product product = new Product();
        Date date = new Date();
        BigDecimal price = BigDecimal.valueOf(100);
        BigDecimal discount = BigDecimal.valueOf(10);

        Post post = new Post(user, date, product, 1, price, true, discount);
        assertNull(post.getId());
        assertEquals(user, post.getUser());
        assertEquals(date, post.getDate());
        assertEquals(product, post.getProduct());
        assertEquals(1, post.getCategory());
        assertEquals(price, post.getPrice());
        assertTrue(post.isHasPromo());
        assertEquals(discount, post.getDiscount());
    }

    @Test
    public void testSettersAndGetters() {
        Post post = new Post();
        User user = new User();
        Product product = new Product();
        Date date = new Date();
        BigDecimal price = BigDecimal.valueOf(100);
        BigDecimal discount = BigDecimal.valueOf(10);

        post.setId(1L);
        post.setUser(user);
        post.setDate(date);
        post.setProduct(product);
        post.setCategory(1);
        post.setPrice(price);
        post.setHasPromo(true);
        post.setDiscount(discount);

        assertEquals(1L, post.getId());
        assertEquals(user, post.getUser());
        assertEquals(date, post.getDate());
        assertEquals(product, post.getProduct());
        assertEquals(1, post.getCategory());
        assertEquals(price, post.getPrice());
        assertTrue(post.isHasPromo());
        assertEquals(discount, post.getDiscount());
    }
}

