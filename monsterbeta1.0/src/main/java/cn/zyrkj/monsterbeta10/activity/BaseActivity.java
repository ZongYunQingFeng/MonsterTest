package cn.zyrkj.monsterbeta10.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.Date;

import cn.zyrkj.monsterbeta10.SQLiteDB.UserDBHelper;
import cn.zyrkj.monsterbeta10.SQLiteDB.UserDao;
import cn.zyrkj.monsterbeta10.bean.User;
import cn.zyrkj.monsterbeta10.okhttp.StateCheckCallbackListener;
import cn.zyrkj.monsterbeta10.util.GetConfigUtil;
import cn.zyrkj.monsterbeta10.util.LoginStateApplication;

/**
 * Created by Administrator on 2017/9/23.
 */

public class BaseActivity extends AppCompatActivity {
    private LoginStateApplication app;
    private long CACHE_TIME;

    private Context mContext;
    private Intent intent;

    //检查网络状态
    private IntentFilter intentFilter;
    private NetworkChangeReceiver networkChangeReceiver;
    
    //检查登录
    private UserDBHelper userDBHelper;
    private UserDao userDao;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        intent = new Intent();

        CACHE_TIME = new GetConfigUtil(this).cacheTime;
        initCheckState();
    }

    public void checkLogin(StateCheckCallbackListener listener) {
        Log.e("State Network",String.valueOf(getApp().isNetworkState()));
        Log.e("State Login",String.valueOf(getApp().isLoginState()));
        Log.e("State Valid",String.valueOf(getApp().isLoginStateValid()));
        //检查是否登录且有效
        if (getApp().isLoginState()&&getApp().isLoginStateValid()){
            if (listener != null) {
                listener.onValid();
            }
        }else {
            if (listener != null) {
                listener.onInvalid();
            }
        }
    }

    //检查网络状态
    private void checkNetworkState() {
        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        networkChangeReceiver = new NetworkChangeReceiver();
        registerReceiver(networkChangeReceiver, intentFilter);
    }
    class NetworkChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            if (networkInfo!=null && networkInfo.isAvailable()) {
                getApp().setNetworkState(true);
                Log.i("CheckState Network1",String.valueOf(getApp().isNetworkState()));
            } else {
                getApp().setNetworkState(false);
                Log.i("CheckState Network2",String.valueOf(getApp().isNetworkState()));
            }
        }

    }
    
    //检查是否登录
    private void checkLoginState() {
        boolean b = false;
        if (user != null) {
            b = user.getCurrent() == 1 ? true : false;
        }
        //数据库没有数据则肯定没有登录的
        getApp().setLoginState(b);
        Log.i("CheckState Login",String.valueOf(getApp().isLoginState()));
    }

    //检测登录是否过期登录
    public void checkLoginStateValid() {
        //判断时间是否超过CACHE_TIME
        boolean b = false;
        if (user != null) {
            Log.i("today",String.valueOf(new Date().getTime()));
            Log.i("anotherday",String.valueOf(user.getDate()));
            Log.i("CACHE_TIME",String.valueOf(CACHE_TIME));
            long today = new Date().getTime();
            long anotherday = user.getDate();
            long time = today - anotherday;
            Log.i("time",String.valueOf(time));
            b = new Date().getTime() - user.getDate() > CACHE_TIME ? false : true;
        }
        Log.i("boolean b",String.valueOf(b));
        getApp().setLoginStateValid(b);
        Log.i("CheckState Valid",String.valueOf(getApp().isLoginStateValid()));
    }
    
    private void queryUser() {
        userDBHelper = new UserDBHelper(mContext, UserDBHelper.TABLE_NAME, null, UserDBHelper.DB_VERSION);
        userDao = new UserDao(userDBHelper);
        user = userDao.QueryCurrentUser();
        Log.i("user == null?",String.valueOf(user==null));
    }
    
    public void initCheckState() {
        checkNetworkState();
        queryUser();
        checkLoginState();
        checkLoginStateValid();
    }
    
    public LoginStateApplication getApp() {
        if (this.app == null) {
            this.app = (LoginStateApplication) getApplication();
        }
        return this.app;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkChangeReceiver);
    }
}
