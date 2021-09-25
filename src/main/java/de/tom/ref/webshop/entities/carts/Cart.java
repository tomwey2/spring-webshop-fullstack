package de.tom.ref.webshop.entities.carts;

import de.tom.ref.webshop.entities.customers.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name="carts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name="customer_id", nullable=false, unique = true)
    private Customer customer;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "last_update")
    private LocalDateTime updateDate;

    public Cart(Customer customer) {
        this.customer = customer;
        this.createDate = LocalDateTime.now();;
    }
}
