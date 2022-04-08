package com.example.aspectproj.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author : Sounean
 * @Time : On 2022-04-08 19:22
 * @Description : Permission
 */
@Target(ElementType.METHOD) // 声明作用域
@Retention(RetentionPolicy.RUNTIME) // 声明生命周期（在编译和运行时都有可能会用到）
public @interface Permission {
    /*
    * 要申请的权限
    * */
    String[] value();   // 因为可能不止一个权限需要申请

    /*
    * 请求码
    * */
    int requestCode();
}
