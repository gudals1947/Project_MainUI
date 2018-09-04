package com.example.leedongho.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

public class Login2  extends AppCompatActivity{
    Button bt_sex;
    RadioButton rb_man;
    RadioButton rb_woman;
    String name;
    String mail;
    String method;
    String password;
    FirebaseAuth auth ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_login_2);
        Intent before_intent = getIntent();
        name = before_intent.getExtras().getString("이름");
        mail = before_intent.getExtras().getString("이메일");
        method = before_intent.getExtras().getString("회원가입 방식");
        if(method.equals("이메일로그인")){
            Log.v("김형민","이메일로그인 확인합니다.");
            password = before_intent.getExtras().getString("비밀번호");
        }
        Login3.actList.add(this);
        Init();
        event();
    }



    private void Init() {
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        bt_sex = (Button) findViewById(R.id.bt_sex);
        rb_man = (RadioButton) findViewById(R.id.rb_man);
        rb_woman = (RadioButton) findViewById(R.id.rb_woman);
    }

    private void event() {
        bt_sex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!rb_man.isChecked()&&!rb_woman.isChecked()){
                    Toast.makeText(Login2.this,"체크 확인 부탁드립니다",Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent login3_intent = new Intent(Login2.this, Login3.class);
                    login3_intent.putExtra("이메일", mail);
                    login3_intent.putExtra("이름", name);
                    login3_intent.putExtra("회원가입 방식", method);
                    login3_intent.putExtra("비밀번호",password);
                    if(rb_man.isChecked()){
                        login3_intent.putExtra("sex", "남자");
                    }
                    else if(rb_woman.isChecked()){
                        login3_intent.putExtra("sex", "여자");
                    }
                    startActivity(login3_intent);
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        auth.signOut();
        LoginManager.getInstance().logOut();
    }
}
