package org.legion.util;

import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@Slf4j
public class Cache implements Serializable {
    private static Cache instance = null;

    private Cache() {
    }

    public static synchronized Cache getInstance() {
        if (instance == null) {
            instance = new Cache();
        }
        return instance;
    }
}
