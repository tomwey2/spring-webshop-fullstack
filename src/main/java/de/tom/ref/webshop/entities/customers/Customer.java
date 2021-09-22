package de.tom.ref.webshop.entities.customers;

import de.tom.ref.webshop.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name="customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Integer id;

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "email", length = 50)
    private String email;

    private String password;

    @Column(nullable = false)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean enabled = false;

    @Column(nullable = false)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean locked = false;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "last_update")
    private LocalDateTime updateDate;

    // this information is not used
    public boolean getAccountExpired() { return false; }
    public boolean getCredentialsExpired() { return false; }

    public Customer(String name, String email, String password, UserRole userRole) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.userRole = userRole;
        this.enabled = false;
        this.locked = false;
        this.createDate = LocalDateTime.now();
    }

    public Customer(String name, String email, String password, UserRole userRole, Boolean enabled, Boolean locked) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.locked = locked;
        this.userRole = userRole;
    }

    // additional fields for future:
    /*
    @Column(name = "first_name", length = 50)
    private String firstName;

    @Column(name = "last_name", length = 50)
    private String lastName;

    @Column(name = "address_line1", length = 50)
    private String addressLine1;

    @Column(name = "address_line2", length = 50)
    private String addressLine2;

    @Column(name = "city", length = 50)
    private String city;

    @Column(name = "postal_code", length = 10)
    private String postalCode;

    @Column(name = "country", length = 50)
    private String country;
    */

}
