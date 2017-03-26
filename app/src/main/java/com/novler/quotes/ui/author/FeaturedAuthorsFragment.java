package com.novler.quotes.ui.author;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.novler.quotes.R;
import com.novler.quotes.models.AuthorData;
import com.novler.quotes.models.ResponseData;
import com.novler.quotes.networking.Service;
import com.novler.quotes.presenter.HomePresenter;
import com.novler.quotes.ui.home.BaseView;
import com.novler.quotes.ui.home.HomeActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FeaturedAuthorsFragment extends Fragment implements BaseView {
  @Inject
  public Service service;

  @Nullable
  @BindView(R.id.reload)
  TextView tvReload;
  @BindView(R.id.authors_list)
  RecyclerView list;
  @BindView(R.id.swipeRefreshLayout)
  SwipeRefreshLayout swipeRefreshLayout;
  HomePresenter presenter = null;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    setRetainInstance(true);
    View view = inflater.inflate(R.layout.fragment_featured_authors, container, false);
    ButterKnife.bind(this, view);
    swipeRefreshLayout.setColorSchemeResources(R.color.lineRed,
      R.color.lineBlue,
      R.color.lineOrange,
      R.color.linePurple);

    LinearLayoutManager mLayoutManager = new LinearLayoutManager(this.getActivity());
    list.setLayoutManager(mLayoutManager);
    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        getItems();
      }
    });
    getItems();
    return view;
  }

  void getItems() {
    if (presenter == null) {
      HomePresenter presenter = new HomePresenter(((HomeActivity) getActivity()).service, this);
      presenter.getAuthorsFeaturedList();
    }

  }

  @OnClick(R.id.reload)
  public void reloadList() {
    getItems();
  }

  @Override
  public void showWait() {
    swipeRefreshLayout.setRefreshing(true);
    tvReload.setVisibility(View.GONE);
  }

  @Override
  public void removeWait() {
    swipeRefreshLayout.setRefreshing(false);
    tvReload.setVisibility(View.GONE);
  }

  @Override
  public void onFailure(String appErrorMessage) {
    tvReload.setVisibility(View.VISIBLE);
  }

  @Override public void getListSuccess(ResponseData listResponse) {
    AuthorsAdapter adapter = new AuthorsAdapter(getActivity(), getActivity().getApplicationContext(), listResponse.getAuthors(),
      new AuthorsAdapter.OnItemClickListener() {
        @Override public void onClick(AuthorData Item, View view) {

          Intent intent = new Intent(getActivity(), AuthorActivity.class);
          Pair<View, String> pair1 = Pair.create(view, "novel_title");
          Pair<View, String> pair2 = Pair.create(view, "novel_author");
          Pair<View, String> pair3 = Pair.create(view, "novel_cover");
          ActivityOptionsCompat options = ActivityOptionsCompat.
            makeSceneTransitionAnimation(getActivity(), pair1, pair2, pair3);

          Bundle bundle = new Bundle();
          bundle.putString("title", Item.getTitle());
          bundle.putInt("novelId", Item.getId());
          bundle.putString("novlerId", Item.getNovlerId());
          intent.putExtras(bundle);
          startActivity(intent, options.toBundle());
        }
      });

    list.setAdapter(adapter);
  }
}
