package cn.zyrkj.monstertest.bean;

/**
 * Created by Administrator on 2017/9/12.
 */

public class Product {
    //产品名称
    private String productName;
    //产品图片
    private String productImage;

    public Product() {
        super();
    }

    public Product(String productName, String productImage) {
        this.productName = productName;
        this.productImage = productImage;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }
}
