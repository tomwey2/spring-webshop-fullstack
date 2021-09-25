package de.tom.ref.webshop.entities.order;

import de.tom.ref.webshop.entities.customers.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name="customer_id", nullable=false, unique = true)
    private Customer customer;

    @Column(name = "order_number", nullable=false, unique = true)
    private String number;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "last_update")
    private LocalDateTime updateDate;

    public Order(Customer customer, String number) {
        this.customer = customer;
        this.number = number;
        this.createDate = LocalDateTime.now();
    }

}
