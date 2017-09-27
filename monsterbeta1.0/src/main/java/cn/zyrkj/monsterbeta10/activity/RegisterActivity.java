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

import java.util.Date;
import java.util.List;

import cn.zyrkj.monsterbeta10.R;
import cn.zyrkj.monsterbeta10.SQLiteDB.UserDBHelper;
import cn.zyrkj.monsterbeta10.SQLiteDB.UserDao;
import cn.zyrkj.monsterbeta10.bean.User;
import cn.zyrkj.monsterbeta10.okhttp.HttpCallbackListener;
import cn.zyrkj.monsterbeta10.okhttp.HttpUtil;
import cn.zyrkj.monsterbeta10.util.GetConfigUtil;
import cn.zyrkj.monsterbeta10.util.LoginStateApplication;

/**
 * 注册
 */

public class RegisterActivity extends BaseActivity {
    private Context mContext;
    private Button registerBtn;
    private EditText registerUsername;
    private EditText registerPassword;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setBackBtnAndTitle();
        initSetting();

        //检测登录是否过期
    }

    public void setBackBtnAndTitle() {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setTitle(R.string.register_btn);
    }

    public void initSetting() {
        mContext = this;
        intent = new Intent();

        registerBtn = (Button) findViewById(R.id.registerBtn);

        registerUsername = (EditText) findViewById(R.id.registerUsername);
        registerPassword = (EditText) findViewById(R.id.registerPassword);

        //注册
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!getApp().isNetworkState()) {
                    Toast.makeText(mContext,"网络未连接",Toast.LENGTH_SHORT).show();
                    return;
                }
                
                String username = String.valueOf(registerUsername.getText()).trim();
                String password = String.valueOf(registerPassword.getText()).trim();
                final User user = new User(username, password);
                String pathUrl = "RegisterServlet";
                String type = "POST";

                //请求服务器
                HttpUtil.SendHttpRequest(new GetConfigUtil(mContext).serverUrl+pathUrl, user, type, new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        Looper.prepare();
                        Toast.makeText(RegisterActivity.this,response,Toast.LENGTH_SHORT).show();
                        if (response.equals("注册成功")) {
                            UserDBHelper userDBHelper = new UserDBHelper(mContext, UserDBHelper.TABLE_NAME, null, UserDBHelper.DB_VERSION);
                            UserDao userDao = new UserDao(userDBHelper);
                            //将该用户置为当前用户状态,其他用户置为非当前状态
                            List<User> userList = userDao.QueryAllUser();
                            for (User u: userList
                                    ) {
                                u.setCurrent(0);
                                userDao.ModifyUser(u);
                            }
                            User userQueryByName = userDao.QueryUserByName(user.getUsername());
                            userQueryByName.setCurrent(1);
                            userQueryByName.setDate(new Date().getTime());
                            userDao.ModifyUser(userQueryByName);
                            
                            intent.setClass(mContext, MainActivity.class);
                            startActivity(intent);
                        }
                        Looper.loop();
                    }
                    @Override
                    public void onError(Exception e) {
                        Log.e("错误返回：", e.getMessage());
                        Looper.prepare();
                        if (e.getMessage().indexOf("Network is unreachable") != -1) {
                            Toast.makeText(RegisterActivity.this,"网络未连接，请检查是否打开网络...",Toast.LENGTH_SHORT).show();
                        } else if (e.getMessage().indexOf("Connection refused") != -1) {
                            Toast.makeText(RegisterActivity.this,"服务器拒绝连接...",Toast.LENGTH_SHORT).show();
                        } else if (e.getMessage().indexOf("Connection timed out") != -1) {
                            Toast.makeText(RegisterActivity.this,"连接超时，请检查当前网络是否稳定...",Toast.LENGTH_SHORT).show();
                        }
                        Looper.loop();
                    }
                });
                /*if (userList != null) {
                    if (userList.size() > 0) {
                        for (User u: userList) {
                            if (user.getUsername().equals(u.getUsername())&&
                                    user.getPassword().equals(u.getPassword())) {
                                Toast.makeText(mContext, "用户名已被注册", Toast.LENGTH_SHORT).show();
                            } else {
                                if (userList.get(userList.size()-1).equals(u)) {
                                    Toast.makeText(mContext, "注册成功", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                }else {
                    Toast.makeText(mContext, "注册成功", Toast.LENGTH_SHORT).show();
                }*/
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
