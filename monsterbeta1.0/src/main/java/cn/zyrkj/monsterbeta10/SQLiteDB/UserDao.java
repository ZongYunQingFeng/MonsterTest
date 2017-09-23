package cn.zyrkj.monsterbeta10.SQLiteDB;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import cn.zyrkj.monsterbeta10.bean.User;

/**
 * Created by Administrator on 2017/9/19.
 */

public class UserDao {
    private UserDBHelper userDBHelper;
    private SQLiteDatabase db;

    public UserDao(UserDBHelper userDBHelper) {
        this.userDBHelper = userDBHelper;
    }
    
    //增
    public void InsertUser(User user) {
        //得到一个可写的数据库  
        db = userDBHelper.getWritableDatabase();

        //生成ContentValues对象 //key:列名，value:想插入的值   
        ContentValues cv = new ContentValues();
        
        //往ContentValues对象存放数据，键-值对模式  
        cv.put("id", user.getUid());
        cv.put("username", user.getUsername());
        cv.put("password", user.getPassword());
        
        //调用insert方法，将数据插入数据库  
        db.insert(userDBHelper.TABLE_NAME, null, cv);
        
        //关闭数据库  
        if (db != null) {
            db.close();
        }
    }
    
    //删
    public void DeleteUser(int id) {
        //得到一个可写的数据库  
        db = userDBHelper.getReadableDatabase();
        String whereClauses = "id=?";
        String [] whereArgs = {String.valueOf(id)};
        
        //调用delete方法，删除数据   
        db.delete(userDBHelper.TABLE_NAME, whereClauses, whereArgs);
        if (db != null) {
            db.close();
        }
    }
    
    //改
    public void ModifyUser(User user) {
        db = userDBHelper.getWritableDatabase();
        
        ContentValues cv = new ContentValues();
        cv.put("username", user.getUsername());
        cv.put("password", user.getPassword());
        
        //where 子句 "?"是占位符号，对应后面的"1",  
        String whereClause="id=?";
        String [] whereArgs = {String.valueOf(user.getUid())};
        //参数1 是要更新的表名  
        //参数2 是一个ContentValeus对象  
        //参数3 是where子句  
        db.update(userDBHelper.TABLE_NAME, cv, whereClause, whereArgs);
        if (db != null) {
            db.close();
        }
    }
        
    //查
    public List<User> QueryAllUser() {
        db = userDBHelper.getReadableDatabase();
        //参数1：表名   
        //参数2：要想显示的列   
        //参数3：where子句   
        //参数4：where子句对应的条件值   
        //参数5：分组方式   
        //参数6：having条件   
        //参数7：排序方式   
        Cursor cursor = db.query(userDBHelper.TABLE_NAME, new String[]{"id","username","password"}, null, null, null, null, null);
        if (cursor.getCount() > 0) {
            List<User> userList = new ArrayList<>();
            while(cursor.moveToNext()){
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String username = cursor.getString(cursor.getColumnIndex("username"));
                String password = cursor.getString(cursor.getColumnIndex("password"));
                User user = new User(id, username, password);
                userList.add(user);
            }
            return userList;
        }
        
        //关闭数据库  
        if (cursor != null) {
            cursor.close();
        }
        if (db != null) {
            db.close();
        }
        return null;
    }
}
