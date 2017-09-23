package cn.zyrkj.monsterbeta10.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.zyrkj.monsterbeta10.R;
import cn.zyrkj.monsterbeta10.bean.User;
import cn.zyrkj.monsterbeta10.okhttp.HttpCallbackListener;
import cn.zyrkj.monsterbeta10.okhttp.HttpUtil;
import cn.zyrkj.monsterbeta10.util.GetConfigUtil;

/**
 * 登录
 */

public class LoginActivity extends BaseActivity {
    private Context mContext;
    private Button loginBtn;
    private Button toRegisterBtn;
    private EditText loginUsername;
    private EditText loginPassword;
    private Intent intent;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setBackBtnAndTitle();
        initSetting();
        //检测登录是否过期
        checksCache();
        app.setIsLogin(true);
    }
    
    public void setBackBtnAndTitle() {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setTitle(R.string.login_btn);
    }
    
    public void initSetting() {
        mContext = this;
        intent = new Intent();
        
        loginBtn = (Button) findViewById(R.id.loginBtn);
        toRegisterBtn = (Button) findViewById(R.id.toRegisterBtn);

        loginUsername = (EditText) findViewById(R.id.loginUsername);
        loginPassword = (EditText) findViewById(R.id.loginPassword);
        
        //登录
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*UserDBHelper userDBHelper = new UserDBHelper(mContext, UserDBHelper.TABLE_NAME, null, UserDBHelper.DB_VERSION);
                UserDao userDao = new UserDao(userDBHelper);
                List<User> userList = userDao.QueryAllUser();*/
                
                String username = String.valueOf(loginUsername.getText());
                String password = String.valueOf(loginPassword.getText());
                User user = new User(username, password);
                String pathUrl = "LoginServlet";
                String type = "POST";

                //请求服务器
                HttpUtil.SendHttpRequest(new GetConfigUtil(mContext).serverUrl+pathUrl, user, type, new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        Looper.prepare();
                        Toast.makeText(LoginActivity.this,response,Toast.LENGTH_SHORT).show();
                        if (response.equals("登录成功")) {
                            intent.setClass(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                        Looper.loop();
                    }
                    @Override
                    public void onError(Exception e) {
                        Log.e("错误返回：", e.getMessage());
                        Looper.prepare();
                        if (e.getMessage().equals("Connection refused")) {
                            Toast.makeText(LoginActivity.this,"网络未连接...",Toast.LENGTH_SHORT).show();
                        }
                        Looper.loop();
                    }
                });

                /*if (userList != null) {
                    if (userList.size() > 0) {
                        for (User u: userList) {
                            if (user.getUsername().equals(u.getUsername())&&
                                    user.getPassword().equals(u.getPassword())) {
                                Toast.makeText(mContext, "登录成功", Toast.LENGTH_SHORT).show();
                            } else {
                                if (userList.get(userList.size()-1).equals(u)) {
                                    Toast.makeText(mContext, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                }else {
                    Toast.makeText(mContext, "用户不存在", Toast.LENGTH_SHORT).show();
                }*/
            }
        });

        //去注册
        toRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.setClass(mContext, RegisterActivity.class);
                startActivity(intent);
            }
        });
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
