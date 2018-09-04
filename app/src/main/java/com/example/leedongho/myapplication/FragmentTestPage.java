package com.example.leedongho.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class FragmentTestPage extends Fragment {
    CheckBox[] cb_q1, cb_q2;
    Button bt_test_score;
    int score = 0;

    public FragmentTestPage(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 플레그먼트 인플레이트
        View view = inflater.inflate(R.layout.activity_fragment_test, container, false);
        cb_q1 = new CheckBox[15];
        cb_q1[0] = (CheckBox) view.findViewById(R.id.cb_q1_01);
        cb_q1[1] = (CheckBox) view.findViewById(R.id.cb_q1_02);
        cb_q1[2] = (CheckBox) view.findViewById(R.id.cb_q1_03);
        cb_q1[3] = (CheckBox) view.findViewById(R.id.cb_q1_04);
        cb_q1[4] = (CheckBox) view.findViewById(R.id.cb_q1_05);
        cb_q1[5] = (CheckBox) view.findViewById(R.id.cb_q1_06);
        cb_q1[6] = (CheckBox) view.findViewById(R.id.cb_q1_07);
        cb_q1[7] = (CheckBox) view.findViewById(R.id.cb_q1_08);
        cb_q1[8] = (CheckBox) view.findViewById(R.id.cb_q1_09);
        cb_q1[9] = (CheckBox) view.findViewById(R.id.cb_q1_10);
        cb_q1[10] = (CheckBox) view.findViewById(R.id.cb_q1_11);
        cb_q1[11] = (CheckBox) view.findViewById(R.id.cb_q1_12);
        cb_q1[12] = (CheckBox) view.findViewById(R.id.cb_q1_13);
        cb_q1[13] = (CheckBox) view.findViewById(R.id.cb_q1_14);
        cb_q1[14] = (CheckBox) view.findViewById(R.id.cb_q1_15);
        cb_q2 = new CheckBox[6];
        cb_q2[0] = (CheckBox) view.findViewById(R.id.cb_q2_01);
        cb_q2[1] = (CheckBox) view.findViewById(R.id.cb_q2_02);
        cb_q2[2] = (CheckBox) view.findViewById(R.id.cb_q2_03);
        cb_q2[3] = (CheckBox) view.findViewById(R.id.cb_q2_04);
        cb_q2[4] = (CheckBox) view.findViewById(R.id.cb_q2_05);
        cb_q2[5] = (CheckBox) view.findViewById(R.id.cb_q2_06);

        bt_test_score = (Button) view.findViewById(R.id.bt_test_score);

        init();
        event();

        return view;
    }

    private void init() {

    }

    private void event() {
        bt_test_score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0;i<cb_q1.length;i++){
                    if(cb_q1[i].isChecked()){
                        score++;
                    }
                }

                for(int i=0;i<cb_q2.length;i++){
                    if(cb_q2[i].isChecked()){
                        score+=2;
                    }
                }
                bt_test_score.setText(""+score);
                score = 0;
            }
        });
    }
}
