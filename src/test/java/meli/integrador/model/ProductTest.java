package meli.integrador.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {

    @Test
    public void testNoArgsConstructor() {
        Product product = new Product();
        assertNotNull(product);
    }

    @Test
    public void testAllArgsConstructor() {
        Product product = new Product("Product Name", "Type", "Brand", "Color", "Notes");
        assertNull(product.getId());
        assertEquals("Product Name", product.getName());
        assertEquals("Type", product.getType());
        assertEquals("Brand", product.getBrand());
        assertEquals("Color", product.getColor());
        assertEquals("Notes", product.getNotes());
    }

    @Test
    public void testSettersAndGetters() {
        Product product = new Product();
        product.setId(1L);
        product.setName("New Product");
        product.setType("New Type");
        product.setBrand("New Brand");
        product.setColor("New Color");
        product.setNotes("New Notes");

        assertEquals(1L, product.getId());
        assertEquals("New Product", product.getName());
        assertEquals("New Type", product.getType());
        assertEquals("New Brand", product.getBrand());
        assertEquals("New Color", product.getColor());
        assertEquals("New Notes", product.getNotes());
    }
}