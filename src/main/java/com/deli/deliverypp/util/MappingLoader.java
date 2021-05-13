package com.deli.deliverypp.util;

import com.deli.deliverypp.DB.DeliUser;
import com.deli.deliverypp.util.annotaions.ProtectedResource;
import com.deli.deliverypp.util.annotaions.RequestUri;
import com.deli.deliverypp.util.annotaions.RequiredModel;
import com.sun.xml.internal.txw2.IllegalAnnotationException;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.MethodParameterScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

public class MappingLoader {

    /**
     *
     */
    public static Map<String, UriSources> requestedUriProperties;
    public static Map<String , DeliUser.UserRole> protectedUriProperties;
    public static Map<String, ProtectedResource> resources;
    public static Map<String, Class<?>> requiredModel;
    private static final Logger log = Logger.getGlobal();
    static {
        try {
            load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("Mapping Load Complete");
    }

    private static void load() {
        Reflections reflections = new Reflections("com.deli.deliverypp",
                new SubTypesScanner(),
                new TypeAnnotationsScanner(),
                new MethodAnnotationsScanner(),
                new MethodParameterScanner());

        // load annotation Request uri

        Set<Method> requested = reflections.getMethodsAnnotatedWith(RequestUri.class);

        // load protected resources

        Set<Method> protectedResources = reflections.getMethodsAnnotatedWith(ProtectedResource.class);
        HashMap<String, ProtectedResource> rem = new HashMap<>();
        HashMap<String, DeliUser.UserRole> pm = new HashMap<>();
        HashMap<String, Class<?>> rc = new HashMap<>();
        for (Method m: protectedResources) {
//            String uri = m.getAnnotation(RequestUri.class).uri();
//            pm.put(uri, m.getAnnotation(ProtectedResource.class).role());
//            log.info(pm.get(uri).name());
//            if (m.getAnnotation(RequestUri.class) == null) {
//                throw new IllegalAnnotationException("must declared with RequestUri annotation");
//            }
//            else {
//            }

            String i = m.getAnnotation(ProtectedResource.class).uri();
            ProtectedResource p = m.getAnnotation(ProtectedResource.class);
            log.info(p.uri());
            String key = i+" "+p.method().toLowerCase(Locale.ROOT);
            rem.put(key, p);

            try {
                RequiredModel r = m.getAnnotation(RequiredModel.class);
                if (r != null) {
                    rc.put(key, r.target());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        resources = rem;
        protectedUriProperties = pm;
        HashMap<String, UriSources> rm = new HashMap<>();
        for (Method m: requested) {
            String uri = m.getAnnotation(RequestUri.class).uri();
            rm.put(uri, new UriSources(m, uri, m.getAnnotation(ProtectedResource.class) != null));
        }

        requestedUriProperties = rm;
    }
}
