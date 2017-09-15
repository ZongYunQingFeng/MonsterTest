package cn.zyrkj.monstertest.bean;

import java.util.Date;

/**
 * 访客消息
 */

public class VisitMessage {
    //访客
    private User user;
    //访客评论
    private String vContent;
    //评论的用户是谁
    private User toUser;
    //创建时间
    private Date date;

    public VisitMessage() {
        super();
    }

    public VisitMessage(User user, String vContent, User toUser, Date date) {
        this.user = user;
        this.vContent = vContent;
        this.toUser = toUser;
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getvContent() {
        return vContent;
    }

    public void setvContent(String vContent) {
        this.vContent = vContent;
    }

    public User getToUser() {
        return toUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
