package com.novler.quotes.ui.novel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.novler.quotes.BaseHomeApp;
import com.novler.quotes.R;
import com.novler.quotes.models.NovelData;
import com.novler.quotes.models.ResponseData;
import com.novler.quotes.networking.Service;
import com.novler.quotes.presenter.HomePresenter;
import com.novler.quotes.ui.home.BaseView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class FeaturedNovelsActivity extends BaseHomeApp implements BaseView {
  @Inject
  public Service service;
  @BindView(R.id.reload)
  LinearLayout reload;
  @BindView(R.id.list)
  RecyclerView list;
  @BindView(R.id.swipeRefreshLayout)
  SwipeRefreshLayout swipeRefreshLayout;
  HomePresenter presenter = null;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getDeps().inject(this);

    renderView(savedInstanceState);
    init();

    swipeRefreshLayout.setColorSchemeResources(R.color.lineRed,
      R.color.lineBlue,
      R.color.lineOrange,
      R.color.linePurple);

    getItems();
  }

  void getItems() {
    if (presenter == null) {
      presenter = new HomePresenter(service, this);
      presenter.getNovelFeaturedList();
    }
    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {

        presenter.getNovelFeaturedList();
      }
    });
  }

  @OnClick(R.id.reload)
  public void reloadList() {
    getItems();
  }


  @Override
  public void renderView(final Bundle savedInstanceState) {
    //(R.anim.fadein, R.anim.fadeout);
    setContentView(R.layout.activity_featured_novels);
    super.renderView(savedInstanceState);
    LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
    list.setLayoutManager(mLayoutManager);
  }

  public void init() {
    Bundle b = getIntent().getExtras();


  }

  @Override
  public void showWait() {
    swipeRefreshLayout.setRefreshing(true);
    reload.setVisibility(View.GONE);
  }

  @Override
  public void removeWait() {
    swipeRefreshLayout.setRefreshing(false);
    reload.setVisibility(View.GONE);
  }

  @Override
  public void onFailure(String appErrorMessage) {
    reload.setVisibility(View.VISIBLE);
    //Snackbar.make(list, appErrorMessage, Snackbar.LENGTH_INDEFINITE).show();
  }

  @Override public void getListSuccess(ResponseData listResponse) {
    NovelsAdapter adapter = new NovelsAdapter(this, this.getApplicationContext(), listResponse.getNovels(),
      new NovelsAdapter.OnItemClickListener() {

        @Override public void onClick(NovelData Item, View view) {
          Intent intent = new Intent(FeaturedNovelsActivity.this, NovelActivity.class);
          Pair<View, String> pair1 = Pair.create(view, "novel_title");
          Pair<View, String> pair2 = Pair.create(view, "novel_author");
          Pair<View, String> pair3 = Pair.create(view, "novel_cover");
          @SuppressWarnings("unchecked") ActivityOptionsCompat options = ActivityOptionsCompat.
            makeSceneTransitionAnimation(FeaturedNovelsActivity.this, pair1, pair2, pair3);

          Bundle bundle = new Bundle();
          bundle.putString("cover", Item.getCover());
          bundle.putString("title", Item.getTitle());
          bundle.putString("author", Item.getAuthor());
          bundle.putInt("novelId", Item.getId());
          bundle.putString("novlerId", Item.getNovlerId());
          intent.putExtras(bundle);
          startActivity(intent, options.toBundle());
        }
      });

    list.setAdapter(adapter);
  }


}
