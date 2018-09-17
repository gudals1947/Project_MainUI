package com.example.leedongho.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentInfoPage extends Fragment {

    public FragmentInfoPage(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 플레그먼트 인플레이트
        View view = inflater.inflate(R.layout.activity_fragment_info, container, false);
        init();
        event();

        return view;
    }

    private void init() {
    }

    private void event() {

    }
}
