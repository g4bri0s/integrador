package meli.integrador.dto;

import lombok.Getter;
import lombok.Setter;
import meli.integrador.model.Post;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class PostResponse{

    private UserResponse user;
    private Date date;
    private ProductResponse product;
    private int category;
    private BigDecimal price;
    private boolean hasPromo;
    private BigDecimal discount;

    public PostResponse() {
    }

    public PostResponse(UserResponse user, Date date, ProductResponse product, int category, BigDecimal price, boolean hasPromo, BigDecimal discount) {
        this.user = user;
        this.date = date;
        this.product = product;
        this.category = category;
        this.price = price;
        this.hasPromo = hasPromo;
        this.discount = discount;
    }

    public PostResponse(Post post) {
        this.user = new UserResponse(post.getUser());
        this.date = post.getDate();
        this.product = new ProductResponse(post.getProduct());
        this.category = post.getCategory();
        this.price = post.getPrice();
        this.hasPromo = post.isHasPromo();
        this.discount = post.getDiscount();
    }
}
