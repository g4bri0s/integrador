package meli.integrador.dto;

import lombok.Getter;
import lombok.Setter;
import meli.integrador.model.Product;

@Getter
@Setter
public class ProductResponse {
    private String name;
    private String type;
    private String brand;
    private String color;
    private String notes;

    public ProductResponse() {
    }

    public ProductResponse(String name, String type, String brand, String color, String notes) {
        this.name = name;
        this.type = type;
        this.brand = brand;
        this.color = color;
        this.notes = notes;
    }

    public ProductResponse(Product product) {
        this.name = product.getName();
        this.type = product.getType();
        this.brand = product.getBrand();
        this.color = product.getColor();
        this.notes = product.getNotes();
    }
}
