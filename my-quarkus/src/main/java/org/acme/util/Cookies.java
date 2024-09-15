package org.acme.util;

import jakarta.servlet.http.Cookie;

import jakarta.ws.rs.core.NewCookie;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.time.*;

public class Cookies {

    public static final long DEFAULT_EXPIRY = TimeUnit.DAYS.toSeconds(7);

    public static NewCookie ofRemoval(String name) {
        return new NewCookie.Builder(name)
                .maxAge(0)
                .expiry(Date.from(Instant.EPOCH))
                .path("/")
                .build();
    }

    public static NewCookie ofSecured(String name, String value) {
        return new NewCookie.Builder(name).value(value)
                .httpOnly(true)
                .maxAge((int)DEFAULT_EXPIRY)
                .path("/")
                .sameSite(NewCookie.SameSite.STRICT)
                .build();
    }

    public static NewCookie ofUnsecured(String name, String value) {
        return new NewCookie.Builder(name).value(value)
                .httpOnly(false)
                .maxAge((int)DEFAULT_EXPIRY)
                .path("/")
                .sameSite(NewCookie.SameSite.STRICT)
                .build();
    }

    public static Optional<String> get(Cookie[] cookies, String name) {
        return Arrays.stream(cookies)
                .filter(c -> c.getName().equals(name))
                .findFirst()
                .map(c -> c.getValue());
    }
}
