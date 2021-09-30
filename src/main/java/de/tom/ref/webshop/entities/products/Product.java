package de.tom.ref.webshop.entities.products;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name="products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    /**
     * Product's unique identifier. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * The product's name (or title). Example: 'Mens Pique Polo Shirt'
     * The product title has max. 150 characters. It clearly describes
     * the product. */
    @Column(name = "title", length = 150)
    private String title;

    /**
     * The product's description. It has max. 5000 characters. */
    @Column(name = "description", length = 500)
    private String description;

    /**
     * The product's landing page. If the users click on the link they are
     * sent to a landing page on the website for that product. The link
     * attribute is the URL.
     * Actually not used. */
    @Column(name = "link", length = 50)
    private String link;

    /**
     * The URL of the main image of the product.
     * Actually not used. */
    @Column(name = "image_link", length = 50)
    private String image;

    /**
     * The product's price. */
    @Column(name = "price", scale = 2)
    private BigDecimal price = BigDecimal.ZERO;

    @ManyToOne
    @JoinColumn(name="category_id", nullable=false)
    private ProductCategory category;

    @Column(name = "units_in_stock")
    private Integer unitsInStock = 0;

    /**
     * The product's availability.
     */
    @Enumerated(EnumType.STRING)
    private ProductAvailability availability;

    /** The attribute active is set to true (default) if the product is used
     * otherwise false. */
    @Column(name = "active")
    private Boolean active = Boolean.TRUE;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "last_update")
    private LocalDateTime updateDate;

    public Product(String title, ProductCategory category, BigDecimal price, Integer unitsInStock) {
        this.title = title;
        this.category = category;
        this.price = price;
        this.unitsInStock = unitsInStock;
        this.availability = unitsInStock > 0 ? ProductAvailability.in_stock : ProductAvailability.out_of_stock;
        this.active = Boolean.TRUE;
        this.createDate = LocalDateTime.now();
    }

}
