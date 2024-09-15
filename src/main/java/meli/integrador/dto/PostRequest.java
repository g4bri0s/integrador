package meli.integrador.dto;

import lombok.Getter;
import lombok.Setter;
import meli.integrador.model.Post;
import meli.integrador.model.Product;
import meli.integrador.model.User;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class PostRequest {

    private Date date;
    private Long product;
    private int category;
    private BigDecimal price;
    private boolean hasPromo;
    private BigDecimal discount;

    public PostRequest() {
    }

    public PostRequest(Date date, Long product, int category, BigDecimal price, boolean hasPromo, BigDecimal discount) {
        this.date = date;
        this.product = product;
        this.category = category;
        this.price = price;
        this.hasPromo = hasPromo;
        this.discount = discount;
    }

    public Post toPost() {
        return new Post(new User(), this.getDate(), new Product(), this.getCategory(), this.getPrice(), this.isHasPromo(), this.getDiscount());
    }

    public Post updatePost(Post post) {
        if (this.getDate() != null) {
            post.setDate(this.getDate());
        }
        if (this.getCategory() != 0) {
            post.setCategory(this.getCategory());
        }
        if (this.getPrice() != null) {
            post.setPrice(this.getPrice());
        }
        if (this.isHasPromo()) {
            post.setHasPromo(true);
        }
        if (this.getDiscount() != null) {
            post.setDiscount(this.getDiscount());
        }
        return post;
    }

    public PostResponse toPostResponse() {
        return new PostResponse(new UserResponse(), this.getDate(), new ProductResponse(), this.getCategory(), this.getPrice(), this.isHasPromo(), this.getDiscount());
    }
}
