package com.example.aspectproj.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.aspectproj.interfaces.PermissionRequestCallback;
import com.example.aspectproj.util.PermissionUtil;

/**
 * @Author : Sounean
 * @Time : On 2022-04-08 19:54
 * @Description : 为了支持织入类申请权限(需要一个上下文来调用)而新建的一个透明类.在这个类里我们
 * 尽管能获取到权限申请的回调
 * @Warn: 别忘了写进清单里
 */
public class ApplyPermissionAct extends AppCompatActivity {
    /**
     * 请求的权限
     */
    public static final String REQUEST_PERMISSIONS = "request_permissions";

    //请求码
    public static final String REQUEST_CODE = "request_code";
    //默认请求码
    public static final int REQUEST_CODE_DEFAULT = -1;
    private static PermissionRequestCallback requestCallback;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if(intent!=null){
            //1.获取到意图传递过来的权限的数组
            String[] permissionArr = intent.getStringArrayExtra(REQUEST_PERMISSIONS);
            int request_code = intent.getIntExtra(REQUEST_CODE, REQUEST_CODE_DEFAULT);
            //三者一项不符合，结束当前页面
            if (permissionArr == null || request_code == -1 || requestCallback == null) {
                this.finish();
                return;
            }
            // 2.开始执行申请权限的流程
            //2.1判断是否已经授权了
            if(PermissionUtil.hasPermissionRequest(this,permissionArr)){
                requestCallback.permissionSuccess();
                this.finish();
                return;
            }
            //2.2如果没有，就开始申请权限 （系统的方法，只会对当前还未申请的权限进行申请）
            ActivityCompat.requestPermissions(this,permissionArr,request_code);
        }else {
            finish();
        }
    }

    public static void launchActivity(Context context, String[] permissions, int requestCode,
                                      PermissionRequestCallback callback){
        // 跳转到ApplyPermissionAct中
        requestCallback = callback;

        Bundle bundle = new Bundle();
        bundle.putStringArray(REQUEST_PERMISSIONS, permissions);    // 权限申请的集合
        bundle.putInt(REQUEST_CODE, requestCode);   // 请求码的集合
        Intent intent = new Intent();
        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setClass(context, ApplyPermissionAct.class);
        context.startActivity(intent);
    }

    /*
    * 权限申请后的回调方法
    * */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //再次判断是否权限真正的申请成功
        if(PermissionUtil.requestPermissionSuccess(grantResults)){
            requestCallback.permissionSuccess();
            this.finish();
            return;
        }
        //权限永久拒绝，并且用户勾选了不在提示
        if(PermissionUtil.shouldShowRequestPermissionRationale(this,permissions)){
            requestCallback.permissionDenied();
            this.finish();
            return;
        }
        //权限本次拒绝（不是永久拒绝）
        requestCallback.permissionCanceled();
        this.finish();
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0,0);
    }
}
