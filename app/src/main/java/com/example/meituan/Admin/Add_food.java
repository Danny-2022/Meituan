package com.example.meituan.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meituan.Database.DBUtils;
import com.example.meituan.User.MainActivity;
import com.example.meituan.R;
import com.example.meituan.Tool.UritoString;
import com.example.meituan.Type;

import java.util.Calendar;

public class Add_food extends AppCompatActivity implements View.OnClickListener {
    private ImageView im_1, im_2;
    private EditText ed_title, ed_content, ed_lei;
    private int grade;
    private SeekBar ratingBar;
    private Button bt_add;
    private String imagepath = "null", imagepath2 = "null";
    private TextView tv_grade, tv_time;
    private DBUtils dbUtils;
    private String title, content, time, leibie;
    private int year, month, day, hour, min;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_food);
        initView();
        initListener();

        ratingBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tv_grade.setText(String.valueOf(i));
                grade = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    public void initView() {
        im_1 = findViewById(R.id.add_image_shu_one);
        im_2 = findViewById(R.id.add_image_shu_two);
        ed_title = findViewById(R.id.ed_shu_title);
        ed_lei = findViewById(R.id.ed_shu_leibie);
        ed_content = findViewById(R.id.ed_shu_content);
        bt_add = findViewById(R.id.bt_add_food);
        tv_grade = findViewById(R.id.tv_grade);
        tv_time = findViewById(R.id.tv_time);
        ratingBar = findViewById(R.id.seekbar);
        ratingBar.setMax(100);
        im_1.setBackgroundResource(R.drawable.add_image);
        im_2.setBackgroundResource(R.drawable.add_image);
        dbUtils = new DBUtils();
        tv_time.setText(getTime());
    }

    public void initListener() {
        im_1.setOnClickListener(this);
        bt_add.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_image_shu_one:
                im_1.setBackground(null);
                choosePhoto(Type.CHANGE_IMG_PROFILE);
                break;
            case R.id.bt_add_food:
                getText();
                if (title == null||title.length()==0 || content == null||content.length()==0 || leibie == null||leibie.length()==0) {
                    Toast.makeText(this, "Please input", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        if (dbUtils.AddFood(title, imagepath,content,leibie,0.00, grade) == 0) {
                            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Add_food.this, MainActivity.class);
                            Add_food.this.startActivity(intent);
                            Add_food.this.finish();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                break;
        }
    }

    private void choosePhoto(int type) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, type);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case Type.CHANGE_IMG_PROFILE:
                Uri profileUri = data.getData();
                imagepath = new UritoString().getPath(getApplicationContext(), profileUri);
                Bitmap bitmap = null;
                bitmap = BitmapFactory.decodeFile(imagepath);
                im_1.setImageBitmap(bitmap);
                break;
        }
    }

    public void getText() {
        title = ed_title.getText().toString();
        content = ed_content.getText().toString();
        leibie = ed_lei.getText().toString();
    }

    public String getTime() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        min = calendar.get(Calendar.MINUTE);
        time = year + "-" + month + "-" + day + " " + hour + ":" + min;
        return time;
    }
}