package com.novler.quotes.ui.home;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.novler.quotes.R;
import com.novler.quotes.models.QuoteListData;
import com.novler.quotes.ui.novel.NovelActivity;
import com.novler.quotes.util.RoundedImageView;
import com.novler.quotes.util.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
  private final OnItemClickListener listener;
  private List<QuoteListData> data;
  private Context context;
  private Activity activity;

  public HomeAdapter(Activity activity, Context context, List<QuoteListData> data, OnItemClickListener listener) {
    this.data = data;
    this.activity = activity;
    this.listener = listener;
    this.context = context;
  }


  @Override
  public HomeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quote, null);
    view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
    return new ViewHolder(view);
  }

  @Override
  public void onViewDetachedFromWindow(ViewHolder holder) {
    super.onViewDetachedFromWindow(holder);
    holder.itemView.clearAnimation();
  }

  @Override
  public void onBindViewHolder(HomeAdapter.ViewHolder holder, int position) {
    int lastPosition = -1;
    Animation animation = AnimationUtils.loadAnimation(context,
      (position > lastPosition) ? R.anim.up_from_bottom
        : R.anim.down_from_top);
    holder.itemView.startAnimation(animation);
    lastPosition = position;

    if (activity.getClass() == NovelActivity.class)
      holder.titleCoverContainer.setVisibility(View.GONE);

    holder.novelId = data.get(position).getId();
    holder.share(data.get(position), listener);
    holder.click(data.get(position), listener);
    holder.tvUser.setText("انتخاب شده توسط : " + data.get(position).getUser());
    holder.tvAuthor.setText(data.get(position).getAuthor());
    holder.tvNovel.setText(data.get(position).getNovel());
    holder.tvDate.setText(data.get(position).getDateShamsi());

    if (position % 5 == 1)
      holder.parentLayout.setBackground(context.getResources().getDrawable(R.drawable.border_grey));
    else if (position % 5 == 2)
      holder.parentLayout.setBackground(context.getResources().getDrawable(R.drawable.border_red));
    else if (position % 5 == 3)
      holder.parentLayout.setBackground(context.getResources().getDrawable(R.drawable.border_blue));
    else if (position % 5 == 4)
      holder.parentLayout.setBackground(context.getResources().getDrawable(R.drawable.border_purple));
    else
      holder.parentLayout.setBackground(context.getResources().getDrawable(R.drawable.border_orange));

    holder.tvText.setText(Html.fromHtml(
      Util.clearText( data.get(position).getText())
    ));


    String images = data.get(position).getNovelImage();
    Glide.with(context).load(images)
      .placeholder(R.drawable.novel_placeholder)
      .error(R.drawable.novel_placeholder)
      .crossFade()
      .diskCacheStrategy(DiskCacheStrategy.SOURCE)
      .skipMemoryCache(true)
      .into(holder.cover);
  }


  @Override
  public int getItemCount() {
    return data.size();
  }


  public interface OnItemClickListener {
    void onClick(QuoteListData Item, View view);
  }


  class ViewHolder extends RecyclerView.ViewHolder {
    String novelId;
    String authorId;

    @BindView(R.id.titleCoverContainer)
    LinearLayout titleCoverContainer;
    @BindView(R.id.parentLayout)
    LinearLayout parentLayout;
    @BindView(R.id.user)
    TextView tvUser;
    @BindView(R.id.text)
    TextView tvText;
    @BindView(R.id.novel)
    TextView tvNovel;
    @BindView(R.id.dateShamsi)
    TextView tvDate;
    @BindView(R.id.author)
    TextView tvAuthor;
    @BindView(R.id.image)
    RoundedImageView cover;
    @BindView(R.id.shareTelegram)
    ImageView shareTelegram;


    ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    @OnClick(R.id.image)
    void submit(View view) {
      // TODO submit data to server...
    }


    void share(final QuoteListData listData, final OnItemClickListener listener) {
      shareTelegram.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          listener.onClick(listData, v);
        }
      });
    }

    void click(final QuoteListData listData, final OnItemClickListener listener) {
      cover.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          listener.onClick(listData, v);
        }
      });
    }
  }


}