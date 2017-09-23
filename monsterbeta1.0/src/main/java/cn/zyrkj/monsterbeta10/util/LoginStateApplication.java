package cn.zyrkj.monsterbeta10.util;

import android.app.Application;

/**
 * Created by Administrator on 2017/9/23.
 */

public class LoginStateApplication extends Application {
    private boolean isLogin;

    @Override
    public void onCreate() {
        super.onCreate();
        setIsLogin(false);
    }

    public boolean getIsLogin() {
        return this.isLogin;
    }

    public void setIsLogin(boolean isLogin) {
        this.isLogin = isLogin;
    }
}
