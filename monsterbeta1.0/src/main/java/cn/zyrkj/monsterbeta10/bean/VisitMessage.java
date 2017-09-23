package cn.zyrkj.monsterbeta10.bean;

import java.util.Date;

/**
 * 访客消息
 */

public class VisitMessage {
    //访客
    private Customer customer;
    //访客评论
    private String vContent;
    //评论的用户是谁
    private Customer toUser;
    //创建时间
    private Date date;

    public VisitMessage() {
        super();
    }

    public VisitMessage(Customer customer, String vContent, Customer toUser, Date date) {
        this.customer = customer;
        this.vContent = vContent;
        this.toUser = toUser;
        this.date = date;
    }



    public String getvContent() {
        return vContent;
    }

    public void setvContent(String vContent) {
        this.vContent = vContent;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Customer getToUser() {
        return toUser;
    }

    public void setToUser(Customer toUser) {
        this.toUser = toUser;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
