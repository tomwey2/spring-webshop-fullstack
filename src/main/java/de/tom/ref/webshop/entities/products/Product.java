package de.tom.ref.webshop.entities.products;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name="products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer id;

    @Column(name = "product_name", length = 50)
    private String name;

    @ManyToOne
    @JoinColumn(name="category_id", nullable=false)
    private ProductCategory category;

    @Column(name = "unit_price", scale = 2)
    private BigDecimal unitPrice = BigDecimal.ZERO;

    @Column(name = "units_in_stock")
    private Integer unitsInStock = 0;

    @Column(name = "units_on_order")
    private Integer unitsOnOrder = 0;

    @Column(name = "active")
    private Boolean active = Boolean.TRUE;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "last_update")
    private LocalDateTime updateDate;

    public Product(String name, ProductCategory category, BigDecimal unitPrice, Integer unitsInStock) {
        this.name = name;
        this.category = category;
        this.unitPrice = unitPrice;
        this.unitsInStock = unitsInStock;
        this.active = Boolean.TRUE;
        this.createDate = LocalDateTime.now();
    }

}
