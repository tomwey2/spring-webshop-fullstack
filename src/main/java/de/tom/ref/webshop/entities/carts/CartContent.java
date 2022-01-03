package de.tom.ref.webshop.entities.carts;

import de.tom.ref.webshop.entities.products.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name="cart_contents")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartContent {

    /**
     * The cart content's unique identifier. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * The reference to the cart whose contains the contents.
     */
    @ManyToOne
    @JoinColumn(name="cart_id", nullable=false)
    private Cart cart;

    /**
     * The reference to the product that is the content.
     */
    @ManyToOne
    @JoinColumn(name="product_id", nullable=false)
    private Product product;

    /**
     * The quantity describes how many units of the product is in th cart.
     */
    @Column(name = "quantity")
    private Integer quantity;

    /**
     * The price of the amount of units, i.e. price = quantity * product price.
     */
    @Column(name = "price", scale = 2)
    private BigDecimal price = BigDecimal.ZERO;

    public CartContent(Cart cart, Product product, Integer quantity) {
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
        this.price = product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }

}
