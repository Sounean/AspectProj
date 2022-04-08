package com.example.aspectproj;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aspectproj.annotation.Permission;
import com.example.aspectproj.annotation.PermissionDenied;
import com.example.aspectproj.annotation.PermissionFailed;
import com.example.aspectproj.util.PermissionUtil;

/*
*分析：切入点为：getSd() 所需要的方法和请求码都以注解的方式保存
* */
public class PermissionActTwo extends AppCompatActivity {

    //private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission_act_two);
        TextView tx = findViewById(R.id.textTwo);
        tx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSd();
                Log.e("---->","11111");
            }
        });
        Button btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri packageURI = Uri.parse("package:" + "com.example.aspectproj");
                Intent intent =  new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,packageURI);
                startActivity(intent);
            }
        });
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }

    // 加载一个注解
    @Permission(value = "android.permission.READ_EXTERNAL_STORAGE",requestCode = 1)
    private void getSd() {
        // 具体的读写操作
        Log.e("---->","有被执行到");
    }


    @PermissionFailed(requestCode = 1)
    private void requestPermissionFailed() {
        Toast.makeText(this, "用户拒绝了权限", Toast.LENGTH_SHORT).show();
    }

    @PermissionDenied(requestCode = 1)
    private void requestPermissionDenied() {
        Toast.makeText(this, "权限申请失败,不再询问", Toast.LENGTH_SHORT).show();
        //开发者可以根据自己的需求看是否需要跳转到设置页面去
        PermissionUtil.startAndroidSettings(this);
    }

}