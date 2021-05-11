package com.deli.deliverypp.util;

import com.deli.deliverypp.DB.DeliUser;
import com.deli.deliverypp.util.annotaions.ProtectedResource;
import com.deli.deliverypp.util.annotaions.RequestUri;
import com.sun.xml.internal.txw2.IllegalAnnotationException;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.MethodParameterScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

public class MappingLoader {

    /**
     *
     */
    public static Map<String, UriSources> requestedUriProperties;
    public static Map<String , DeliUser.UserRole> protectedUriProperties;
    private static final Logger log = Logger.getGlobal();
    static {
        load();
        log.info("Mapping Load Complete");
    }

    private static void load() throws IllegalAnnotationException{
        Reflections reflections = new Reflections("com.deli.deliverypp",
                new SubTypesScanner(),
                new TypeAnnotationsScanner(),
                new MethodAnnotationsScanner(),
                new MethodParameterScanner());

        // load annotation Request uri

        Set<Method> requested = reflections.getMethodsAnnotatedWith(RequestUri.class);

        // load protected resources

        Set<Method> protectedResources = reflections.getMethodsAnnotatedWith(ProtectedResource.class);

        HashMap<String, DeliUser.UserRole> pm = new HashMap<>();
        for (Method m: protectedResources) {
            if (m.getAnnotation(RequestUri.class) == null) {
                throw new IllegalAnnotationException("must declared with RequestUri annotation");
            }
            else {
                String uri = m.getAnnotation(RequestUri.class).uri();
                pm.put(uri, m.getAnnotation(ProtectedResource.class).role());
                log.info(pm.get(uri).name());
            }
        }
        protectedUriProperties = pm;
        HashMap<String, UriSources> rm = new HashMap<>();
        for (Method m: requested) {
            String uri = m.getAnnotation(RequestUri.class).uri();
            rm.put(uri, new UriSources(m, uri, m.getAnnotation(ProtectedResource.class) != null));
        }

        requestedUriProperties = rm;
    }
}
