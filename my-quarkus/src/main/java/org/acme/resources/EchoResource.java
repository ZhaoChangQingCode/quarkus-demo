package org.acme.resources;

import java.util.*;
import org.eclipse.microprofile.jwt.*;
import io.smallrye.jwt.build.Jwt;

import java.io.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import jakarta.enterprise.context.*;
import jakarta.inject.*;

import jakarta.annotation.security.*;
import java.security.Principal;

import java.util.Date;
import java.time.*;
import java.util.concurrent.TimeUnit;

// import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import io.quarkus.security.identity.SecurityIdentity;

import org.acme.managers.*;
import org.acme.pojo.*;
import org.acme.util.*;

import java.nio.file.Files;

import static org.acme.consts.QuarkusApplicationConsts.*;

import static java.lang.System.out;

@ApplicationScoped
@Path("/echo")
public class EchoResource {
    @Inject private CustomerManager srv;
    @Inject private SecurityIdentity basic;

    @GET
    @Path("jwt")
    @Produces(MediaType.TEXT_PLAIN)
    public String jwt(@QueryParam("roles") String roles) {
        return Tokens.withRoles(roles.split(","));
    }

    @GET
    @Path("test")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public String test() throws Exception {
        return Files.readString(ROOT_PATH.resolve("index.html"));
    }

    @GET
    @Path("secured")
    @Produces(MediaType.TEXT_PLAIN)
    @RolesAllowed({"Admin", "User", "Guest"})
    public String secured(@Context SecurityContext ctx) {
        return ctx.getUserPrincipal().getName();
    }

    @GET
    @PermitAll
    @Path("cookies")
    public Response ofCookie(@QueryParam("roles") String roles) {
        var authToken = Cookies.ofSecured("authToken", Tokens.withRoles(roles.split(",")));
        return Response.noContent()
                .cookie(authToken)
                .build();
    }

    @GET
    @PermitAll
    @Path("noCookies")
    public Response ofCookieRemoval(@QueryParam("name") String name) {
        return Response.noContent()
                .cookie(Cookies.ofRemoval(name))
                .build();
    }
}
