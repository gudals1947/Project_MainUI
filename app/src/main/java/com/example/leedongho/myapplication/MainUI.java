package com.example.leedongho.myapplication;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.astuetz.PagerSlidingTabStrip;
import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainUI extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String first_name;
    String last_name;
    String sex;
    int age;
    int nYear;
    private FirebaseAuth auth;

    @BindView(R.id.descriptImage)
    ImageSwitcher descriptImage;
    @BindView(R.id.tabs)
    PagerSlidingTabStrip tabStrip;
    @BindView(R.id.view_pager)
    ViewPager pager;
    // images for switcher
    private static final int[] IMAGES = {R.drawable.viewpager1, R.drawable.viewpager2, R.drawable.viewpager3, R.drawable.viewpager4};
    TextView tv_name;
    TextView tv_sex;
    TextView tv_age;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        auth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 그전 인텐트 값 받아오기
//        BeformIntent();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);

        tv_name = (TextView) header.findViewById(R.id.tv_name);
        tv_sex = (TextView) header.findViewById(R.id.tv_sex);
        tv_age = (TextView) header.findViewById(R.id.tv_age);
        FirebaseFirestore.getInstance().collection("user").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot documentSnapshots) {
                for (DocumentChange documentChange : documentSnapshots.getDocumentChanges()) {
                    String isAttendance = documentChange.getDocument().getData().get("mail").toString();
                    if (isAttendance.equals(auth.getCurrentUser().getEmail())) {
                        tv_name.setText(documentChange.getDocument().getData().get("name").toString());
                        tv_sex.setText(auth.getCurrentUser().getEmail());
                        tv_age.setText(documentChange.getDocument().getData().get("year").toString());
                        break;
                    }
                }
            }
        });
        init();
        event();
    }

    private void BeformIntent() {
        Intent before_intent = getIntent();
        first_name = before_intent.getExtras().getString("first_edit_text");
        last_name = before_intent.getExtras().getString("last_edit_text");
        sex = before_intent.getExtras().getString("sex");
        age = before_intent.getExtras().getInt("datePicker_edit_text");

        String temp = first_name + " " + last_name;

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);

        TextView tv_name = (TextView) header.findViewById(R.id.tv_name);
        TextView tv_sex = (TextView) header.findViewById(R.id.tv_sex);
        TextView tv_age = (TextView) header.findViewById(R.id.tv_age);

        Calendar calendar = new GregorianCalendar(Locale.KOREA);
        nYear = calendar.get(Calendar.YEAR);


        tv_name.setText(auth.getCurrentUser().getDisplayName());
        tv_sex.setText(auth.getCurrentUser().getEmail());
//        tv_age.setText("나이 : "+String.valueOf(nYear-age+1)+" 세");
    }

    private void init() {
        ButterKnife.bind(this);
        pager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()));
        tabStrip.setViewPager(pager);
        tabStrip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                // if user go to another tab change color, change image and query from database to match
                descriptImage.setImageResource(IMAGES[position]);
                changeColor(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        descriptImage.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                // set props for image switcher
                ImageView imgview = new ImageView(getApplicationContext());
                imgview.setScaleType(ImageView.ScaleType.CENTER_CROP);
                return imgview;
            }
        });
        // Photo flies in and out
        Animation in = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        Animation out = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);
        descriptImage.setInAnimation(in);
        descriptImage.setOutAnimation(out);
        descriptImage.setImageResource(IMAGES[0]); // first start render

    }

    private void changeColor(int position) {
        switch (position) {
            case 0:
                applyNewColor("#ff4500", "#ffa500", "#ff8C00");
                break;
            case 1:
                applyNewColor("#a00037", "#ff5c8d", "#d81b60");
                break;
            case 2:
                applyNewColor("#4b2c20", "#a98274", "#795548");
                break;
            case 3:
                applyNewColor("#303f9f", "#757de8", "#3f51b5");
                break;
            default:
                break;
        }
    }

    // apply new fancy color based on material color tool
    private void applyNewColor(String actionBarColor, String tabStripColor, String indicatorColor) {
        ActionBar actionBar = getSupportActionBar();
        Window window = this.getWindow();

        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(actionBarColor)));
        window.setStatusBarColor(Color.parseColor(indicatorColor));
        tabStrip.setBackground(new ColorDrawable((Color.parseColor(tabStripColor))));
        tabStrip.setIndicatorColor(Color.parseColor(indicatorColor));
    }

    private void event() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // 네비게이션 드로어 메뉴 선택 이벤트
        int id = item.getItemId();
        Fragment fragment = null;
        if (id == R.id.nav_news) {
            String url = "https://www.dementianews.co.kr/";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        } else if (id == R.id.nav_game) {

        } else if (id == R.id.nav_information) {

        } else if (id == R.id.nav_test) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_score) {

        } else if (id == R.id.nav_exit) {

            auth.signOut();
            LoginManager.getInstance().logOut();
            finish();
            Intent intent = new Intent(this, Memberjoin.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
