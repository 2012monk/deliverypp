package com.deli.deliverypp.util.annotaions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({
        ElementType.FIELD,
        ElementType.PARAMETER,
        ElementType.TYPE_PARAMETER,
        ElementType.METHOD
})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiredResource {
}
