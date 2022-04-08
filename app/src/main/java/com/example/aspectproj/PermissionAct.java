package com.example.aspectproj;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

public class PermissionAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        initPermissions();
    }

        //调用此方法判断是否拥有权限
    private void initPermissions() {
        //判断是否已经拥有权限
        int i = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //判断是否永久拒绝了权限
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if(!shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)){
                    //权限申请被永久拒绝
                    Uri packageURI = Uri.parse("package:" + "com.example.aspectproj");
                    Intent intent =  new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,packageURI);
                    startActivity(intent);
                }else{
                    //请求权限，第二参数权限String数据，第三个参数是请求码便于在onRequestPermissionsResult 方法中根据code进行判断
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            1);
                }
            }else{

            }
        } else {
            //拥有权限执行操作
            Toast.makeText(PermissionAct.this,"已经拥有权限",Toast.LENGTH_LONG).show();
        }
    }



    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){//响应Code
            case 1:
                if (grantResults.length > 0) {
                    for(int i = 0; i < grantResults.length; i++){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            Toast.makeText(this,"未拥有相应权限",Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                    //拥有权限执行操作
                } else {
                    Toast.makeText(this,"未拥有相应权限",Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}