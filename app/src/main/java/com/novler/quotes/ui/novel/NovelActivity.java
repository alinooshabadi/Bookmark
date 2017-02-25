package com.novler.quotes.ui.novel;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.novler.quotes.BaseApp;
import com.novler.quotes.R;
import com.novler.quotes.models.QuoteListData;
import com.novler.quotes.models.ResponseData;
import com.novler.quotes.networking.Service;
import com.novler.quotes.ui.home.BaseView;
import com.novler.quotes.ui.home.HomeAdapter;
import com.novler.quotes.ui.home.HomePresenter;
import com.novler.quotes.util.RoundedImageView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NovelActivity extends BaseApp implements BaseView {
  @Inject
  public Service service;

  @BindView(R.id.image)
  RoundedImageView mCover;
  @BindView(R.id.list)
  RecyclerView mList;
  @BindView(R.id.toolbar)
  Toolbar mToolbar;
  @BindView(R.id.toolbarTitle)
  TextView mToolbarTitle;

  int mNovelId;
  String mCoverUrl = "";
  String mTitle = "";

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getDeps().inject(this);

    renderView();
    init();

    HomePresenter presenter = new HomePresenter(service, this);
    presenter.getNovel(mNovelId);

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
    LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
    mList.setLayoutManager(mLayoutManager);
  }

  public void init() {
    Bundle b = getIntent().getExtras();

    if (b != null) {
      mCoverUrl = b.getString("cover");
      mTitle = b.getString("title");
      mNovelId = b.getInt("novelId");
    }


    Glide.with(getApplicationContext()).load(mCoverUrl)
      .placeholder(R.drawable.novel_placeholder)
      .diskCacheStrategy(DiskCacheStrategy.SOURCE)
      .skipMemoryCache(true)
      .into(mCover);

    SetToolbar();
  }

  void SetToolbar() {
    setSupportActionBar(mToolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setDisplayShowTitleEnabled(false);
    getSupportActionBar().setDisplayShowHomeEnabled(true);


    mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
  }

  @Override public void showWait() {

  }

  @Override public void removeWait() {

  }

  @Override
  public void onFailure(String appErrorMessage) {

  }

  @Override public void getListSuccess(ResponseData listResponse) {
    HomeAdapter adapter = new HomeAdapter(this, this.getApplicationContext(), listResponse.getNovel().getQuotes(),
      new HomeAdapter.OnItemClickListener() {
        @Override
        public void onClick(QuoteListData Item, View view) {

        }
      });
    mList.setAdapter(adapter);
  }
}
