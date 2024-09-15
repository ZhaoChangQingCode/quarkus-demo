package org.acme;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@Named("srv")
@ApplicationScoped
public class GreetingService {
    public String greet() {
        return "Hello from Quarkus REST";
    }
}
