import jakarta.enterprise.context.*;
import jakarta.persistence.*;

import java.util.*;

import org.acme.pojo.*;

@ApplicationScoped
public class OrderManager {
    @PersistenceContext
    private EntityManager em;

    private static final Class<Order> TYPE = Order.class;

    // public List<Order> findByCustomerId(Integer id) {
    //     return em.createNamedQuery("Order.findByCustomerId", TYPE)
    //             .setParameter("id", id)
    //             .getResultList();
    // }
}
