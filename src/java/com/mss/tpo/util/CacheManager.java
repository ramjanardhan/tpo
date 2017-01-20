package com.mss.tpo.util;

import java.util.Collections;
import java.util.Map;
import java.util.HashMap;

public class CacheManager {
    /*
     * Cache to store the datasource objects for improving performance by
     * avoided repetative calls to the JNDI registry
     */

    private static Map<String, Object> cache;

    /** Creates a new instance of ApplicationCacheManager */
    private CacheManager() {
    }

    /**
     * @return An instance of the Cache Map
     * @throws ServiceLocatorException
     */
    public static Map<String, Object> getCache() throws ServiceLocatorException {
        try {
            if (cache == null) {
                cache = Collections.synchronizedMap(new HashMap<String, Object>());
            }
        } catch (Exception ex) {
            throw new ServiceLocatorException(ex);
        }
        return cache;
    }
}
