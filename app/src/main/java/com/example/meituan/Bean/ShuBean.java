package com.example.meituan.Bean;

import java.io.Serializable;

public class ShuBean implements Serializable {
    private int shuid;
    private String user;
    private String title;
    private String content;
    private String img_1,img_2;
    private int grade;
    private int like;
    private String time;
    public ShuBean(int shuid,String user,String title,String content,String img_1,String img_2,int grade,String time,int like){
        this.shuid = shuid;
        this.user = user;
        this.title = title;
        this.content = content;
        this.img_1 = img_1;
        this.img_2 = img_2;
        this.grade = grade;
        this.time = time;
        this.like = like;

    }

    public String getTime() {
        return time;
    }

    public String getContent() {
        return content;
    }

    public int getGrade() {
        return grade;
    }

    public int getLike() {
        return like;
    }

    public int getShuid() {
        return shuid;
    }

    public String getImg_1() {
        return img_1;
    }

    public String getImg_2() {
        return img_2;
    }

    public String getTitle() {
        return title;
    }

    public String getUser() {
        return user;
    }

}
