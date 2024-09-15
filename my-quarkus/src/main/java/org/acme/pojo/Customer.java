package org.acme.pojo;

import jakarta.persistence.*;
import jakarta.json.bind.annotation.*;
import com.fasterxml.jackson.annotation.*;

import java.io.Serializable;
import java.util.*;

@Cacheable
@Entity
@Table(name="customers")
@NamedQueries({
    // SELECT
    @NamedQuery(name="Customer.findAll", query="SELECT c FROM Customer c"),
    @NamedQuery(name="Customer.findById", query="SELECT c FROM Customer c WHERE c.id = :id"),
    @NamedQuery(name="Customer.findByName", query="SELECT c FROM Customer c WHERE c.name = :name"),
})
public class Customer implements Serializable {
    // @JsonbProperty("id")
    @Id
    @GeneratedValue
    int id;

    // @JsonbProperty("name")
    @Column
    String name;

    // @JsonbProperty("orders")
    @OneToMany(mappedBy="customer", cascade=CascadeType.ALL, orphanRemoval=true, fetch=FetchType.LAZY)
    List<Order> orders = new ArrayList<>();

    public int getId()                    { return id; }
    public String getName()               { return name; }
    public void setName(String newName) { name = newName; }
    public List<Order> getOrders()        { return orders; }

    @Override
    public String toString() {
        return "%d -- %s".formatted(id, name);
    }
}
