package com.novler.quotes.ui.novel;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.novler.quotes.BaseApp;
import com.novler.quotes.R;
import com.novler.quotes.models.QuoteListData;
import com.novler.quotes.models.ResponseData;
import com.novler.quotes.networking.Service;
import com.novler.quotes.presenter.HomePresenter;
import com.novler.quotes.ui.home.BaseView;
import com.novler.quotes.ui.home.HomeAdapter;
import com.novler.quotes.util.FontUtil;
import com.novler.quotes.util.RoundedImageView;
import com.novler.quotes.util.Util;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.novler.quotes.R.id.link;

public class NovelActivity extends BaseApp implements BaseView {
  @Inject
  public Service service;
  String mNovelUrl = "";

  @BindView(R.id.collapsingToolbarLayout)
  CollapsingToolbarLayout mCollapsingToolbarLayout;

  @BindView(R.id.appBarLayout)
  AppBarLayout mAppBarLayout;

  @BindView(R.id.image)
  RoundedImageView mCover;

  @BindView(R.id.title)
  TextView mTitleView;
  @BindView(R.id.titleBar)
  TextView mTitleBarView;
  @BindView(R.id.author)
  TextView mAuthorView;
  @BindView(R.id.translator)
  TextView mTranslator;
  @BindView(R.id.originalTitle)
  TextView mOriginalTitle;
  @BindView(R.id.publisher)
  TextView mPublisher;

  @BindView(R.id.list)
  RecyclerView mList;
  @BindView(R.id.toolbar)
  Toolbar mToolbar;

  @BindView(R.id.rating)
  TextView mRating;

  @BindView(link)
  ImageView mlinkToWeb;

  int mNovelId;
  String mCoverUrl = "";
  String mTitle = "";
  String mAuthor = "";


  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getDeps().inject(this);

    renderView();
    init();

    HomePresenter presenter = new HomePresenter(service, this);
    presenter.getNovel(mNovelId);

    mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
      boolean isShow = false;
      int scrollRange = -1;

      @Override
      public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (scrollRange == -1) {
          scrollRange = appBarLayout.getTotalScrollRange();
        }
        if (scrollRange + verticalOffset == 0) {
          mTitleBarView.setText(mTitle);
          mTranslator.setVisibility(View.INVISIBLE);
          mPublisher.setVisibility(View.INVISIBLE);
          mCover.setVisibility(View.INVISIBLE);
          mlinkToWeb.setVisibility(View.INVISIBLE);
          isShow = true;
        } else if (isShow) {
          mTitleBarView.setText(" ");
          mTranslator.setVisibility(View.VISIBLE);
          mPublisher.setVisibility(View.VISIBLE);
          mCover.setVisibility(View.VISIBLE);
          mlinkToWeb.setVisibility(View.VISIBLE);
          isShow = false;
        }
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
      mAuthor = b.getString("author");
      mNovelId = b.getInt("novelId");
    }
    mTitleView.setTypeface(FontUtil.getTypeface(getApplicationContext(), FontUtil.FontType.IranSansBold));
    mAuthorView.setTypeface(FontUtil.getTypeface(getApplicationContext(), FontUtil.FontType.IranSansBold));
    mTitleBarView.setTypeface(FontUtil.getTypeface(getApplicationContext(), FontUtil.FontType.IranSansBold));

    mAuthorView.setText(mAuthor);
    mTitleView.setText(mTitle);


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

  @OnClick(link)
  void openWeb() {
    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mNovelUrl));
    startActivity(browserIntent);
  }

  @Override public void getListSuccess(ResponseData listResponse) {
    HomeAdapter adapter = new HomeAdapter(this, this.getApplicationContext(), listResponse.getNovel().getQuotes(),
      new HomeAdapter.OnItemClickListener() {
        @Override
        public void onClick(QuoteListData Item, View view) {

        }
      });

    mList.setAdapter(adapter);
    if (listResponse.getNovel().getTranslator() != null)
      mTranslator.setText("مترجم: " + listResponse.getNovel().getTranslator());
    else
      mTranslator.setVisibility(View.GONE);
    mOriginalTitle.setText(listResponse.getNovel().getOriginalTitle());
    //mRatingbar.setRating((Float.valueOf(listResponse.getNovel().getRate().toString())));
    mRating.setText(Util.toPersianNumber(String.format("%.2f", listResponse.getNovel().getRate())));
    mRating.setTypeface(FontUtil.getTypeface(getApplicationContext(), FontUtil.FontType.IranSansBold));
    mPublisher.setText("ناشر: " + listResponse.getNovel().getPublisher());
    mNovelUrl = listResponse.getNovel().getUrl();
  }
}
