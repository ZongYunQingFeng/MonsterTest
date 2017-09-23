package cn.zyrkj.monsterbeta10.okhttp;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import cn.zyrkj.monsterbeta10.bean.User;

/**
 * Created by Administrator on 2017/9/20.
 */

public class HttpUtil {
    //使用
    public static void SendHttpRequest(final String address, final User user, final String type, final HttpCallbackListener listener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try{
                    URL url =  new URL(address);
                    connection = (HttpURLConnection)url.openConnection();
                    connection.setRequestMethod(type);
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);

                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    
                    //判断get还是post
                    if (type.equalsIgnoreCase("GET")) {
                        
                    }else if (type.equalsIgnoreCase("POST")) {
                        OutputStream out = connection.getOutputStream();
                        String content = "username=" + user.getUsername() + "&password=" + user.getPassword();// 无论服务器转码与否，这里不需要转码，因为Android系统自动已经转码为utf-8啦  
                        out.write(content.getBytes());
                        out.flush();
                        out.close();
                    }
                    
                    InputStream in = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null){
                        response.append(line);
                    }
                    if (listener != null){
                        listener.onFinish(response.toString());
                    }
                }catch (Exception e){
                    if (listener != null){
                        listener.onError(e);
                    }
                }finally {
                    if (connection != null){
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }
    
    //解析JSON
    public static void parseJsonUseGson(String jsonData){
        Gson gson = new Gson();
        List<User> userList = gson.fromJson(jsonData, new TypeToken<List<User>>(){}.getType());
        for (User user: userList){
            Log.e("User", "id: " + user.getUid());
            Log.e("User", "username: " + user.getUsername());
            Log.e("User", "password: " + user.getPassword());
        }
    }
    
    //生成JSON
    public static void buildJsonUseGson(User user){
        Gson gson = new Gson();
        String jsonObject = gson.toJson(user);
        Log.e("jsonObject", "jsonObject: " + jsonObject);
    }
}
