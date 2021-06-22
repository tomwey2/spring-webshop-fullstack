package de.tom.ref.webshop.entities;

import javax.persistence.*;

@Entity
@Table(name="cart_content")
public class CartContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "content_id")
    private Integer id;

    //@ManyToOne
    //@JoinColumn(name="cart_id", nullable=false)
    @Column(name = "cart_id")
    private Integer cartId;

    //@ManyToOne
    //@JoinColumn(name="product_id", nullable=false)
    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "quantity")
    private Integer quantity;

    public CartContent(Integer cartId, Integer productId, Integer quantity) {
        this.cartId = cartId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        final CartContent other = (CartContent) obj;
        return (other.getCartId() == this.getCartId()) && (other.getProductId() == this.getProductId());
    }
}
