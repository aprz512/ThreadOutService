package com.sf.threadoutservice;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.orhanobut.logger.Logger;
import com.sf.threadoutservice.service.KeepAliveService;
import com.sf.threadoutservice.task.LocationTask;
import com.sf.threadoutservice.task.SomethingTask;
import com.sf.threadoutservice.task.TaskHelper;

import java.util.concurrent.Executors;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

import static com.sf.threadoutservice.MainActivityPermissionsDispatcher.requestStoragePermissionWithPermissionCheck;

@RuntimePermissions
public class MainActivity extends AppCompatActivity {

    private static volatile boolean isActivityAlive = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestStoragePermissionWithPermissionCheck(this);
        setContentView(R.layout.activity_main);
        Executors.newSingleThreadExecutor().submit(new LoopTask());
//        startService(new Intent(this, KeepAliveService.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isActivityAlive = false;
    }

    private static class LoopTask implements Runnable {

        @Override
        public void run() {
            while (isActivityAlive) {
                try {
                    SystemClock.sleep(3000L);
                    TaskHelper.submitTask(new LocationTask());
                    TaskHelper.submitTask(new SomethingTask());
                } catch (Exception e) {
                    Logger.e(e.getMessage());
                }
            }
        }
    }

    @SuppressLint("NeedOnRequestPermissionsResult")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        onRequestPermissionsResult(requestCode, grantResults);
    }

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public void requestStoragePermission() {

    }

    @OnShowRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public void showRationaleForStorage(PermissionRequest request) {
        Toast.makeText(this, "行行好，给个权限吧，要记录日志", Toast.LENGTH_SHORT).show();
    }

    @OnPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public void onStorageDenied() {
        Toast.makeText(this, "拒绝了权限申请", Toast.LENGTH_SHORT).show();
    }

    @OnNeverAskAgain(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public void onStorageNeverAskAgain() {
        Toast.makeText(this, "不要再问了", Toast.LENGTH_SHORT).show();
    }
}
