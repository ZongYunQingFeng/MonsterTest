package cn.zyrkj.monsterbeta10.bean;

import java.util.Date;

/**
 * Created by Administrator on 2017/9/19.
 */

public class User {
    private int uid;
    private String username;
    private String password;
    private long date;
    private int current;

    public User() {
        super();
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.date = new Date().getTime();
        this.current = 1;
    }
    
    public User(int uid, String username, String password) {
        this.uid = uid;
        this.username = username;
        this.password = password;
        this.date = new Date().getTime();
        this.current = 1;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", date=" + date +
                ", current=" + current +
                '}';
    }
}
