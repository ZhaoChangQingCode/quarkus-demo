package org.acme.managers;

import jakarta.persistence.*;
import jakarta.enterprise.context.*;
import jakarta.transaction.Transactional;
import jakarta.validation.*;


import java.util.*;

import org.acme.pojo.*;

@ApplicationScoped
public class CustomerManager {
    @PersistenceContext
    private EntityManager em;

    private static final Class<Customer> TYPE = Customer.class;

    public List<Customer> findAll() {
        return em.createNamedQuery("Customer.findAll", TYPE).getResultList();
    }

    public Customer findById(Integer id) {
        return em.find(TYPE, id);
    }

    public List<Customer> findByName(String name) {
        return em.createNamedQuery("Customer.findByName", TYPE)
                .setParameter("name", name)
                .getResultList();
    }

    @Transactional
    public void persist(@Valid Customer entity) {
        em.persist(entity);
    }

    @Transactional
    public void remove(int id) {
        em.remove(findById(id));
    }

    @Transactional
    public void refresh(int id, Customer modified) {
        Customer entity = findById(id);
        entity.setName(modified.getName());
        em.refresh(entity);
    }
}
