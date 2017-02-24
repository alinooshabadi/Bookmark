package com.novler.quotes.ui.novel;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.novler.quotes.BaseApp;
import com.novler.quotes.R;
import com.novler.quotes.util.FontUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NovelActivity extends BaseApp {

    @BindView(R.id.image)
    ImageView mCover;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbarTitle)
    TextView toolbarTitle;

    String mCoverUrl = "";
    String mTitle = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        renderView();
        init();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public void renderView() {
        setContentView(R.layout.activity_novel);
        ButterKnife.bind(this);
    }

    public void init() {
        Bundle b = getIntent().getExtras();

        if (b != null) {
            mCoverUrl = b.getString("cover");
            mTitle = b.getString("mTitle");
        }


        Glide.with(getApplicationContext()).load(mCoverUrl)
                .placeholder(R.drawable.novel_placeholder)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .skipMemoryCache(true)
                .into(mCover);

        SetToolbar();
    }

    void SetToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbarTitle.setTypeface(FontUtil.getTypeface(getApplicationContext(), FontUtil.FontType.IranSansLight));
        toolbarTitle.setText(mTitle);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}
