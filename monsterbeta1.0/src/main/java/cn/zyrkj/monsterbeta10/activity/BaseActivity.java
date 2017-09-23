package cn.zyrkj.monsterbeta10.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import cn.zyrkj.monsterbeta10.util.LoginStateApplication;

/**
 * Created by Administrator on 2017/9/23.
 */

public class BaseActivity extends AppCompatActivity {
    public LoginStateApplication app;
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    //检测缓存是否过期登录
    public void checksCache() {
        app = (LoginStateApplication) getApplication();
        boolean isLogin = app.getIsLogin();
        Toast.makeText(this,""+isLogin,Toast.LENGTH_SHORT).show();
    }
}
