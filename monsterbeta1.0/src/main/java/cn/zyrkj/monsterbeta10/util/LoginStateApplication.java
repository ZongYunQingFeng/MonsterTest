package cn.zyrkj.monsterbeta10.util;

import android.app.Application;

import cn.zyrkj.monsterbeta10.SQLiteDB.UserDao;

/**
 * Created by Administrator on 2017/9/23.
 */

public class LoginStateApplication extends Application {
    //登录是否过期
    private boolean loginStateValid;
    //是否登录
    private boolean loginState;
    //网络是否连接
    private boolean networkState;

    @Override
    public void onCreate() {
        super.onCreate();
        setLoginStateValid(false);
        setLoginState(false);
        setNetworkState(false);
    }

    public boolean isLoginStateValid() {
        return loginStateValid;
    }

    public void setLoginStateValid(boolean loginStateValid) {
        this.loginStateValid = loginStateValid;
    }

    public boolean isLoginState() {
        return loginState;
    }

    public void setLoginState(boolean loginState) {
        this.loginState = loginState;
    }

    public boolean isNetworkState() {
        return networkState;
    }

    public void setNetworkState(boolean networkState) {
        this.networkState = networkState;
    }
}
