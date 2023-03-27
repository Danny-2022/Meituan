package com.example.meituan.Bean;

public class CommentBean {
    private int shuId;
    private String user;
    private String comments;
    private String img;
    private int grade;
    private String time;
    public CommentBean(int shuId,String user,String comments,String img,int grade,String time){
        this.shuId = shuId;
        this.user = user;
        this.img = img;
        this.comments = comments;
        this.grade = grade;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public String getUser() {
        return user;
    }

    public int getGrade() {
        return grade;
    }

    public int getShuId() {
        return shuId;
    }

    public String getComments() {
        return comments;
    }

    public String getImg() {
        return img;
    }
}
