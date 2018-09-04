package com.example.leedongho.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class FragmentNewsPage extends Fragment {
    Button bt_news;

    public FragmentNewsPage(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 플레그먼트 인플레이트
        View view = inflater.inflate(R.layout.activity_fragment_news, container, false);

        bt_news = (Button) view.findViewById(R.id.bt_news);
        init();
        event();

        return view;
    }

    private void init() {
    }

    private void event() {
        bt_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.dementianews.co.kr/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }
}
