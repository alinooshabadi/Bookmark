package com.novler.quotes.ui.author;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.novler.quotes.BaseHomeApp;
import com.novler.quotes.R;
import com.novler.quotes.models.AuthorData;
import com.novler.quotes.models.ResponseData;
import com.novler.quotes.networking.Service;
import com.novler.quotes.presenter.HomePresenter;
import com.novler.quotes.ui.home.BaseView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class FeaturedAuthorsActivity extends BaseHomeApp implements BaseView {
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

    renderView();
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
      presenter.getAuthorsFeaturedList();
    }
    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {

        presenter.getAuthorsFeaturedList();
      }
    });
  }

  @OnClick(R.id.reload)
  public void reloadList() {
    getItems();
  }


  @Override
  public void renderView() {
    setContentView(R.layout.activity_featured_novels);
    super.renderView();
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
    AuthorsAdapter adapter = new AuthorsAdapter(this, this.getApplicationContext(), listResponse.getAuthors(),
      new AuthorsAdapter.OnItemClickListener() {


        @Override public void onClick(AuthorData Item, View view) {

        }
      });

    list.setAdapter(adapter);
  }


}
