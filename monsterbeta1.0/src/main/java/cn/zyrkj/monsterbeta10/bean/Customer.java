package cn.zyrkj.monsterbeta10.bean;

import java.util.ArrayList;

/**
 * 用户信息
 */

public class Customer extends ItemType{
    //用户信息id
    private int cid;
    //用户昵称
    private String userName;
    //用户头像
    private String userImage;
    //用户绑定的产品
    private ArrayList<Product> productlist;
    //用户账户id
    private int uid;

    public Customer() {
        super();
    }

    public Customer(String userName, String userImage, ArrayList<Product> productlist) {
        this.userName = userName;
        this.userImage = userImage;
        this.productlist = productlist;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public ArrayList<Product> getProductlist() {
        return productlist;
    }

    public void setProductlist(ArrayList<Product> productlist) {
        this.productlist = productlist;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
