package com.example.meituan.User;

import static com.example.meituan.User.MainActivity.food_list;
import static com.example.meituan.User.MainActivity.food_sum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.meituan.Bean.Foodbean;
import com.example.meituan.Database.DBUtils;
import com.example.meituan.R;

import java.util.List;

public class BookFood extends AppCompatActivity implements View.OnClickListener {
    private int index;
    private List<Foodbean> list;
    private ImageView im_1, im_2;
    private TextView ed_title, ed_content, ed_lei;
    private int grade,price;
    private SeekBar ratingBar;
    private Button bt_add;
    private String imagepath = "null", imagepath2 = "null";
    private TextView tv_grade, tv_time,tv_num;
    private DBUtils dbUtils;
    private String title, content, time, leibie,img;
    private int year, month, day, hour, min;
    private ImageButton remove,add;
    private int num = 0;
    private int sum = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_food);
        initView();
        initListener();
        title = list.get(index).getFood_name();
        content = list.get(index).getFood_intro();
        price = list.get(index).getFood_price();
        leibie = list.get(index).getFood_lei();
        img = list.get(index).getFood_img();

        im_1.setImageBitmap(BitmapFactory.decodeFile(img));
        ed_lei.setText(leibie);
        ed_title.setText(title);
        ed_content.setText(content);

        sum = num*price;
        tv_num.setText(String.valueOf(num));
        tv_grade.setText("¥:"+sum);

    }
    public void initView(){
        im_1 = findViewById(R.id.add_image_shu_one);
        im_2 = findViewById(R.id.add_image_shu_two);
        ed_title = findViewById(R.id.ed_shu_title);
        ed_lei = findViewById(R.id.ed_shu_leibie);
        ed_content = findViewById(R.id.ed_shu_content);
        bt_add = findViewById(R.id.bt_add_food);
        tv_num = findViewById(R.id.num);
        tv_grade = findViewById(R.id.tv_grade);
        tv_time = findViewById(R.id.tv_time);
        remove = findViewById(R.id.sp_remove);
        add = findViewById(R.id.sp_add);
        im_1.setBackgroundResource(R.drawable.add_image);
        dbUtils = new DBUtils();
        index = getIntent().getIntExtra("index",0);
        list = (List<Foodbean>) getIntent().getSerializableExtra("list");
        num = getIntent().getIntExtra("num",0);
    }
    public void initListener(){
        bt_add.setOnClickListener(this);
        remove.setOnClickListener(this);
        add.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_add_food:
                Intent intent = new Intent();
                intent.setClass(BookFood.this,MainActivity.class);
                BookFood.this.startActivity(intent);
                BookFood.this.finish();
                break;
            case R.id.sp_remove:
                if (num==0||sum<0){

                }else {
                    tv_num.setText(String.valueOf(--num));
                    sum = num*price;
                    tv_grade.setText("¥:"+sum);
                    food_list.remove(title);
                    food_sum -= price;
                    if (food_sum<0){
                        food_sum=0;
                    }
                }
                break;
            case R.id.sp_add:
                tv_num.setText(String.valueOf(++num));
                sum = num*price;
                tv_grade.setText("¥:"+sum);
                food_list.add(title);
                food_sum += price;
                break;
        }


    }
}