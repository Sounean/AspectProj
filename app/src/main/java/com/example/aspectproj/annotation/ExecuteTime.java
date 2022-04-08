package com.example.aspectproj.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author : Sounean
 * @Time : On 2022-04-07 23:15
 * @Description : ExecuteTime
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface ExecuteTime {
}
