package meli.integrador.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import meli.integrador.dto.ProductRequest;
import meli.integrador.dto.ProductResponse;
import meli.integrador.exception.ProductNotFound;
import meli.integrador.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    public ProductResponse createProduct(@RequestBody ProductRequest productRequest){
        return productService.createProduct(productRequest);
    }

    @GetMapping("/{id}")
    public ProductResponse getProduct(@PathVariable Long id) throws ProductNotFound {
        return productService.getProduct(id);
    }

    @GetMapping
    public Page<ProductResponse> getProducts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        return productService.getAllProducts(page, size);
    }

    @PutMapping("/{id}")
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    public ProductResponse updateProduct(@PathVariable Long id, @RequestBody ProductRequest productRequest) throws ProductNotFound {
        return productService.updateProduct(id, productRequest);
    }

    @DeleteMapping("/{id}")
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    public void deleteProduct(@PathVariable Long id) throws ProductNotFound {
        productService.deleteProduct(id);
    }

}
