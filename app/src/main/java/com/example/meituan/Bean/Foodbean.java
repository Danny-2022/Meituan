package com.example.meituan.Bean;

import java.io.Serializable;

public class Foodbean implements Serializable {
    private int food_id;
    private String food_name;
    private String food_img;
    private String food_intro;
    private String food_lei;
    private double food_score;
    private int food_price;
    private int path;
    public Foodbean(int id,String name,String img,String intro,String lei,double score,int price){
        food_id = id;
        food_name = name;
        food_img = img;
        food_intro = intro;
        food_lei = lei;
        food_score = score;
        food_price = price;
    }

    public int getPath() {
        return path;
    }

    public Foodbean(int img, String food_name){
        this.food_name = food_name;
        this.path = img;
    }

    public double getFood_score() {
        return food_score;
    }

    public int getFood_id() {
        return food_id;
    }

    public int getFood_price() {
        return food_price;
    }

    public String getFood_img() {
        return food_img;
    }

    public String getFood_intro() {
        return food_intro;
    }

    public String getFood_lei() {
        return food_lei;
    }

    public String getFood_name() {
        return food_name;
    }
}
