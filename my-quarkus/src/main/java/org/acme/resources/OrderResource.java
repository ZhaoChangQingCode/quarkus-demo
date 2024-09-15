import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import jakarta.enterprise.context.*;
import jakarta.inject.*;

import java.util.*;

import org.acme.pojo.*;

@RequestScoped
@Path("/order")
@Produces(MediaType.APPLICATION_JSON)
public class OrderResource {
    @Inject
    private OrderManager srv;

    // @GET
    // @Path("{id}")
    // public List<Order> findByCustomerId(@PathParam("id") Integer id) {
    //     return srv.findByCustomerId(id);
    // }
}
