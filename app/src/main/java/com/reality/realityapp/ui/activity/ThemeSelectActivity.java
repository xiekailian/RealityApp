package com.reality.realityapp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.reality.realityapp.R;
import com.reality.realityapp.bean.Token;
import com.reality.realityapp.business.UserBusiness;
import com.reality.realityapp.net.CommonCallback;
import com.reality.realityapp.ui.activity.base.BaseActivity;
import com.reality.realityapp.utils.T;

import java.util.ArrayList;
import java.util.List;

public class ThemeSelectActivity extends BaseActivity implements OnClickListener {

    /**
     * 分类有：热点，国内，国际，军事，财经，互联网，
     * 首页，汽车，体育，娱乐，教育，时尚，科技，社会，
     * 亲子，旅游，科学，星座，奢侈品，游戏，美食，电影，健康，理财
     *
     * 选了9个：国内，军事，财经，体育，娱乐，游戏，科学，汽车，旅游
     */
    private static final String TAG = "ThemeSelectActivity";
    private Button skipButton;
    private Button submitButton;
    private ImageView politicsImg;
    private ImageView sportsImg;
    private ImageView scienceImg;
    private ImageView financeImg;
    private ImageView militaryImg;
    private ImageView carsImg;
    private ImageView entertainmentImg;
    private ImageView houseImg;
    private ImageView gameImg;

    private ArrayList<Integer> chosenTags = new ArrayList<Integer>();
    private ArrayList<String> chosenTagsStrs = new ArrayList<String>();

    /** 当前的ImageView */
    private ImageView currentImage;
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String TOKEN = "TOKEN";
    private String username = "";
    private String password = "";
    private String token = "";
    private UserBusiness userBusiness = new UserBusiness();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_select);
        initView();
        initEvent();
        initIntent(getIntent());
    }

    private void initView() {
        skipButton = (Button) findViewById(R.id.id_btn_skip);
        submitButton = (Button) findViewById(R.id.id_btn_submit);
        politicsImg = (ImageView) findViewById(R.id.id_img_politics);
        politicsImg.setTag("国内");
        sportsImg = (ImageView) findViewById(R.id.id_img_sports);
        sportsImg.setTag("体育");
        scienceImg = (ImageView) findViewById(R.id.id_img_science);
        scienceImg.setTag("科学");
        financeImg = (ImageView) findViewById(R.id.id_img_finance);
        financeImg.setTag("财经");
        militaryImg = (ImageView) findViewById(R.id.id_img_military);
        militaryImg.setTag("军事");
        carsImg = (ImageView) findViewById(R.id.id_img_cars);
        carsImg.setTag("汽车");
        entertainmentImg = (ImageView) findViewById(R.id.id_img_entertainment);
        entertainmentImg.setTag("娱乐");
        houseImg = (ImageView) findViewById(R.id.id_img_house);
        houseImg.setTag("旅游");
        gameImg = (ImageView) findViewById(R.id.id_img_game);
        gameImg.setTag("游戏");
    }

    private  void initEvent() {

        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toFirstPageActivity();
            }
        });

        politicsImg.setOnClickListener(this);
        sportsImg.setOnClickListener(this);
        scienceImg.setOnClickListener(this);
        financeImg.setOnClickListener(this);
        militaryImg.setOnClickListener(this);
        carsImg.setOnClickListener(this);
        entertainmentImg.setOnClickListener(this);
        houseImg.setOnClickListener(this);
        gameImg.setOnClickListener(this);
//        //默认给第一个ImageView加边框
//        politicsImg.setImageResource(R.drawable.border);
//        currentImage = politicsImg;

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> selectedThemes = new ArrayList<String>();
                selectedThemes.addAll(chosenTagsStrs);
                userBusiness.themeSelect(token, password, selectedThemes, new CommonCallback<Token>() {
                    @Override
                    public void onError(Exception e) {
                        T.showToast(e.getMessage());
                    }

                    @Override
                    public void onResponse(Token response) {
                        stopLoadingProgress();
                        T.showToast("选择成功:"+response.getToken());
                        Log.v(TAG, "选择成功！！");
//                        //保存用户的信息
//                        User user = new User(response.get_id(),username,password,response.getToken());
//                        UserInfoHolder.getInstance().setUser(user);

                        toFirstPageActivity();
//                        toThemeSelectActivity();
                    }
                });
                toFirstPageActivity();
            }
        });
    }

    /**
     * 跳转到首页
     */
    private void toFirstPageActivity() {
        Intent intent = new Intent(this, FirstPageActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    public void onClick(View v) {
//        int tag = (Integer)v.getTag();
//        for (int i=0; i<chosenTags.size();i++) {
//            if (tag==chosenTags.get(i)) {
//                chosenTags.remove(i);
//                cancelSelect((ImageView) v);
//                return;
//            }
//        }
//        chosenTags.add(tag);
//        Log.v(TAG, "tag: "+tag);
//        select((ImageView) v);

        String tagStr = v.getTag().toString();
        for (int i=0; i<chosenTagsStrs.size();i++) {
            if (tagStr.equals(chosenTagsStrs.get(i))) {
                chosenTagsStrs.remove(i);
                cancelSelect((ImageView) v);
                return;
            }
        }
        chosenTagsStrs.add(tagStr);
        Log.v(TAG, "tagStr: "+tagStr);
        select((ImageView) v);
    }

    public static void launch(Context context, String username, String password, String token) {
        Intent intent = new Intent(context, ThemeSelectActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra(USERNAME, username);
        intent.putExtra(PASSWORD, password);
        intent.putExtra(TOKEN, token);

//        Log.d("login activity", "string1: "+username);
        context.startActivity(intent);
    }
    /**
     * 给点击选中的ImageView加边框，若已选中则取消边框
     * @param imageView 要添加/取消边框的ImageView
     */
    public void select(ImageView imageView){
        imageView.setImageResource(R.drawable.border);
    }
    public void cancelSelect(ImageView imageView){
        imageView.setImageDrawable(null);
    }


    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        initIntent(intent);
    }

    private void initIntent(Intent intent) {
//        Log.d("login activity", "initIntent: "+intent);
        if (intent == null) {
            return;
        }

        username = intent.getStringExtra(USERNAME);
        password = intent.getStringExtra(PASSWORD);
        token = intent.getStringExtra(TOKEN);
        Log.d("theme select activity", "username: "+username);
        Log.d("theme select activity", "password: "+password);
        Log.d("theme select activity", "token: "+ token);
    }
}

