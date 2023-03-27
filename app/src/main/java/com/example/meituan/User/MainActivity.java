package com.example.meituan.User;

import static com.example.meituan.Database.DBUtils.userid;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowId;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meituan.Adapter.FoodAdapter;
import com.example.meituan.Bean.Foodbean;
import com.example.meituan.Database.DBUtils;
import com.example.meituan.R;
import com.example.meituan.Type;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Foodbean> list_fo;
    private ListView recyclerView;
    private FoodAdapter foodAdapter;
    private Foodbean foodbean;
    private DBUtils dbUtils;
    public static List<String> food_list = new ArrayList<>();
    public  HashMap<String, Integer> Sites = new HashMap<String, Integer>();
    public static int food_sum = 0;
    private TextView tv_fo;
    private StringBuffer stringBuffer;
    private  int count;
    private AlertDialog.Builder builder;
    private View view;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.list_food);
        tv_fo = findViewById(R.id.tv_food);
        dbUtils = new DBUtils();
        try {
            list_fo = dbUtils.QueryFood();
            foodAdapter = new FoodAdapter(this,list_fo);
            recyclerView.setAdapter(foodAdapter);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this,BookFood.class);
                intent.putExtra("index",i);
                intent.putExtra("list", (Serializable) list_fo);
                intent.putExtra("num",Sites.get(list_fo.get(i).getFood_name()));
                MainActivity.this.startActivity(intent);
            }
        });
        tv_fo.setText(getData(food_list).toString()+" Total:¥"+food_sum);
    }
    public StringBuffer getData(List<String> list){
        stringBuffer = new StringBuffer();
            for (int i = 0; i < list_fo.size(); i++) {
                count = Collections.frequency(list, list_fo.get(i).getFood_name());
                if (count != 0) {
                    stringBuffer.append(list_fo.get(i).getFood_name() + "*" + count + " ");
                    Sites.put(list_fo.get(i).getFood_name(), count);
                }
            }
        return stringBuffer;
    }
    public void Buy(View view){
        if (food_sum==0){
            Toast.makeText(this, "No food was purchased", Toast.LENGTH_SHORT).show();
        }else {
            BuyFood();
        }

    }
    public void BuyFood() {
        view = LayoutInflater.from(this).inflate(R.layout.item_buy, null);
        final String[] in = new String[1];
        final TextView textView = view.findViewById(R.id.name_log);
        final RadioGroup radioGroup = view.findViewById(R.id.radio);
        final EditText editText = view.findViewById(R.id.meal_num);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.in:
                        in[0] = "in";
                        editText.setVisibility(View.VISIBLE);
                        break;
                    case R.id.out:
                        in[0] = "out";
                        editText.setVisibility(View.INVISIBLE);
                        break;
                }
            }
        });

        builder = new AlertDialog.Builder(this).setView(view).setTitle("Choose how you want to eat").setIcon(R.drawable.ic_baseline_outdoor_grill_24)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (radioGroup.getCheckedRadioButtonId()==R.id.in) {
                            if (editText.getText().toString().isEmpty()) {
                                Toast.makeText(getApplicationContext(), "please intput the table number", Toast.LENGTH_SHORT).show();
                            } else {
                                try {
                                    if (dbUtils.AddDingIn(userid,getData(food_list).toString(),food_sum,in[0], Integer.parseInt(editText.getText().toString()))==0){
                                        Toast.makeText(MainActivity.this, "Buy Failed", Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }else {
                            try {
                                if (dbUtils.AddDingIn(userid,getData(food_list).toString(),food_sum,in[0], 0)==0){
                                    Toast.makeText(MainActivity.this, "Buy Failed", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }).setNegativeButton("Canel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });;
        builder.create().show();
    }
}