package com.example.leedongho.myapplication;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class AttentionPower extends AppCompatActivity {
    Random rnd = new Random();
    TextView tv_question, tv_answer, tv_count;
    Button bt_o, bt_x;
    Boolean answer = false;
    int score = 0;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_attention_power);
        init();
        event();
    }

    // 초기화
    private void init() {
        bt_o = (Button) findViewById(R.id.bt_o);
        bt_x = (Button) findViewById(R.id.bt_x);
        tv_question = (TextView) findViewById(R.id.tv_question);
        tv_answer = (TextView) findViewById(R.id.tv_answer);
        answer = false;
        score = 0;
        count = 0;
        tv_count = (TextView) findViewById(R.id.tv_count);
        tv_count.setText(score + " / 10");
        setTitle("AttentionPower - " + (count+1) + " 번째 문제");
        setQeustion();
    }

    // 실질적인 메인
    private void event() {
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = (String)((Button) v).getText();
                if(str.equals("O") && answer == true || str.equals("X") && answer == false){
                    score++;
                    Toast.makeText(AttentionPower.this, "정답! 맞춘 갯수 : " + score + "개", Toast.LENGTH_SHORT).show();
                    setQeustion();
                } else {
                    Toast.makeText(AttentionPower.this, "오답! 맞춘 갯수 : " + score + "개", Toast.LENGTH_SHORT).show();
                    setQeustion();
                }
                tv_count.setText(score + " / 10");
                count++;
                setTitle("AttentionPower - " + (count+1) + " 번째 문제");
                if (count == 10) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(AttentionPower.this);
                    dialog.setTitle("게임 끝");
                    dialog.setMessage("기록 : " + score + " / 10");
                    dialog.setPositiveButton("한판 더", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    dialog.setNegativeButton("나가기", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                    dialog.show();
                    init();
                }
            }
        };
        bt_o.setOnClickListener(clickListener);
        bt_x.setOnClickListener(clickListener);
    }

    // 문제준비
    private void setQeustion() {
        tv_answer.setText(" ");

        // 문제 색 설정
        int tmp_q = rnd.nextInt(4);
        if(tmp_q == 0){
            tv_question.setTextColor(0xffFF0000);
        } else if(tmp_q == 1){
            tv_question.setTextColor(0xffFFFF00);
        } else if(tmp_q == 2){
            tv_question.setTextColor(0xff008000);
        } else if(tmp_q == 3){
            tv_question.setTextColor(0xff0000FF);
        }

        // 보기 글자색 설정
        int tmp_a1 = rnd.nextInt(4);
        if(tmp_a1 == 0){
            tv_answer.setTextColor(0xffFF0000);
        } else if(tmp_a1 == 1){
            tv_answer.setTextColor(0xffFFFF00);
        } else if(tmp_a1 == 2){
            tv_answer.setTextColor(0xff008000);
        } else if(tmp_a1 == 3){
            tv_answer.setTextColor(0xff0000FF);
        }

        // 보기 글자 설정
        int tmp_a2 = rnd.nextInt(4);
        if(tmp_a2 == 0){
            tv_answer.setText("빨강");
        } else if(tmp_a2 == 1){
            tv_answer.setText("노랑");
        } else if(tmp_a2 == 2){
            tv_answer.setText("초록");
        } else if(tmp_a2 == 3){
            tv_answer.setText("파랑");
        }

        // 문제와 보기가 같은 색이면 정답
        if(tmp_q == tmp_a2){
           answer = true;
        } else {
            answer = false;
        }
    }
}
