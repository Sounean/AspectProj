package com.example.aspectproj;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @Author : Sounean
 * @Time : On 2022-04-07 22:46
 * @Description : 1.声明织入类：TimeAspect织入类,用了@Aspect注释，会让ajc，在项目开始时遍历所有的类，
 * 看哪些类用了这个注解，就不然让那些类经过javac编译了；而是让AspectJ的编译器去编译。
 */
@Aspect
public class TimeAspect {
    // 1通过这个注解，可以声明切入点,在括号里，传字符串参数  用AspectJ提供的通配符语法
    // 可以用call或execution通配符，call只是调用切入点的方法，execution是得到这个切入点的方法。
    // 此处用了execution：用execution(方法返回值 包名类名一直到方法名(传参))    *表示任意对象 ..表示不管多少传参，都可以用这个表示
    // 声明了getTimeWork()表示切入之后的操作
    //@Pointcut("execution(* com.example.aspectproj.MainActivity.getTime(..))") // 具体的某个包某个类的某个方法
    @Pointcut("execution(@ExecuteTime * * (..))")   // 通过注解的方式，获取切入点(@注解 任何返回类型 任何方法 （任何参数）)
    public void getTimeWork(){}

    // 2 新建通知：（3种类型 before、after、around）
    // 把上面的切点交给了around。
    // 参数中的proceedingJoinPoint对象其实就是注解中的“getTimeWork()”，此时才是将对象传进去了
    // 3.Joint Point（连接点）：方法体中的proceedingJoinPoint.proceed()其实就是执行原先切入点的方法
    @Around("getTimeWork()")
    public void invokGetTime(ProceedingJoinPoint proceedingJoinPoint){
        long startTime = System.currentTimeMillis();

        try {
            proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        long endTime = System.currentTimeMillis();
        Log.e("AspectJ--->", "正常方式执行时间: "+(endTime-startTime) );
    }


}
