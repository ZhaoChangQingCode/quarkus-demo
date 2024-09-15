package org.acme.resources;

import jakarta.persistence.*;
import jakarta.enterprise.context.*;
import jakarta.ws.rs.core.*;
import jakarta.ws.rs.*;
import jakarta.inject.*;
import jakarta.transaction.SystemException;
import jakarta.annotation.security.*;

import java.security.*;

import java.util.*;
import java.time.*;

import org.acme.pojo.*;
import org.acme.managers.*;

import org.eclipse.microprofile.jwt.JsonWebToken;

// import javax.cache.annotation.*;
import io.quarkus.cache.*;

import static org.acme.consts.Role.*;

@RequestScoped
@Path("/customer")
@Produces(MediaType.APPLICATION_JSON)
@DeclareRoles({GUEST, USER, ADMIN})
public class CustomerResource {
    @Inject private CustomerManager srv;
    @Inject private JsonWebToken jwt;

    /*
     * GET /customer/all
     */
    @GET
    @Path("all")
    @RolesAllowed({USER, ADMIN})
    @CacheResult(cacheName="customer")
    public List<Customer> findAll() {
        return srv.findAll();
    }

    /*
     * GET /customer/{id}
     */
    @GET
    @Path("{id}")
    @CacheResult(cacheName="customer")
    @RolesAllowed({USER, ADMIN})
    public Customer findById(@CacheKey @PathParam("id") int id) {
        return srv.findById(id);
    }

    /*
     * GET /customer?name={}
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @CacheResult(cacheName="customer")
    @RolesAllowed({USER, ADMIN})
    public List<Customer> findByName(@CacheKey @QueryParam("name") String name) {
        return srv.findByName(name);
    }

    /*
     * POST /customer
     */
    // @CacheRemoveAll(cacheName = "expensiveResourceCache")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({ADMIN})
    public void persist(Customer customer) throws SystemException {
        srv.persist(customer);
    }

    /*
     * DELETE /customer/{id}
     */
    @DELETE
    @Path("{id}")
    @CacheInvalidate(cacheName="customer")
    @RolesAllowed({ADMIN})
    public void remove(@CacheKey @PathParam("id") int id) {
        srv.remove(id);
    }

    /*
     * PUT /customer/{id}
     */
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    // @CachePut(cacheName="customers")
    public void refresh(@CacheKey @PathParam("id") int id, @BeanParam Customer entity) {
        srv.refresh(id, entity);
    }
}
