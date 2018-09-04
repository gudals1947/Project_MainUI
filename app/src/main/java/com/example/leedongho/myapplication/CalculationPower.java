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

public class CalculationPower extends AppCompatActivity {
    Random rnd = new Random();
    TextView tv_question1, tv_question2, tv_question3, tv_answer2, tv_count;
    Button bt_0, bt_1, bt_2, bt_3, bt_4, bt_5, bt_6, bt_7, bt_8, bt_9, bt_next, bt_clear;
    int answer = 0;
    int score = 0;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_calculation_power);
        init();
        event();
    }

    // 초기화
    private void init() {
        tv_answer2 = (TextView) findViewById(R.id.tv_answer2);
        bt_0 = (Button) findViewById(R.id.bt_0);
        bt_1 = (Button) findViewById(R.id.bt_1);
        bt_2 = (Button) findViewById(R.id.bt_2);
        bt_3 = (Button) findViewById(R.id.bt_3);
        bt_4 = (Button) findViewById(R.id.bt_4);
        bt_5 = (Button) findViewById(R.id.bt_5);
        bt_6 = (Button) findViewById(R.id.bt_6);
        bt_7 = (Button) findViewById(R.id.bt_7);
        bt_8 = (Button) findViewById(R.id.bt_8);
        bt_9 = (Button) findViewById(R.id.bt_9);
        bt_next = (Button) findViewById(R.id.bt_next);
        bt_clear = (Button) findViewById(R.id.bt_clear);
        tv_question1 = (TextView) findViewById(R.id.tv_question1);
        tv_question2 = (TextView) findViewById(R.id.tv_question2);
        tv_question3 = (TextView) findViewById(R.id.tv_question3);
        answer = 0;
        score = 0;
        count = 0;
        tv_count = (TextView) findViewById(R.id.tv_count);
        tv_count.setText(score + " / 10");
        setTitle("CalculationPower - " + (count+1) + " 번째 문제");
        setQeustion();
    }

    // 실질적인 메인
    private void event() {
        // 버튼을 누르면 해당 버튼의 숫자가 입력
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = (String) tv_answer2.getText();
                str += ((Button) v).getText();
                tv_answer2.setText(Integer.parseInt(str) + "");
            }
        };
        bt_0.setOnClickListener(clickListener);
        bt_1.setOnClickListener(clickListener);
        bt_2.setOnClickListener(clickListener);
        bt_3.setOnClickListener(clickListener);
        bt_4.setOnClickListener(clickListener);
        bt_5.setOnClickListener(clickListener);
        bt_6.setOnClickListener(clickListener);
        bt_7.setOnClickListener(clickListener);
        bt_8.setOnClickListener(clickListener);
        bt_9.setOnClickListener(clickListener);

        bt_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer == Integer.parseInt((String) tv_answer2.getText())) {
                    score++;
                    Toast.makeText(CalculationPower.this, "정답! 맞춘 갯수 : " + score + "개", Toast.LENGTH_SHORT).show();
                    setQeustion();
                } else {
                    Toast.makeText(CalculationPower.this, "오답! 맞춘 갯수 : " + score + "개", Toast.LENGTH_SHORT).show();
                    setQeustion();
                }
                tv_count.setText(score + " / 10");
                count++;
                setTitle("CalculationPower - " + (count+1) + " 번째 문제");
                if (count == 10) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(CalculationPower.this);
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
        });
        bt_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_answer2.setText("0");
            }
        });
    }

    // 문제준비
    private void setQeustion() {
        tv_question1.setText("" + (rnd.nextInt(9) + 1));
        tv_question3.setText("" + (rnd.nextInt(9) + 1));
        String str1 = (String) tv_question1.getText();
        String str2 = (String) tv_question3.getText();
        int tmp = rnd.nextInt(2);
        if (tmp == 0) {
            tv_question2.setText("+");
            answer = Integer.parseInt(str1) + Integer.parseInt(str2);
        } else {
            tv_question2.setText("-");
            if (Integer.parseInt(str1) >= Integer.parseInt(str2)) {
                answer = Integer.parseInt(str1) - Integer.parseInt(str2);
            } else {
                tv_question1.setText(str2);
                tv_question3.setText(str1);
                answer = Integer.parseInt(str2) - Integer.parseInt(str1);
            }
        }
        tv_answer2.setText("" + 0);
    }
}
