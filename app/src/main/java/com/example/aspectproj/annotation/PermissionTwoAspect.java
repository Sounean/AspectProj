package com.example.aspectproj.annotation;


import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.example.aspectproj.PermissionAct;
import com.example.aspectproj.activity.ApplyPermissionAct;
import com.example.aspectproj.interfaces.PermissionRequestCallback;
import com.example.aspectproj.util.PermissionUtil;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class PermissionTwoAspect {

    // 通过注解的方式，获取切入点(@Permission * * (..))的解释：@注解 被该注解标记的任何返回类型 任何方法 （任何参数）)
    // @annotation(permission)的解释：获取含有前面所指的@Permission注解的xx上所保存的值（此处指value和requestCode），这样子注解上给的值，也传进来了
    @Pointcut("execution(@com.example.aspectproj.annotation.Permission * * (..)) && @annotation(permissions)")
    public void getPermissionTwoWork(Permission permissions){}   // 此处传参名字要和上面获取注解保存值时新建的对象名字一样

    @Around("getPermissionTwoWork(permissions)")
    public void invokGetPermission(ProceedingJoinPoint proceedingJoinPoint,Permission permissions){
        // 需求是在方法执行钱 要去进行权限申请，如果权限申请成功，就接着执行，如果权限申请失败，就不执行
        // 1.获取上下文
        Context context = null;
        Object aThis = proceedingJoinPoint.getThis();// 获取切入点所在的对象
        if (aThis instanceof Context){  //1.1得先判断含有切入点方法的类是不是一个正常的Act（万一是工具类，就根本获取不到上下文）
            context = (Context) aThis;
        }else if ( aThis instanceof Fragment){  // 1.2 如果含有切入点方法的是一个Frag，那也无法直接获得上下文
            context = ((Fragment)aThis).getActivity();
        }

        if(context==null || permissions == null || permissions.value()==null || permissions.value().length==0){ // 3.如果没有上下文；没有注解；者注解里，需要申请的权限没有，就不需要下面申请权限的过程
            return;
        }

        // 2.获取到权限
        String[] value = permissions.value();
        int requesCode = permissions.requestCode();
        ApplyPermissionAct.launchActivity(context, value, requesCode, new PermissionRequestCallback() {
            @Override
            public void permissionSuccess() {
                // 如果申请成功,执行原有的逻辑
                try {
                    proceedingJoinPoint.proceed();
                } catch (Throwable throwable) {

                }
            }

            @Override
            public void permissionCanceled() {
                // 权限申请被拒绝怎么办
                //反射执行permissionCanceled()方法
                PermissionUtil.invokeAnnotation(aThis,PermissionFailed.class);
            }

            @Override
            public void permissionDenied() {
                //权限申请被拒绝并且永久不提示怎么办
                Log.e("MN-------->","权限申请结果：被永久拒绝");
                //反射执行permissionDenied()方法
                PermissionUtil.invokeAnnotation(aThis,PermissionDenied.class);
            }
        });



        Log.e("---->","有被执行到4");
    }


}
