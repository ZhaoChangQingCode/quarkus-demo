package org.acme.util;

import io.smallrye.jwt.build.Jwt;
import java.util.*;

public class Tokens {
    private Tokens() {}

    public static String withRoles(String... roles) {
        return Jwt.issuer("https://example.com/issuer")
                .upn("jdoe@quarkus.io")
                .groups(Set.of(roles))
                .sign();
    }
}
