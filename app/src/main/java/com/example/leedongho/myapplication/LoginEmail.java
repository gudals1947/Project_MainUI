package com.example.leedongho.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginEmail extends AppCompatActivity {
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private TextView ed_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_email);
        ed_email = (TextView) findViewById(R.id.ed_email);
        Button bt_email = (Button) findViewById(R.id.bt_email);
        Login3.actList.add(this);
        bt_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!ed_email.getText().equals("")) {
                    FirebaseFirestore.getInstance().collection("user").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        boolean choice = false;
                        @Override
                        public void onSuccess(QuerySnapshot documentSnapshots) {
                            for (DocumentChange documentChange : documentSnapshots.getDocumentChanges()) {
                                String isAttendance = documentChange.getDocument().getData().get("mail").toString();
                                if (isAttendance.equals(ed_email.getText().toString())) {
                                    Toast.makeText(LoginEmail.this, "중복된 아이디입니다.", Toast.LENGTH_SHORT).show();
                                    choice = true;
                                    break;
                                }
                            }
                            if (checkEmail(validateEmail(ed_email.getText().toString()), ed_email.getText().toString())&&choice==false) {
                                Intent intent = new Intent(LoginEmail.this, LoginPassword.class);
                                intent.putExtra("아이디", ed_email.getText().toString());
                                startActivity(intent);
                            }
                        }
                    });

                } else {
                    Toast.makeText(LoginEmail.this, "이메일을 입력해주세요.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean checkEmail(boolean b, final String edemail) {
        boolean checkemail = false;
        if (b) {
            checkemail = true;

        } else {
            Toast.makeText(LoginEmail.this, "이메일 양식을 확인해주세요.",
                    Toast.LENGTH_SHORT).show();
            checkemail = false;
        }
        return checkemail;
    }

    public boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

}
