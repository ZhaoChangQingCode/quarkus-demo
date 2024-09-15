package org.acme;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import org.acme.GreetingService;

@ApplicationScoped
@Path("/hello")
public class GreetingResource {

    @Inject
    @Named("srv")
    private GreetingService srv;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return srv.greet();
    }
}
