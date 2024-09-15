package meli.integrador.dto;

import lombok.Getter;
import lombok.Setter;
import meli.integrador.model.Product;

@Getter
@Setter
public class ProductRequest {
    private String name;
    private String type;
    private String brand;
    private String color;
    private String notes;

    public ProductRequest() {
    }

    public ProductRequest(String name, String type, String brand, String color, String notes) {
        this.name = name;
        this.type = type;
        this.brand = brand;
        this.color = color;
        this.notes = notes;
    }

    public Product toProduct() {
        return new Product(
                this.getName(),
                this.getType(),
                this.getBrand(),
                this.getColor(),
                this.getNotes()
        );
    }

    public Product updateProduct(Product product) {
        if (this.getName() != null) {
            product.setName(this.getName());
        }
        if (this.getType() != null) {
            product.setType(this.getType());
        }
        if (this.getBrand() != null) {
            product.setBrand(this.getBrand());
        }
        if (this.getColor() != null) {
            product.setColor(this.getColor());
        }
        if (this.getNotes() != null) {
            product.setNotes(this.getNotes());
        }
        return product;
    }
}
