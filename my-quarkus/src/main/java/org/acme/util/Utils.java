package org.acme.util;

import java.util.*;

public class Utils {
    private Utils() {}

    public static final <T> T requireNonNull(T obj) {
        Objects.requireNonNull(obj);
        if ((obj instanceof String str) && str.equals("null"))
            throw new NullPointerException();
        return obj;
    }

    public static boolean containsAny(Set<String> s1, Set<String> s2) {
        Iterator<String> it = s1.iterator();
        while (it.hasNext()) {
            if (s2.contains(it.next()))
                return true;
        }
        return false;
    }
}
