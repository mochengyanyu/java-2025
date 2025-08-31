package com.wk.dynamic.annon;

import com.wk.dynamic.enums.DBEnums;

import java.lang.annotation.*;

@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DS {

    DBEnums value() default DBEnums.DB1;

}
