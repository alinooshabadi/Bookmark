package com.novler.quotes.ui.quote;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.novler.quotes.BookmarkApplication;
import com.novler.quotes.ExportInstagramActivity;
import com.novler.quotes.R;
import com.novler.quotes.models.QuoteListData;
import com.novler.quotes.models.ResponseData;
import com.novler.quotes.ui.home.BaseView;
import com.novler.quotes.ui.novel.NovelActivity;
import com.novler.quotes.util.FontUtil;
import com.novler.quotes.util.ShareUtil;
import com.novler.quotes.util.Utils;
import com.novler.quotes.util.network.NetworkUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BaseQuotesFragment extends Fragment implements BaseView {

  @BindView(R.id.reload)
  LinearLayout tvReload;
  @BindView(R.id.list)
  RecyclerView list;
  @BindView(R.id.swipeRefreshLayout)
  SwipeRefreshLayout swipeRefreshLayout;

  @OnClick(R.id.reloadButton)
  public void reloadList() {
    getItems();
  }

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
    if (NetworkUtil.getConnectivityStatus(getContext()) == NetworkUtil.TYPE_NOT_CONNECTED) {
      new MaterialDialog.Builder(getContext())
        .title("ارتباط با اینترنت برقرار نیست")
        .titleGravity(GravityEnum.END)
        .contentGravity(GravityEnum.END)
        .buttonsGravity(GravityEnum.END)
        .theme(Theme.LIGHT)
        .typeface(FontUtil.getTypeface(getContext(), FontUtil.FontType.IranSansLight), FontUtil.getTypeface(getContext(), FontUtil.FontType.IranSansLight))
        .content("برای دریافت آخرین نقل‌قول‌ها، به اینترنت متصل شوید.")
        .positiveText("اینترنت WIFI")
        .negativeText("اینترنت موبایل")
        .neutralText("بیخیال")
        .onPositive(new MaterialDialog.SingleButtonCallback() {
          @Override
          public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
            startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
          }
        })
        .onNegative(new MaterialDialog.SingleButtonCallback() {
          @Override
          public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setClassName("com.android.phone", "com.android.phone.NetworkSetting");
            startActivity(intent);
          }
        })
        .show();
    }
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

  @Override
  public void getListSuccess(ResponseData listResponse) {
    final BookmarkApplication mApp = ((BookmarkApplication) getActivity().getApplicationContext());
    QuotesAdapter adapter = new QuotesAdapter(getActivity(), list, getActivity().getApplicationContext(), listResponse.getQuotes(),
      new QuotesAdapter.OnItemClickListener() {
        @Override
        public void onClick(QuoteListData Item, View view) {
          if (view.getId() == R.id.shareTelegram)
            ShareUtil.ShareTextQuote(getActivity(), Item.getText(), Item.getNovel(), Item.getAuthor());

          else if (view.getId() == R.id.shareInsta) {
            Intent intent = new Intent(getActivity(), ExportInstagramActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("title", Item.getNovel());
            bundle.putString("author", Item.getAuthor());
            bundle.putString("text", Utils.clearText(Item.getText()));
            intent.putExtras(bundle);
            startActivity(intent);
          } else {
            Intent intent = new Intent(getActivity(), NovelActivity.class);
            Pair<View, String> pair1 = Pair.create(view, "novel_title");
            Pair<View, String> pair2 = Pair.create(view, "novel_author");
            Pair<View, String> pair3 = Pair.create(view, "novel_cover");
            @SuppressWarnings("unchecked") ActivityOptionsCompat options = ActivityOptionsCompat.
              makeSceneTransitionAnimation(getActivity(), pair1, pair2, pair3);

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

    list.setAdapter(adapter);
  }
}
