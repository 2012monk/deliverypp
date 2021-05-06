package com.deli.deliverypp.util;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoadConfig {
    String path() default "config.properties";
}
