package com.novler.quotes.ui.novel;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.novler.quotes.BaseApp;
import com.novler.quotes.ExportInstagramActivity;
import com.novler.quotes.R;
import com.novler.quotes.models.QuoteListData;
import com.novler.quotes.models.ResponseData;
import com.novler.quotes.networking.Service;
import com.novler.quotes.presenter.HomePresenter;
import com.novler.quotes.ui.author.AuthorActivity;
import com.novler.quotes.ui.home.BaseView;
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
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import static com.novler.quotes.R.id.link;
import static com.novler.quotes.R.id.share;

public class NovelActivity extends BaseApp implements BaseView {
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
  @BindView(R.id.title)
  TextView tvTitle;
  @BindView(R.id.titleBar)
  TextView tvTitleBar;
  @BindView(R.id.novel_author)
  TextView tvAuthor;
  @BindView(R.id.translator)
  TextView tvTranslator;
  @BindView(R.id.novel_originalTitle)
  TextView tvOriginalTitle;
  @BindView(R.id.publisher)
  TextView tvPublisher;
  @BindView(R.id.rating)
  TextView tvRating;
  @BindView(R.id.list)
  RecyclerView mList;
  @BindView(R.id.toolbar)
  Toolbar toolbar;
  @BindView(link)
  ImageView ivLinkToWeb;

  int mNovelId;
  String mNovelUrl = "";
  String mCoverUrl = "";
  String mTitle = "";
  String mAuthor = "";
  String mAuthorNovlerId = "";
  String mNovlerId = "";

  private GoogleApiClient client;

  @OnClick(R.id.novel_author)
  void clickAuthor(View view) {
    Intent intent = new Intent(NovelActivity.this, AuthorActivity.class);

    Bundle bundle = new Bundle();
    bundle.putString("author", mAuthor);
    bundle.putString("novlerId", mAuthorNovlerId);
    intent.putExtras(bundle);
    startActivity(intent);
  }

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
        if (scrollRange + verticalOffset == 0) {
          tvTitleBar.setText(mTitle);
          tvTranslator.setVisibility(View.INVISIBLE);
          tvPublisher.setVisibility(View.INVISIBLE);
          ivCover.setVisibility(View.INVISIBLE);
          toolbar.setBackgroundColor(getResources().getColor(R.color.primary));
          isShow = true;
        } else if (isShow) {
          tvTitleBar.setText(" ");
          tvTranslator.setVisibility(View.VISIBLE);
          tvPublisher.setVisibility(View.VISIBLE);
          ivCover.setVisibility(View.VISIBLE);
          toolbar.setBackgroundColor(Color.argb(0, 0, 0, 0));
          isShow = false;
        }
      }
    });
    // ATTENTION: This was auto-generated to implement the App Indexing API.
    // See https://g.co/AppIndexing/AndroidStudio for more information.
    client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
  }

  public void renderView() {
    setContentView(R.layout.activity_novel);
    ButterKnife.bind(this);
    LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
    mList.setLayoutManager(mLayoutManager);
  }

  public void init() {
    HomePresenter presenter = new HomePresenter(service, this);
    presenter.getNovel(mNovlerId);

    tvTitle.setTypeface(FontUtil.getTypeface(getApplicationContext(), FontUtil.FontType.IranSansBold));
    tvAuthor.setTypeface(FontUtil.getTypeface(getApplicationContext(), FontUtil.FontType.IranSansBold));
    tvTitleBar.setTypeface(FontUtil.getTypeface(getApplicationContext(), FontUtil.FontType.IranSansBold));

    if (mAuthor != null)
      tvAuthor.setText(mAuthor);
    if (mTitle != null)
      tvTitle.setText(mTitle);

    if (mCoverUrl != null)
      Glide.with(getApplicationContext()).load(mCoverUrl)
        .bitmapTransform(new RoundedCornersTransformation(getApplicationContext(), 12, 5))
        .placeholder(R.drawable.novel_placeholder)
        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
        .skipMemoryCache(true)
        .into(ivCover);
    SetToolbar();
  }

  void SetToolbar() {
    setSupportActionBar(toolbar);
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
    if (mNovelUrl.hashCode() != "".hashCode()) {
      Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mNovelUrl));
      startActivity(browserIntent);
    }
  }

  @OnClick(share)
  void shareLink() {
    ShareUtil.intentMessage(this, mNovelUrl);
  }

  @Override public void getListSuccess(ResponseData listResponse) {
    QuotesAdapter adapter = new QuotesAdapter(this, this.getApplicationContext(), listResponse.getNovel().getQuotes(),
      new QuotesAdapter.OnItemClickListener() {
        @Override
        public void onClick(QuoteListData Item, View view) {
          if (view.getId() == R.id.shareTelegram)
            ShareUtil.ShareTextQuote(NovelActivity.this, Item.getText(), Item.getNovel(), Item.getAuthor());

          else if (view.getId() == R.id.shareInsta) {
            Intent intent = new Intent(NovelActivity.this, ExportInstagramActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("title", Item.getNovel());
            bundle.putString("author", Item.getAuthor());
            bundle.putString("text", Utils.clearText(Item.getText()));
            intent.putExtras(bundle);
            startActivity(intent);
          }
        }
      });

    mList.setAdapter(adapter);
    if (listResponse.getNovel().getTranslator() != null)
      tvTranslator.setText("مترجم: " + listResponse.getNovel().getTranslator());
    else
      tvTranslator.setVisibility(View.GONE);
    tvOriginalTitle.setText(listResponse.getNovel().getOriginalTitle());
    tvRating.setText(Utils.toPersianNumber(String.format("%.2f", listResponse.getNovel().getRate())));
    tvRating.setTypeface(FontUtil.getTypeface(getApplicationContext(), FontUtil.FontType.IranSansBold));
    tvPublisher.setText("ناشر: " + listResponse.getNovel().getPublisher());
    tvAuthor.setText(listResponse.getNovel().getAuthor());
    tvTitle.setText(listResponse.getNovel().getTitle());
    mNovelUrl = listResponse.getNovel().getUrl();
    mAuthorNovlerId = listResponse.getNovel().getAuthorNovlerId();
    mCoverUrl = listResponse.getNovel().getCover();
    Glide.with(getApplicationContext()).load(mCoverUrl)
      .bitmapTransform(new RoundedCornersTransformation(getApplicationContext(), 12, 5))
      .placeholder(R.drawable.novel_placeholder)
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

  public Action getIndexApiAction() {
    if (mTitle == null)
      mTitle = "";

    Thing object = new Thing.Builder()
      .setName(mTitle)
      .setUrl(Uri.parse("https://novler.com/_novel/" + mNovlerId))
      .build();
    return new Action.Builder(Action.TYPE_VIEW)
      .setObject(object)
      .setActionStatus(Action.STATUS_TYPE_COMPLETED)
      .build();
  }

  @Override
  public void onStart() {
    super.onStart();
    client.connect();
    AppIndex.AppIndexApi.start(client, getIndexApiAction());
  }

  @Override
  public void onStop() {
    super.onStop();
    AppIndex.AppIndexApi.end(client, getIndexApiAction());
    client.disconnect();
  }

  private void getIntents() {
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
