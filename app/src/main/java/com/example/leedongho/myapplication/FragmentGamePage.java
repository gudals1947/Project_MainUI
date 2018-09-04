package com.example.leedongho.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class FragmentGamePage extends Fragment {
    Button bt_calculation_test;
    Button bt_memorizing_test;
    Button bt_attention_test;

    public FragmentGamePage(){
        super();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 플레그먼트 인플레이트
        View view = inflater.inflate(R.layout.activity_fragment_game, container, false);
        bt_calculation_test = (Button) view.findViewById(R.id.bt_calculation_test);
        bt_memorizing_test = (Button) view.findViewById(R.id.bt_memorizing_test);
        bt_attention_test = (Button) view.findViewById(R.id.bt_attention_test);
        init();
        event();

        return view;
    }

    private void init() {

    }

    private void event() {
        bt_calculation_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CalculationPower.class);
                startActivity(intent);
            }
        });
        bt_memorizing_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MemoryPower.class);
                startActivity(intent);
            }
        });
        bt_attention_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AttentionPower.class);
                startActivity(intent);
            }
        });
    }
}
