package org.acme.pojo;

import jakarta.persistence.*;
// import jakarta.json.bind.annotation.*;
import com.fasterxml.jackson.annotation.*;

import java.io.Serializable;
import java.util.*;

@Cacheable
@Entity
@Table(name="orders")
// @NamedQueries({
//     @NamedQuery(name="Order.findByCustomerId", query="SELECT o FROM Order o WHERE o.customer.id = :id")
// })
public class Order implements Serializable {
    @JsonProperty("id")
    @Id
    @GeneratedValue
    int id;

    @JsonProperty("orderTime")
    @Temporal(TemporalType.DATE)
    @Column(name="order_time")
    Date orderTime;

    @ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinColumn(name="customer_id", referencedColumnName="id")
    Customer customer;

    public Date getOrderTime()    { return orderTime; }
    // public Customer getCustomer() { return customer;}
}
