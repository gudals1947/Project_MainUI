package com.example.leedongho.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

public class Intro extends Activity{ //인트로 화면의 대한 클래스
    Handler handler = new Handler();
    Runnable r = new Runnable() {
        @Override
        public void run() {
            Intent mainintent = new Intent(getApplicationContext(), Memberjoin.class);
            startActivity(mainintent);
            finish();
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(r,1000); //1초 뒤에 Runnable 객체수행
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(r); //예약 취소
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_main);
    }
}
