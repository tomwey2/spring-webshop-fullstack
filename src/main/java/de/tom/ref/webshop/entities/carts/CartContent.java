package de.tom.ref.webshop.entities.carts;

import de.tom.ref.webshop.entities.products.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name="cart_content")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "content_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name="cart_id", nullable=false)
    private Cart cart;

    @ManyToOne
    @JoinColumn(name="product_id", nullable=false)
    private Product product;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "price", scale = 2)
    private BigDecimal price = BigDecimal.ZERO;

    public CartContent(Cart cart, Product product, Integer quantity) {
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
        this.price = product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }

}
