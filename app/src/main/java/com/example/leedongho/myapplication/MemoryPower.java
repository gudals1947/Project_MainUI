package com.example.leedongho.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

public class MemoryPower extends AppCompatActivity {
    GameView gv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gv = new GameView(this);
        setContentView(gv);
    }

    // 도형 하나에 대한 정보
    class Shape {
        final static int RECT = 0;
        final static int CIRCLE = 1;
        final static int TRIANGLE = 2;
        int what;
        int color;
        Rect rt;
    }

    // 게임 뷰 : 실질적인 메인이다.
    class GameView extends View {
        // 그리기 모드, 빈 화면 또는 도형 출력
        final static int BLANK = 0;
        final static int PLAY = 1;
        // 게임 진행 속도
        final static int DELAY = 500;
        // 현재 그리기 모드
        int status;
        // 생성된 도형의 목록
        ArrayList<Shape> arShape = new ArrayList<Shape>();
        Random Rnd = new Random();
        Activity mParent;

        public GameView(Context context) {
            super(context);
            mParent = (Activity)context;
            // 빈 화면으로 시작하고 잠시 후에 게임 시작
            status = BLANK;
            mHandler.sendEmptyMessageDelayed(0, DELAY);
        }

        public  void onDraw(Canvas canvas){
            // 검은색 배경으로 지운다. 빈 화면이면 지우기만 하고 리턴
            canvas.drawColor(Color.BLACK);
            if(status == BLANK){
                return;
            }

            // 도형 목록을 순회하면서 도형 정보대로 출력한다.
            int idx;
            for(idx = 0; idx < arShape.size(); idx ++){
                Paint Pnt = new Paint();
                Pnt.setAntiAlias(true);
                Pnt.setColor(arShape.get(idx).color);

                Rect rt = arShape.get(idx).rt;
                switch (arShape.get(idx).what) {
                    case Shape.RECT:
                        canvas.drawRect(rt, Pnt);
                        break;
                    case Shape.CIRCLE:
                        canvas.drawCircle(rt.left + rt.width()/2, rt.top + rt.height()/2,
                                rt.width()/2, Pnt);
                        break;
                    case Shape.TRIANGLE:
                        Path path = new Path();
                        path.moveTo(rt.left + rt.width()/2, rt.top);
                        path.lineTo(rt.left, rt.bottom);
                        path.lineTo(rt.right, rt.bottom);
                        canvas.drawPath(path, Pnt);
                        break;
                }
            }
        }
        public boolean onTouchEvent(MotionEvent event){
            if(event. getAction() == MotionEvent.ACTION_DOWN){
                int sel;
                sel = FindShapeIdx((int)event.getX(), (int)event.getY());

                // 빈 바닥을 찍었으면 무시한다.
                if(sel == -1){
                    return true;
                }

                // 마지막 추가된 도형을 제대로 찍었으면 다음 단계로 진행.
                // 빈화면 잠시 보여준 후 새 도형 추가
                if(sel == arShape.size()-1){
                    status = BLANK;
                    invalidate();
                    mHandler.sendEmptyMessageDelayed(0, DELAY);
                    // 엉뚱한 도형을 찍었으면 질문 후 게임 종료 또는 재시작
                } else {
                    new AlertDialog.Builder(getContext())
                            .setMessage("기록 : " + arShape.size() + " 단계" + "\n다시 하시겠습니까?")
                            .setTitle("게임 끝")
                            .setPositiveButton("한판 더", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    arShape.clear();
                                    status = BLANK;
                                    invalidate();
                                    mHandler.sendEmptyMessageDelayed(0, DELAY);
                                }
                            })
                            .setNegativeButton("나가기", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mParent.finish();
                                }
                            })
                            .show();
                }
                return true;
            }
            return false;
        }

        // 새로운 도형을 목록에 추가한다.
        void AddNewShape() {
            Shape shape = new Shape();
            int idx;
            boolean bFindIntersect;
            Rect rt = new Rect();

            // 기존 도형과 겹치지 않는 새 위치를 찾는다.
            for(;;){
                // 크기는 32, 48, 64 중 하나 선택
                int Size = 64 + 32 * Rnd.nextInt(3);

                // 위치는 난수로 선택
                rt.left = Rnd.nextInt(getWidth());
                rt.top = Rnd.nextInt(getHeight());
                rt.right = rt.left + Size;
                rt.bottom = rt.top + Size;

                // 화면을 벗어나면 안 된다.
                if(rt.right > getWidth() || rt.bottom > getHeight()){
                    continue;
                }

                // 기존 도형 순회하며 겹치는지 조사한다.
                bFindIntersect = false;
                for(idx = 0; idx < arShape.size(); idx ++){
                    if(rt.intersect(arShape.get(idx).rt) == true){
                        bFindIntersect = true;
                    }
                }

                // 겹치지 않을 때 확정한다. 겹치면 계속 새 위치 선정한다.
                if(bFindIntersect == false){
                    break;
                }
            }

            // 새 도형 정보 작성. 모양, 색상 등을 난수 선택한다.
            shape.what = Rnd.nextInt(3);

            switch (Rnd.nextInt(5)) {
                case 0:
                    shape.color = Color.WHITE;
                    break;
                case 1:
                    shape.color = Color.RED;
                    break;
                case 2:
                    shape.color = Color.GREEN;
                    break;
                case 3:
                    shape.color = Color.BLUE;
                    break;
                case 4:
                    shape.color = Color.YELLOW;
                    break;
            }

            shape.rt = rt;
            arShape.add(shape);
        }

        // x, y 위치의 도형 번호를 찾는다. 도형이 없는 위치면 -1 리턴
        int FindShapeIdx(int x, int y){
            int idx;

            for(idx = 0; idx < arShape.size(); idx ++){
                if(arShape.get(idx).rt.contains(x, y)){
                    return idx;
                }
            }
            return -1;
        }

        // 새 도형을 추가하고 화면을 다시 그린다. 딜레이를 주기 위해 핸들러를 사용한다.
        Handler mHandler = new Handler() {
            public void handleMessage(Message msg){
                AddNewShape();
                status = PLAY;
                invalidate();

                String title;
                title = "MemoryPower - " + arShape.size() + " 단계";
                mParent.setTitle(title);
            }
        };
    }
}
