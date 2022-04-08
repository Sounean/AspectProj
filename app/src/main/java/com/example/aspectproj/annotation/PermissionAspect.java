package com.example.aspectproj.annotation;


import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class PermissionAspect {

    @Pointcut("execution(* com.example.aspectproj.PermissionAct.initPermissions (..))")   // 通过注解的方式，获取切入点(@注解 任何返回类型 任何方法 （任何参数）)
    public void getPermissionWork(){}

    @After("getPermissionWork()")
    public void invokGetPermission(){
        Log.e("AspectJ--->", "申请权限 " );
    }
}
