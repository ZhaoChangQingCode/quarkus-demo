import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import jakarta.annotation.security.*;

import java.nio.channels.*;
import java.nio.charset.*;
// import java.nio.file.Files;
import org.acme.io.Files;
import java.nio.*;
import java.io.*;
import java.util.*;

import jakarta.enterprise.context.*;
import jakarta.inject.Inject;

import org.eclipse.microprofile.jwt.JsonWebToken;
import io.smallrye.jwt.auth.principal.JWTParser;

import org.acme.util.*;

import static org.acme.consts.QuarkusApplicationConsts.*;
import static org.acme.consts.Role.*;

@ApplicationScoped
@Path("/restricted2")
@Produces(MediaType.TEXT_HTML)
public class IndexResource {
    @Inject private JWTParser parser;

    @GET
    @Path("{page}")
    public Response index(@CookieParam("authToken") String token, @PathParam("page") String page) {
        try {
            // 参数 token 永远不为 null，但会是字符串字面量 "null"，很奇怪。我猜是因为内部调用了 String#valueOf 吧。
            return (authenticate(token, Set.of(ADMIN, USER)) && !token.equals("null"))
                ? Response.ok(Files.readString(ROOT_PATH.resolve("restricted").resolve(page))).build()
                : Response.status(401).build();
        } catch (IOException e) {
            return Response.status(404).entity(e.getMessage()).build();
        }
    }

    private boolean authenticate(String token, Set<String> rolesAllowed) {
        try {
            return Utils.containsAny(parser.parse(token).getGroups(), rolesAllowed);
        } catch (Exception e) {
            return false;
        }
    }
}
