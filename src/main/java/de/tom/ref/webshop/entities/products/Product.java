package de.tom.ref.webshop.entities.products;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name="products")
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
    private Date createDate;

    @Column(name = "last_update")
    private Date updateDate;

    public Product() {
    }

    public Product(String name, ProductCategory category, BigDecimal unitPrice, Integer unitsInStock) {
        this.name = name;
        this.category = category;
        this.unitPrice = unitPrice;
        this.unitsInStock = unitsInStock;
        this.active = Boolean.TRUE;
        this.createDate = new Date();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getUnitsInStock() {
        return unitsInStock;
    }

    public void setUnitsInStock(Integer unitsInStock) {
        this.unitsInStock = unitsInStock;
    }

    public Integer getUnitsOnOrder() {
        return unitsOnOrder;
    }

    public void setUnitsOnOrder(Integer unitsOnOrder) {
        this.unitsOnOrder = unitsOnOrder;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public java.lang.String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name +
                ", unitPrice=" + unitPrice +
                ", unitsInStock=" + unitsInStock +
                ", unitsOnOrder=" + unitsOnOrder +
                ", active=" + active +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        final Product other = (Product) obj;
        return other.getId() == this.getId();
    }

}
