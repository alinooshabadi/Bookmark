package com.novler.quotes.home.QuoteTabs;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.ybq.android.spinkit.SpinKitView;
import com.novler.quotes.NovelActivity;
import com.novler.quotes.R;
import com.novler.quotes.home.HomeAdapter;
import com.novler.quotes.home.HomeView;
import com.novler.quotes.models.QuoteListData;
import com.novler.quotes.models.QuoteListResponse;
import com.novler.quotes.util.ShareUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by P on 2/21/2017.
 */

public class BaseQuotesFragment extends Fragment implements HomeView {
    @BindView(R.id.progress)
    SpinKitView progressBar;
    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_quotes_list, container, false);
        ButterKnife.bind(this, view);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this.getActivity());
        list.setLayoutManager(mLayoutManager);

        getItems();

        swipeRefreshLayout.setColorSchemeResources(R.color.lineRed,
                R.color.lineBlue,
                R.color.lineOrange,
                R.color.linePurple);


        return view;
    }

    public void getItems() {

    }

    @Override
    public void showWait() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void removeWait() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onFailure(String appErrorMessage) {

    }

    @Override
    public void getListSuccess(QuoteListResponse listResponse) {
        HomeAdapter adapter = new HomeAdapter(getActivity().getApplicationContext(), listResponse.getData(),
                new HomeAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(QuoteListData Item, View view) {
                        if (view.getId() == R.id.shareTelegram) {
                            ShareUtil.intentMessageTelegram(getActivity(), Item.getText());
                        }
                        else {
                            Intent intent = new Intent(getActivity(), NovelActivity.class);
                            ActivityOptionsCompat options = ActivityOptionsCompat.
                                    makeSceneTransitionAnimation(getActivity(), view, "novel_cover");

                            Bundle bundle = new Bundle();
                            bundle.putString("cover", Item.getNovelmage());
                            bundle.putString("title", Item.getNovel());
                            intent.putExtras(bundle);
                            startActivity(intent, options.toBundle());
                        }
                    }
                });

        list.setAdapter(adapter);
    }
}
