package com.novler.quotes;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.novler.quotes.ui.home.HomeActivity;
import com.novler.quotes.util.FontUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.splash_bookmark)
    TextView bookmark;
    @BindView(R.id.splash_novler_link)
    TextView novlerLink;
    @BindView(R.id.splash_novler_text)
    TextView novlerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        bookmark.setTypeface(FontUtil.getTypeface(getApplicationContext(), FontUtil.FontType.AGhasem));
        novlerLink.setTypeface(FontUtil.getTypeface(getApplicationContext(), FontUtil.FontType.IranSansBold));
        novlerText.setTypeface(FontUtil.getTypeface(getApplicationContext(), FontUtil.FontType.IranSansLight));



        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        /*
      Duration of wait
     */
        int SPLASH_DISPLAY_LENGTH = 1500;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(SplashActivity.this, HomeActivity.class);
                SplashActivity.this.startActivity(mainIntent);
                SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }


}
