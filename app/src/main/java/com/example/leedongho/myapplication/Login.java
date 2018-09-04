package com.example.leedongho.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    Button bt_name;
    TextInputEditText et_first_name;
    TextInputEditText et_last_name;
    private  String id;
    private  String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Login3.actList.add(this);
        Intent beformIntent = getIntent();
        id = beformIntent.getExtras().getString("아이디");
        Log.v("김형민id:",""+id);
        password = beformIntent.getExtras().getString("비밀번호");
        Log.v("김형민password:",""+password);
        Init();
        event();
    }

    // 초기화
    private void Init() {
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        bt_name = (Button) findViewById(R.id.bt_name);
        et_first_name = (TextInputEditText) findViewById(R.id.first_edit_text);
        et_last_name = (TextInputEditText) findViewById(R.id.name_edit_text);
    }

    private void event() {
        bt_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("성:",""+et_first_name.getText());
                Log.v("이름:",""+et_last_name.getText());
                if(!(et_first_name.getText().toString().equals(""))&&!et_last_name.getText().toString().equals("")) {
                    Intent login2_intent = new Intent(Login.this, Login2.class);
                    login2_intent.putExtra("이름",""+et_first_name.getText()+et_last_name.getText());
                    login2_intent.putExtra("회원가입 방식","이메일로그인");
                    login2_intent.putExtra("이메일",id);
                    login2_intent.putExtra("비밀번호",password);
                    startActivity(login2_intent);
                }
                else{
                    Toast.makeText(Login.this,"정보를 정확히 입력해주세요",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
