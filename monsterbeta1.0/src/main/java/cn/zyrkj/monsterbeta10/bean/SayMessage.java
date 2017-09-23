package cn.zyrkj.monsterbeta10.bean;

import java.util.ArrayList;
import java.util.Date;

import cn.zyrkj.monsterbeta10.model.NineGridTestModel;

/**
 * 蜜曰消息列表
 */

public class SayMessage {
    //用户
    private Customer customer;
    //内容
    private String content;
    //相册
    private NineGridTestModel nineGridTestModel;
    //访客评论
    private ArrayList<VisitMessage> visitMessage;
    //创建时间
    private Date date;
    //消息类型
    private String type;

    public SayMessage() {
        super();
    }

    public SayMessage(Customer customer, String content, NineGridTestModel nineGridTestModel, ArrayList<VisitMessage> visitMessage, Date date, String type) {
        this.customer = customer;
        this.content = content;
        this.nineGridTestModel = nineGridTestModel;
        this.visitMessage = visitMessage;
        this.date = date;
        this.type = type;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public NineGridTestModel getNineGridTestModel() {
        return nineGridTestModel;
    }

    public void setNineGridTestModel(NineGridTestModel nineGridTestModel) {
        this.nineGridTestModel = nineGridTestModel;
    }

    public ArrayList<VisitMessage> getVisitMessage() {
        return visitMessage;
    }

    public void setVisitMessage(ArrayList<VisitMessage> visitMessage) {
        this.visitMessage = visitMessage;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
