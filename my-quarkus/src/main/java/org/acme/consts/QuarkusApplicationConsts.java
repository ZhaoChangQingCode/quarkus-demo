package org.acme.consts;

import java.nio.file.*;

public class QuarkusApplicationConsts {
    public static final String ROOT = "META-INF/resources/";
    public static final Path ROOT_PATH;

    static {
        try {
            ROOT_PATH = Path.of(QuarkusApplicationConsts.class.getClassLoader().getResource(ROOT).toURI());
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }
}
