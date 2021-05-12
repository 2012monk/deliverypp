package com.deli.deliverypp.util.annotaions;

import com.deli.deliverypp.DB.DeliUser;

import javax.servlet.annotation.WebServlet;
import java.lang.annotation.*;

/**
 * declare protected resource
 *
 */
@Target({
        ElementType.TYPE,
        ElementType.METHOD
})
@Retention(RetentionPolicy.RUNTIME)
public @interface ProtectedResource {
    String redirect() default "/";
    DeliUser.UserRole role() default DeliUser.UserRole.CLIENT;
    String method() default "get";
    boolean id() default false;
    String uri();
}
