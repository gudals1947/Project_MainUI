package com.example.leedongho.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class Login3 extends AppCompatActivity {
    private static final String TAG = "김형민";
    Button bt_birthday;
    String name;
    String mail;
    String sex;
    String method;
    String password;
    DatePicker datePicker;
    public static ArrayList<Activity> actList = new ArrayList<Activity>();
    FirebaseAuth auth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_3);
        auth = FirebaseAuth.getInstance();
        actList.add(this);
        Intent before_intent = getIntent();
        name = before_intent.getExtras().getString("이름");
        mail = before_intent.getExtras().getString("이메일");
        sex = before_intent.getExtras().getString("sex");
        method = before_intent.getExtras().getString("회원가입 방식");
        if(method.equals("이메일로그인")){
            Log.v("김형민확인비밀번호","입니다");
            password = before_intent.getExtras().getString("비밀번호");
        }
        Init();
        event();
    }


    private void Init() {
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        bt_birthday = (Button) findViewById(R.id.bt_end);
        datePicker = (DatePicker) findViewById(R.id.datePicker);
    }

    private void event() {
        bt_birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = new GregorianCalendar(Locale.KOREA);
//                Log.v("김형민 메일확인용:", "" + mail);
                if(!method.equals("이메일로그인")) {
                    UserDTO userDTO = new UserDTO(mail, name, sex, calendar.get(Calendar.YEAR) - datePicker.getYear() + 1, true, method);
                    FirebaseFirestore.getInstance().collection("user").document().set(userDTO).addOnSuccessListener(
                            new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Intent intent = new Intent(Login3.this, MainUI.class);
                                    Toast.makeText(Login3.this, "가입 성공", Toast.LENGTH_SHORT).show();
                                    startActivity(intent);
                                }
                            }
                    );
                }
                else{
                    Log.v("김형민확인","입니다");
                    createUser(mail,password);
                    UserDTO userDTO = new UserDTO(mail, name, sex, calendar.get(Calendar.YEAR) - datePicker.getYear() + 1, true, method);
                    FirebaseFirestore.getInstance().collection("user").document().set(userDTO).addOnSuccessListener(
                            new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Intent intent = new Intent(Login3.this, MainUI.class);
                                    Toast.makeText(Login3.this, "가입 성공", Toast.LENGTH_SHORT).show();
                                    startActivity(intent);
                                }
                            }
                    );
                }

                int ActivitystackNum = actList.size();
                for (int i = 0; i < ActivitystackNum; i++) {
                    actList.get(i).finish();
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

    private void createUser(final String email, final String password) {

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = auth.getCurrentUser();

                        } else {
                            // If sign in fails, display a message to the user.

                        }
                        // ...
                    }
                });
    }
}
