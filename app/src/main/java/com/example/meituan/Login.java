package com.example.meituan;



import static com.example.meituan.Database.DBUtils.getLoginName;
import static com.example.meituan.Database.DBUtils.getLoginpassword;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.meituan.Database.DBUtils;
import com.example.meituan.Database.SP;
import com.example.meituan.User.MainActivity;


public class Login extends AppCompatActivity implements View.OnClickListener {
    private EditText Et_login_name,Et_login_pass;
    private Button Bt_login;
    private String sc_num,sc_password;
    private CheckBox cb_pass,cb_admin;
    private boolean aBoolean,admin;
    private String user,password;
    private TextView textView;
    private SP sp;
    private Boolean login,rem;
    private DBUtils dbUtils;
    public static String LoginUser;
    public static Boolean Admin = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initListener();
        user = sp.getUserName();
        password = sp.getUserPassword();
        login = sp.getLogin();
        rem = sp.getRem();
        if (rem == true){
            Intent intent = new Intent();
            intent.setClass(Login.this, MainActivity.class);
            Login.this.startActivity(intent);
            Login.this.finish();
        }else {
            if (login == true) {
                Et_login_name.setText(user);
                Et_login_pass.setText(password);
                cb_pass.setChecked(true);
                aBoolean = true;
            } else {

            }
            if (getIntent().getStringExtra("name") == null) {

            } else {
                user = getIntent().getStringExtra("name");
                password = getIntent().getStringExtra("password");
                Et_login_name.setText(user);
                Et_login_pass.setText(password);
            }
        }
    }
    public void initView(){
        Et_login_name = findViewById(R.id.login_name);
        Et_login_pass = findViewById(R.id.login_password);
        Bt_login = findViewById(R.id.login_button);
        cb_pass = findViewById(R.id.cb_password);
        cb_admin = findViewById(R.id.cb_admin);
        textView = findViewById(R.id.register);
        sp = new SP(getApplicationContext());
        aBoolean = false;
        admin = false;
    }
    public void initListener() {
        textView.setOnClickListener(this);
        Bt_login.setOnClickListener(this);
        cb_pass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (cb_pass.isChecked()){
                    aBoolean = true;
                }else {
                    aBoolean = false;
                }
            }
        });
        cb_admin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (cb_pass.isChecked()){
                    admin = true;
                }else {
                    admin = false;
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_button:
                getText();
                if (sc_num.isEmpty()||sc_password.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please intput full information",Toast.LENGTH_SHORT).show();
                }else {
                    dbUtils = new DBUtils();
                    try {
                        if (admin) {
                            if (sc_num.equals("admin")&&sc_password.equals("admin")){
                                Toast.makeText(this, "Login successful by Admin", Toast.LENGTH_SHORT).show();
                                Admin = true;
                                Intent intent = new Intent(Login.this, MainActivity.class);
                                Login.this.startActivity(intent);
                                Login.this.finish();
                            }else {
                                Toast.makeText(this, "The information is incorrect", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            dbUtils.QueryUser(sc_num);
                            if (sc_num.equals(getLoginName())) {
                                if (sc_password.equals(getLoginpassword())) {
                                    sp.SaveUser(sc_num, sc_password, aBoolean);
                                    LoginUser = sc_num;
                                    Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Login.this, MainActivity.class);
                                    Login.this.startActivity(intent);
                                    Login.this.finish();
                                } else {
                                    Toast.makeText(this, "The password is incorrect", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(this, "Please register first", Toast.LENGTH_SHORT).show();
                            }
                        }
                        } catch(InterruptedException e){
                            e.printStackTrace();
                        }
                }
                break;
            case R.id.register:
                Intent intent = new Intent();
                intent.setClass(Login.this, Register.class);
                Login.this.startActivity(intent);
                Login.this.finish();
                break;

        }

    }
    public void getText(){
        sc_num = Et_login_name.getText().toString();
        sc_password = Et_login_pass.getText().toString();
    }

}