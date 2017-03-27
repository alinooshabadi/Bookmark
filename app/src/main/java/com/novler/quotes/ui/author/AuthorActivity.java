package com.novler.quotes.ui.author;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.novler.quotes.BaseApp;
import com.novler.quotes.ExportInstagramActivity;
import com.novler.quotes.R;
import com.novler.quotes.models.AuthorData;
import com.novler.quotes.models.QuoteListData;
import com.novler.quotes.models.ResponseData;
import com.novler.quotes.networking.Service;
import com.novler.quotes.presenter.HomePresenter;
import com.novler.quotes.ui.home.BaseView;
import com.novler.quotes.ui.novel.NovelActivity;
import com.novler.quotes.ui.quote.QuotesAdapter;
import com.novler.quotes.util.FontUtil;
import com.novler.quotes.util.ShareUtil;
import com.novler.quotes.util.Utils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.ColorFilterTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static com.novler.quotes.R.id.link;
import static com.novler.quotes.R.id.share;

public class AuthorActivity extends BaseApp implements BaseView {
  @Inject
  public Service service;

  @BindView(R.id.collapsingToolbarLayout)
  CollapsingToolbarLayout mCollapsingToolbarLayout;
  @BindView(R.id.appBarLayout)
  AppBarLayout mAppBarLayout;

  @BindView(R.id.image)
  ImageView ivCover;
  @BindView(R.id.backgroundPoster)
  ImageView ivBackgroundPoster;
  @BindView(R.id.author_title)
  TextView tvTitle;
  @BindView(R.id.author_originalTitle)
  TextView tvOriginalTitle;
  @BindView(R.id.titleBar)
  TextView tvTitleBar;
  @BindView(R.id.authur_BirthDate)
  TextView tvBirthDate;
  @BindView(R.id.authur_DeathDate)
  TextView tvDeathDate;
  @BindView(R.id.author_bio)
  ExpandableTextView tvBio;
  @BindView(R.id.list)
  RecyclerView mList;
  @BindView(R.id.toolbar)
  Toolbar toolbar;
  @BindView(link)
  ImageView ivLinkToWeb;
  @BindView(R.id.expand_collapse)
  ImageView ivExpand;
  @BindView(R.id.expandable_text)
  TextView tvExpand;

  int mNovelId;
  String mUrl = "";
  String mCoverUrl = "";
  String mTitle = "";
  String mAuthor = "";
  String mNovlerId = "";

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getDeps().inject(this);

    getIntents();
    renderView();
    init();

    mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
      boolean isShow = false;
      int scrollRange = -1;

      @Override
      public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (scrollRange == -1) {
          scrollRange = appBarLayout.getTotalScrollRange();
        }
        if (scrollRange + verticalOffset < 0) {
          tvTitleBar.setText(mTitle);
          ivCover.setVisibility(View.INVISIBLE);
          toolbar.setBackgroundColor(getResources().getColor(R.color.primary));
          isShow = true;
        } else if (isShow) {
          tvTitleBar.setText(" ");
          ivCover.setVisibility(View.VISIBLE);
          tvBio.setVisibility(View.VISIBLE);
          tvExpand.setVisibility(View.VISIBLE);
          ivExpand.setVisibility(View.VISIBLE);
          toolbar.setBackgroundColor(Color.argb(0, 0, 0, 0));
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
    setContentView(R.layout.activity_author);
    ButterKnife.bind(this);
    LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
    mList.setLayoutManager(mLayoutManager);
  }

  public void init() {
    HomePresenter presenter = new HomePresenter(service, this);
    presenter.getAuthor(mNovlerId);

    tvTitle.setTypeface(FontUtil.getTypeface(getApplicationContext(), FontUtil.FontType.IranSansBold));
    tvTitleBar.setTypeface(FontUtil.getTypeface(getApplicationContext(), FontUtil.FontType.IranSansBold));

    if (mAuthor != null)
      tvBio.setText(mAuthor);
    if (mTitle != null)
      tvTitle.setText(mTitle);

    if (mCoverUrl != null)
      Glide.with(getApplicationContext()).load(mCoverUrl)
        .bitmapTransform(new CropCircleTransformation(getApplicationContext()))
        .placeholder(R.drawable.author_placeholder)
        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
        .skipMemoryCache(true)
        .into(ivCover);
    SetToolbar();
  }

  void SetToolbar() {
    setSupportActionBar(toolbar);
    if (getSupportActionBar() != null)
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setDisplayShowHomeEnabled(true);
    if (Build.VERSION.SDK_INT > 17)
      toolbar.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
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
    if (mUrl.hashCode() != "".hashCode()) {
      Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mUrl));
      startActivity(browserIntent);
    }
  }

  @OnClick(share)
  void shareLink() {
    ShareUtil.intentMessage(this, mUrl);
  }

  @Override public void getListSuccess(ResponseData listResponse) {
    QuotesAdapter adapter = new QuotesAdapter(this, this.getApplicationContext(), listResponse.getAuthor().getQuotes(),
      new QuotesAdapter.OnItemClickListener() {
        @Override
        public void onClick(QuoteListData Item, View view) {
          if (view.getId() == R.id.shareTelegram) {
            ShareUtil.ShareTextQuote(AuthorActivity.this, Item.getText(), Item.getNovel(), Item.getAuthor());

          } else if (view.getId() == R.id.shareInsta) {
            Intent intent = new Intent(AuthorActivity.this, ExportInstagramActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("title", Item.getNovel());
            bundle.putString("author", Item.getAuthor());
            bundle.putString("text", Utils.clearText(Item.getText()));
            intent.putExtras(bundle);
            startActivity(intent);
          } else {
            Intent intent = new Intent(AuthorActivity.this, NovelActivity.class);
            Pair<View, String> pair1 = Pair.create(view, "novel_title");
            Pair<View, String> pair2 = Pair.create(view, "novel_author");
            Pair<View, String> pair3 = Pair.create(view, "novel_cover");
            @SuppressWarnings("unchecked") ActivityOptionsCompat options = ActivityOptionsCompat.
              makeSceneTransitionAnimation(AuthorActivity.this, pair1, pair2, pair3);

            Bundle bundle = new Bundle();
            bundle.putString("cover", Item.getNovelImage());
            bundle.putString("title", Item.getNovel());
            bundle.putString("author", Item.getAuthor());
            bundle.putString("novlerId", Item.getNovelNovlerId());
            bundle.putInt("novelId", Item.getNovelId());
            intent.putExtras(bundle);
            startActivity(intent, options.toBundle());
          }

        }
      });

    AuthorData author = listResponse.getAuthor();
    mList.setAdapter(adapter);
    tvOriginalTitle.setText(author.getOriginalTitle());
    tvTitle.setText(author.getTitle());
    tvBio.setText(author.getBio());
    mUrl = author.getUrl();
    if (author.getBirthDate().hashCode() != "1".hashCode())
      tvBirthDate.setText(author.getBirthDate());
    if (author.getDeathDate().hashCode() != "1".hashCode())
      tvDeathDate.setText("-" + author.getDeathDate());
    mCoverUrl = author.getImageUrl();
    Glide.with(getApplicationContext()).load(mCoverUrl)
      .bitmapTransform(new CropCircleTransformation(getApplicationContext()))
      .placeholder(R.drawable.author_placeholder)
      .diskCacheStrategy(DiskCacheStrategy.SOURCE)
      .skipMemoryCache(true)
      .into(ivCover);

    Glide.with(getApplicationContext()).load(mCoverUrl)
      .placeholder(R.drawable.background_placeholder)
      .bitmapTransform(new BlurTransformation(getApplicationContext()), new ColorFilterTransformation(getApplicationContext(), Color.argb(150, 0, 0, 0)))
      .diskCacheStrategy(DiskCacheStrategy.SOURCE)
      .skipMemoryCache(true)
      .into(ivBackgroundPoster);

  }

  public void getIntents() {
    Bundle b = getIntent().getExtras();

    if (b != null) {
      mCoverUrl = b.getString("cover");
      mTitle = b.getString("title");
      mAuthor = b.getString("author");
      mNovelId = b.getInt("novelId");
      mNovlerId = b.getString("novlerId");
    }

    Intent intent = getIntent();
    String action = intent.getAction();
    String data = intent.getDataString();
    if (Intent.ACTION_VIEW.equals(action) && data != null) {
      String url = data.replace("https://novler.com/_novel/", "");
      mNovlerId = url.split("/")[0];
    }
  }
}
