package cn.zyrkj.monsterbeta10.bean;

import java.util.ArrayList;

/**
 * 用户
 */

public class Customer {
    //用户昵称
    private String userName;
    //用户头像
    private String userImage;
    //用户绑定的产品
    private ArrayList<Product> productlist;

    public Customer() {
        super();
    }

    public Customer(String userName, String userImage, ArrayList<Product> productlist) {
        this.userName = userName;
        this.userImage = userImage;
        this.productlist = productlist;
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
}
