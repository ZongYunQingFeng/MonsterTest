package cn.zyrkj.monsterbeta10.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import cn.zyrkj.monsterbeta10.R;

/**
 * Created by Administrator on 2017/9/23.
 */

public class SettingActivity extends BaseActivity{
    private Context mContext;
    private Button loginOut;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initSetting();
        setBackBtnAndTitle();

        ///检测登录是否过期
        checksCache();
        app.setIsLogin(false);
    }

    private void initSetting() {
        mContext = this;
        intent = new Intent();
        loginOut = (Button) findViewById(R.id.loginOut);

        loginOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                }*/
                intent.setClass(mContext, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    public void setBackBtnAndTitle() {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setTitle(R.string.settingTitle);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
