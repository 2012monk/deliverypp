package com.deli.deliverypp.util.annotaions;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoadConfig {
    String path() default "config.properties";
}
