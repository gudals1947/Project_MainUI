package com.example.leedongho.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginPassword extends AppCompatActivity {
    private EditText edPassword;
    private Button btPassword;
    private String id;
    public static final Pattern VALID_PASSWORD_REGEX_ALPHJA_NUM = Pattern.compile("^[a-zA-Z0-9!@.#$%^&*?_~]{7,16}$");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_password);
        Login3.actList.add(this);
        Intent beformIntent = getIntent();
        id = beformIntent.getExtras().getString("아이디");

        init();
        start();
    }
    void init(){
        edPassword = (EditText) findViewById(R.id.ed_password);
        btPassword = (Button) findViewById(R.id.bt_password);
    }
    void start(){
        btPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!edPassword.equals("")){
                    if(validatePassword(edPassword.getText().toString())){
                        Intent intent = new Intent(getApplication(),Login.class);
                        intent.putExtra("아이디",id);
                        intent.putExtra("비밀번호",edPassword.getText().toString());
                        startActivity(intent);
                    }
                    else if(!validatePassword(edPassword.getText().toString())){
                        Toast.makeText(LoginPassword.this,"패스워드는 4~16자입니다.",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(LoginPassword.this,"비밀번호를 입력하세요",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public boolean validatePassword(String pwStr){
            Matcher matcher = VALID_PASSWORD_REGEX_ALPHJA_NUM.matcher(pwStr);
            return  matcher.matches();
    }
}
