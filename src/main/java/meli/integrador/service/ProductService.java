package meli.integrador.service;

import meli.integrador.dto.ProductRequest;
import meli.integrador.dto.ProductResponse;
import meli.integrador.exception.ProductNotFound;
import meli.integrador.model.Product;
import meli.integrador.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = productRequest.toProduct();
        productRepository.save(product);
        return new ProductResponse(product);
    }

    public ProductResponse getProduct(Long id) throws ProductNotFound {
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()) {
            return new ProductResponse(product.get());
        }
        throw new ProductNotFound(id);
    }

    public Page<ProductResponse> getAllProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAll(pageable).map(ProductResponse::new);
    }

    public ProductResponse updateProduct(Long id, ProductRequest productRequest) throws ProductNotFound {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            Product updatedProduct = productRequest.updateProduct(product.get());
            updatedProduct.setId(id);
            productRepository.save(updatedProduct);
            return new ProductResponse(updatedProduct);
        }
        throw new ProductNotFound(id);
    }

    public void deleteProduct(Long id) throws ProductNotFound {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            productRepository.delete(product.get());
        } else {
            throw new ProductNotFound(id);
        }
    }
}
