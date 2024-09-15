package meli.integrador.service;

import meli.integrador.dto.ProductRequest;
import meli.integrador.dto.ProductResponse;
import meli.integrador.exception.ProductNotFound;
import meli.integrador.model.Product;
import meli.integrador.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateProduct() {
        ProductRequest productRequest = mock(ProductRequest.class);
        Product product = new Product();
        when(productRequest.toProduct()).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);

        ProductResponse productResponse = productService.createProduct(productRequest);

        assertNotNull(productResponse);
        verify(productRequest).toProduct();
        verify(productRepository).save(product);
    }

    @Test
    public void testGetProductFound() throws ProductNotFound {
        Product product = new Product();
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        ProductResponse productResponse = productService.getProduct(1L);

        assertNotNull(productResponse);
        verify(productRepository).findById(1L);
    }

    @Test
    public void testGetProductNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFound.class, () -> {
            productService.getProduct(1L);
        });

        verify(productRepository).findById(1L);
    }

    @Test
    public void testGetAllProducts() {
        List<Product> products = Arrays.asList(new Product(), new Product());
        when(productRepository.findAll()).thenReturn(products);

        Page<ProductResponse> productResponses = productService.getAllProducts(0, 10);

        assertNotNull(productResponses);
        assertEquals(2, productResponses.getTotalElements());
        verify(productRepository).findAll();
    }

    @Test
    public void testUpdateProductFound() throws ProductNotFound {
        ProductRequest productRequest = mock(ProductRequest.class);
        Product product = new Product();
        Product updatedProduct = new Product();

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRequest.updateProduct(product)).thenReturn(updatedProduct);
        when(productRepository.save(updatedProduct)).thenReturn(updatedProduct);

        ProductResponse productResponse = productService.updateProduct(1L, productRequest);

        assertNotNull(productResponse);
        verify(productRepository).findById(1L);
        verify(productRequest).updateProduct(product);
        verify(productRepository).save(updatedProduct);
    }

    @Test
    public void testUpdateProductNotFound() {
        ProductRequest productRequest = mock(ProductRequest.class);
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFound.class, () -> {
            productService.updateProduct(1L, productRequest);
        });

        verify(productRepository).findById(1L);
    }

    @Test
    public void testDeleteProductFound() throws ProductNotFound {
        Product product = new Product();
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        productService.deleteProduct(1L);

        verify(productRepository).findById(1L);
        verify(productRepository).delete(product);
    }

    @Test
    public void testDeleteProductNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFound.class, () -> {
            productService.deleteProduct(1L);
        });

        verify(productRepository).findById(1L);
    }
}

