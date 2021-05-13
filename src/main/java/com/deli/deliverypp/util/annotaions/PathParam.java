package com.deli.deliverypp.util.annotaions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({
        ElementType.TYPE_PARAMETER,
        ElementType.LOCAL_VARIABLE,
        ElementType.PARAMETER
})
@Retention(RetentionPolicy.RUNTIME)
public @interface PathParam {
    String param();
}
