package cn.zyrkj.monsterbeta10.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nostra13.universalimageloader.utils.L;

import java.util.Date;
import java.util.List;

import cn.zyrkj.monsterbeta10.R;
import cn.zyrkj.monsterbeta10.SQLiteDB.UserDBHelper;
import cn.zyrkj.monsterbeta10.SQLiteDB.UserDao;
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

        intent = getIntent();
        //从Intent当中根据key取得value
        if (intent != null) {
            boolean value = intent.getBooleanExtra("canBackMain",true);
            Log.i("canBackMain",String.valueOf(value));
            if (value) {
                setBackBtnAndTitle();
            } else {
                setTitle("登录");
            }
        }
        initSetting();
    }
    
    //自定义标题栏与返回按钮
    public void setBackBtnAndTitle() {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setTitle(R.string.login_btn);
    }

    /*//禁用返回键
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return false;
    }*/
    
    public void initSetting() {
        mContext = this;
        
        loginBtn = (Button) findViewById(R.id.loginBtn);
        toRegisterBtn = (Button) findViewById(R.id.toRegisterBtn);

        loginUsername = (EditText) findViewById(R.id.loginUsername);
        loginPassword = (EditText) findViewById(R.id.loginPassword);
        
        //登录
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Login Network", String.valueOf(getApp().isNetworkState()));
                if (!getApp().isNetworkState()) {
                    Toast.makeText(mContext,"网络未连接",Toast.LENGTH_SHORT).show();
                    return;
                }
                
                String username = String.valueOf(loginUsername.getText()).trim();
                String password = String.valueOf(loginPassword.getText()).trim();
                final User user = new User(username, password);
                String pathUrl = "LoginServlet";
                String type = "POST";

                //请求服务器
                HttpUtil.SendHttpRequest(new GetConfigUtil(mContext).serverUrl+pathUrl, user, type, new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        Looper.prepare();
                        Toast.makeText(LoginActivity.this,response,Toast.LENGTH_SHORT).show();
                        Log.e("response message",response);
                        if (response.equals("登录成功")) {
                            getApp().setLoginState(true);
                            getApp().setLoginStateValid(true);
                            Log.e("loginAt login",String.valueOf(getApp().isLoginState()));
                            Log.e("loginAt valid",String.valueOf(getApp().isLoginStateValid()));
                            UserDBHelper userDBHelper = new UserDBHelper(mContext, UserDBHelper.TABLE_NAME, null, UserDBHelper.DB_VERSION);
                            UserDao userDao = new UserDao(userDBHelper);
                            //将该用户置为当前用户状态,其他用户置为非当前状态
                            List<User> userList = userDao.QueryAllUser();
                            if (userList.size() > 0) {
                                for (User u: userList
                                        ) {
                                    if (!u.getUsername().equals(user.getUsername())) {
                                        Log.e("u",u.toString());
                                        u.setCurrent(0);
                                        userDao.ModifyUser(u);
                                    }
                                }
                                User userQueryByName = userDao.QueryUserByName(user.getUsername());
                                if (userQueryByName == null) {
                                    userDao.InsertUser(user);
                                }else {
                                    userQueryByName.setCurrent(1);
                                    userQueryByName.setDate(new Date().getTime());
                                    userDao.ModifyUser(userQueryByName);
                                }
                            } else {
                                userDao.InsertUser(user);
                            }
                            
                            intent.setClass(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        Looper.loop();
                    }
                    @Override
                    public void onError(Exception e) {
                        Log.e("错误返回：", e.getMessage());
                        Looper.prepare();
                        if (e.getMessage().indexOf("Network is unreachable") != -1) {
                            Toast.makeText(LoginActivity.this,"网络未连接，请检查是否打开网络...",Toast.LENGTH_SHORT).show();
                        } else if (e.getMessage().indexOf("Connection refused") != -1) {
                            Toast.makeText(LoginActivity.this,"服务器拒绝连接...",Toast.LENGTH_SHORT).show();
                        } else if (e.getMessage().indexOf("Connection timed out") != -1) {
                            Toast.makeText(LoginActivity.this,"连接超时，请检查当前网络是否稳定...",Toast.LENGTH_SHORT).show();
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
