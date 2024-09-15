package meli.integrador.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Getter
@Setter
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "posts_ibfk_1"))
    private User user;

    @NonNull
    private Date date;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "product_id", foreignKey = @ForeignKey(name = "posts_ibfk_2"))
    private Product product;

    @NonNull
    private int category;

    @NonNull
    @Min(1)
    private BigDecimal price;

    private boolean hasPromo;

    @Max(99)
    @Min(0)
    private BigDecimal discount;

    public Post() {
    }

    public Post(User user, Date date, Product product, int category, BigDecimal price, boolean hasPromo, BigDecimal discount) {
        this.user = user;
        this.date = date;
        this.product = product;
        this.category = category;
        this.price = price;
        this.hasPromo = hasPromo;
        this.discount = discount;
    }
}
