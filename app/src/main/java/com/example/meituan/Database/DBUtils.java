package com.example.meituan.Database;


import static com.example.meituan.Database.DbOpenHelper.getConnection;


import android.util.Log;


import com.example.meituan.Bean.CommentBean;
import com.example.meituan.Bean.Foodbean;
import com.example.meituan.Bean.ShuBean;
import com.example.meituan.Bean.UserBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBUtils {
    private String tableName = null;
    private int addId = 0;
    public static String username = null;
    public static String password = null;
    public static int userid = 0;
    private List<Foodbean> list_food;
    private List<CommentBean> list1;
    private List<UserBean> list2;
    public void Query(String tableName){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Connection  conn =(Connection)getConnection();
                String sql = "select * from "+tableName;
                Statement st;
                try {
                    if (conn ==null){
                        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
                        System.out.println(""+conn);
                    }else {
                        st = (Statement) conn.createStatement();
                        ResultSet rs = st.executeQuery(sql);
                        while (rs.next()) {
                            Log.i("data1", rs.getString(1));
                            Log.i("data2", rs.getString(2));
                            Log.i("data3", rs.getString(3));
                        }
                        st.close();
                        conn.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        //closeConnection();
    }
    public int RegisterUser(String name,String password,String sex,int age) throws InterruptedException {
        Thread thread =   new Thread(new Runnable() {
            @Override
            public void run() {
                Connection conn = null;
                conn =(Connection) getConnection();
                String sql = "insert into registeruser (name,password,age,sex) values(?,?,?,?)";
                PreparedStatement pst;
                try {
                    pst = (PreparedStatement) conn.prepareStatement(sql);
                    pst.setString(1,name);
                    pst.setString(2,password);
                    pst.setInt(3,age);
                    pst.setString(4,sex);
                    addId = pst.executeUpdate();
                    pst.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    Log.i("failed",e.getMessage().toString());
                }
            }
        });
        thread.start();
        thread.join();
        return addId;
    }
    public void QueryUser(String name) throws InterruptedException {
        Thread thread =  new Thread(new Runnable() {
            @Override
            public void run() {
                Connection conn = null;
                conn =(Connection) getConnection();
                String sql = "select * from registeruser where name = '"+name+"'";
                Statement st;
                try {
                    st = (Statement) conn.createStatement();
                    ResultSet rs = st.executeQuery(sql);
                    while (rs.next()) {
                        userid = rs.getInt(1);
                        username = rs.getString(2);
                        password = rs.getString(3);
                    }
                    st.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    Log.i("failed",e.getMessage().toString());
                }
            }
        });
        thread.start();
        thread.join();
    }
    public List<UserBean> QueryAllUser() throws InterruptedException {
        list2 = new ArrayList<>();
        Thread thread =  new Thread(new Runnable() {
            @Override
            public void run() {
                Connection conn = null;
                conn =(Connection) getConnection();
                String sql = "select * from registeruser ";
                Statement st;
                try {
                    st = (Statement) conn.createStatement();
                    ResultSet rs = st.executeQuery(sql);
                    String name = null;
                    int id = 0;
                    while (rs.next()) {
                        id = rs.getInt(1);
                        name = rs.getString(2);
                    UserBean userBean = new UserBean(id,name);
                    list2.add(userBean);

                    }
                    st.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    Log.i("failed",e.getMessage().toString());
                }
            }
        });
        thread.start();
        thread.join();
        return list2;
    }
    public  static String getLoginName(){
        return username;
    }
    public static String getLoginpassword(){
        return password;
    }
    public int AddShu(String name,String img1,String img2,String title,String content,int grade,String time) throws InterruptedException {
        addId = 0;
        Thread thread =   new Thread(new Runnable() {
            @Override
            public void run() {
                Connection conn = null;
                conn =(Connection) getConnection();
                String sql = "insert into shu(user,img1,img2,title,content,grade,time) values(?,?,?,?,?,?,?)";
                PreparedStatement pst;
                try {
                    pst = (PreparedStatement) conn.prepareStatement(sql);
                    //将输入的edit框的值获取并插入到数据库中
                    pst.setString(1,name);
                    pst.setString(2,img1);
                    pst.setString(3,img2);
                    pst.setString(4,title);
                    pst.setString(5,content);
                    pst.setInt(6,grade);
                    pst.setString(7,time);
                    addId = pst.executeUpdate();
                    pst.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    Log.i("failed",e.getMessage());
                }
            }
        });
        thread.start();
        thread.join();
        return addId;
    }

    public int AddComment(int id,String name,String content,String img1,int grade,String time) throws InterruptedException {
        addId = 0;
        Thread thread =   new Thread(new Runnable() {
            @Override
            public void run() {
                Connection conn = null;
                conn =(Connection) getConnection();
                String sql = "insert into comments(shuId,user,comments,img,grade,time) values(?,?,?,?,?,?)";
                PreparedStatement pst;
                try {
                    pst = (PreparedStatement) conn.prepareStatement(sql);
                    pst.setInt(1,id);
                    pst.setString(2,name);
                    pst.setString(3,content);
                    pst.setString(4,img1);
                    pst.setInt(5,grade);
                    pst.setString(6,time);
                    addId = pst.executeUpdate();
                    pst.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    Log.i("failed",e.getMessage());
                }
            }
        });
        thread.start();
        thread.join();
        return addId;
    }
    public List<CommentBean> QueryCom(int id) throws InterruptedException {
        list1 = new ArrayList<>();
        Thread thread =  new Thread(new Runnable() {
            @Override
            public void run() {
                Connection conn = null;
                conn =(Connection) getConnection();
                String sql = "select * from comments where shuId ='"+id+"'";
                Statement st;
                try {
                    st = (Statement) conn.createStatement();
                    ResultSet rs = st.executeQuery(sql);
                    while (rs.next()) {
                        String user,title,content,img1,img2,time;
                        int grade,like,id;
                        id = rs.getInt(1);
                        user= rs.getString(2);
                        content = rs.getString(3);
                        img1 = rs.getString(4);
                        grade = rs.getInt(5);
                        time = rs.getString(6);
                        CommentBean shuBean = new CommentBean(id,user,content,img1,grade,time);
                        list1.add(shuBean);
                    }
                    st.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    Log.i("failed",e.getMessage().toString());
                }
            }
        });
        thread.start();
        thread.join();
        return list1;
    }
    public int QuerySum(int id) throws InterruptedException {
         addId = 0;
                list1 = new ArrayList<>();
                final int[] sum = {0};
                Thread thread =  new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Connection conn = null;
                        conn =(Connection) getConnection();
                        String sql = "select Count(shuId) from comments where shuId ='"+id+"'";
                        Statement st;
                        PreparedStatement pst;
                        try {
                            st = (Statement) conn.createStatement();
                            ResultSet rs = st.executeQuery(sql);
                            while (rs.next()) {
                                sum[0] = rs.getInt(1);
                            }
                            Log.i("sum",String.valueOf(sum[0]));
                            st.close();
                            conn.close();
                        } catch(SQLException e) {
                    e.printStackTrace();
                    Log.i("failed",e.getMessage().toString());
                }
            }
        });
        thread.start();
        thread.join();
        return sum[0];
    }
    public int UpdateSum(int sum,int id)throws InterruptedException {
        addId = 0;
        Thread thread =   new Thread(new Runnable() {
            @Override
            public void run() {
                Connection conn = null;
                conn =(Connection) getConnection();
                String sql ="update shu set com = '"+sum+"' where shuId ='"+id+"'";
                PreparedStatement pst;
                try {
                    pst = (PreparedStatement) conn.prepareStatement(sql);
                    addId = pst.executeUpdate();
                    pst.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    Log.i("failed",e.getMessage().toString());
                }
            }
        });
        thread.start();
        thread.join();
        return addId;
    }
    public int DeleteCom(String sum)throws InterruptedException {
        addId = 0;
        Thread thread =   new Thread(new Runnable() {
            @Override
            public void run() {
                Connection conn = null;
                conn =(Connection) getConnection();
                String sql ="delete from comments where comments = '"+sum+"'";
                PreparedStatement pst;
                try {
                    pst = (PreparedStatement) conn.prepareStatement(sql);
                    addId = pst.executeUpdate();
                    pst.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    Log.i("failed",e.getMessage().toString());
                }
            }
        });
        thread.start();
        thread.join();
        return addId;
    }
    public int DeleteUser(int id)throws InterruptedException {
        addId = 0;
        Thread thread =   new Thread(new Runnable() {
            @Override
            public void run() {
                Connection conn = null;
                conn =(Connection) getConnection();
                String sql ="delete from registeruser where userId = '"+id+"'";
                PreparedStatement pst;
                try {
                    pst = (PreparedStatement) conn.prepareStatement(sql);
                    addId = pst.executeUpdate();
                    pst.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    Log.i("failed",e.getMessage().toString());
                }
            }
        });
        thread.start();
        thread.join();
        return addId;
    }
    public int AddFood(String name,String img1,String intro,String lei,double score,int price) throws InterruptedException {
        addId = 0;
        Thread thread =   new Thread(new Runnable() {
            @Override
            public void run() {
                Connection conn = null;
                conn =(Connection) getConnection();
                String sql = "insert into food(name,img,intro,lei,score,price) values(?,?,?,?,?,?)";
                PreparedStatement pst;
                try {
                    pst = (PreparedStatement) conn.prepareStatement(sql);
                    //将输入的edit框的值获取并插入到数据库中
                    pst.setString(1,name);
                    pst.setString(2,img1);
                    pst.setString(3,intro);
                    pst.setString(4,lei);
                    pst.setDouble(5,score);
                    pst.setInt(6,price);
                    addId = pst.executeUpdate();
                    pst.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    Log.i("failed",e.getMessage());
                }
            }
        });
        thread.start();
        thread.join();
        return addId;
    }
    public List<Foodbean> QueryFood() throws InterruptedException {
        list_food = new ArrayList<>();
        Thread thread =  new Thread(new Runnable() {
            @Override
            public void run() {
                Connection conn = null;
                conn =(Connection) getConnection();
                String sql = "select * from food";
                Statement st;
                try {
                    st = (Statement) conn.createStatement();
                    ResultSet rs = st.executeQuery(sql);
                    while (rs.next()) {
                        String name,img,intro,lei;
                        int id,price;
                        double score;
                        id = rs.getInt(1);
                        name= rs.getString(2);
                        img = rs.getString(3);
                        intro = rs.getString(4);
                        price = rs.getInt(7);
                        score = rs.getDouble(6);
                        lei = rs.getString(5);
                        Foodbean foodbean = new Foodbean(id,name,img,intro,lei,score,price);
                        list_food.add(foodbean);
                    }
                    st.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    Log.i("failed",e.getMessage().toString());
                }
            }
        });
        thread.start();
        thread.join();
        return list_food;
    }
    public int AddDingIn(int  id,String food,int price,String fangshi,int zhuohao) throws InterruptedException {
        addId = 0;
        Thread thread =   new Thread(new Runnable() {
            @Override
            public void run() {
                Connection conn = null;
                conn =(Connection) getConnection();
                String sql = "insert into dingdan(userid,food,price,fangshi,zhuohao) values(?,?,?,?,?)";
                PreparedStatement pst;
                try {
                    pst = (PreparedStatement) conn.prepareStatement(sql);
                    //将输入的edit框的值获取并插入到数据库中
                    pst.setInt(1,id);
                    pst.setString(2,food);
                    pst.setInt(3,price);
                    pst.setString(4,fangshi);
                    pst.setInt(5,zhuohao);
                    addId = pst.executeUpdate();
                    pst.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    Log.i("failed",e.getMessage());
                }
            }
        });
        thread.start();
        thread.join();
        return addId;
    }
    public int AddDingOut(int  id,String food,int price,String fangshi,int zhuohao) throws InterruptedException {
        addId = 0;
        Thread thread =   new Thread(new Runnable() {
            @Override
            public void run() {
                Connection conn = null;
                conn =(Connection) getConnection();
                String sql = "insert into dingdan(userid,food,price,fangshi,zhuohao) values(?,?,?,?,?)";
                PreparedStatement pst;
                try {
                    pst = (PreparedStatement) conn.prepareStatement(sql);
                    //将输入的edit框的值获取并插入到数据库中
                    pst.setInt(1,id);
                    pst.setString(2,food);
                    pst.setInt(3,price);
                    pst.setString(4,fangshi);
                    pst.setInt(5,zhuohao);
                    addId = pst.executeUpdate();
                    pst.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    Log.i("failed",e.getMessage());
                }
            }
        });
        thread.start();
        thread.join();
        return addId;
    }
}

