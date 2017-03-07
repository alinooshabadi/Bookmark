package com.novler.quotes.ui.novel;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.novler.quotes.R;
import com.novler.quotes.models.NovelData;
import com.novler.quotes.util.FontUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.ColorFilterTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class NovelsAdapter extends RecyclerView.Adapter<NovelsAdapter.ViewHolder> {
  private final OnItemClickListener listener;
  private List<NovelData> data;
  private Context context;
  private Activity activity;

  public NovelsAdapter(Activity activity, Context context, List<NovelData> data, OnItemClickListener listener) {
    this.data = data;
    this.activity = activity;
    this.listener = listener;
    this.context = context;
  }


  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_featured_novel, null);
    view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
    return new ViewHolder(view);
  }

  @Override
  public void onViewDetachedFromWindow(ViewHolder holder) {
    super.onViewDetachedFromWindow(holder);
    holder.itemView.clearAnimation();
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
   /* int lastPosition = -1;
    Animation animation = AnimationUtils.loadAnimation(context,
      (position > lastPosition) ? R.anim.up_from_bottom
        : R.anim.down_from_top);
    holder.itemView.startAnimation(animation);
    lastPosition = position;*/

    ArrayList<String> colors = new ArrayList<String>();
    String alpha = "#F2";

    colors.add(alpha+ "A3A948");
    colors.add(alpha+ "EDB92E");
    colors.add(alpha+ "F85931");
    colors.add(alpha+ "CE1836");
    colors.add(alpha+ "009989");

    colors.add(alpha+ "00A0B0");
    colors.add(alpha+ "6A4A3C");
    colors.add(alpha+ "CC333F");
    colors.add(alpha+ "EB6841");
    colors.add(alpha+ "EDC951");


    holder.click(data.get(position), listener);

    holder.tvNovelAuthor.setText(data.get(position).getAuthor());
    holder.tvNovelAuthor.setTypeface(FontUtil.getTypeface(context, FontUtil.FontType.IranSansBold));
    holder.tvNovelTitle.setText(data.get(position).getTitle());
    holder.tvNovelTitle.setTypeface(FontUtil.getTypeface(context, FontUtil.FontType.IranSansBold));
    holder.tvNovelOriginalTitle.setText(data.get(position).getOriginalTitle());


    String images = data.get(position).getCover();
    Glide.with(context).load(images)
      .placeholder(R.drawable.novel_placeholder)
      .bitmapTransform(new RoundedCornersTransformation(context, 10, 5))
      .error(R.drawable.novel_placeholder)
      .crossFade()
      .diskCacheStrategy(DiskCacheStrategy.SOURCE)
      .skipMemoryCache(true)
      .into(holder.ivCover);

    Glide.with(context).load(images)
      .placeholder(R.drawable.background_placeholder)
      .bitmapTransform(new ColorFilterTransformation(context, Color.parseColor(colors.get(position%colors.size()))))
      .error(R.drawable.novel_placeholder)
      .diskCacheStrategy(DiskCacheStrategy.SOURCE)
      .skipMemoryCache(true)
      .into(holder.ivBackground);
  }


  @Override
  public int getItemCount() {
    return data.size();
  }


  public interface OnItemClickListener {
    void onClick(NovelData Item, View view);
  }


  class ViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.feature_cover)
    ImageView ivCover;

    @BindView(R.id.feature_background)
    ImageView ivBackground;

    @BindView(R.id.novel_author)
    TextView tvNovelAuthor;

    @BindView(R.id.novel_originalTitle)
    TextView tvNovelOriginalTitle;

    @BindView(R.id.novel_title)
    TextView tvNovelTitle;


    ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }


    void click(final NovelData listData, final OnItemClickListener listener) {
      ivBackground.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          listener.onClick(listData, v);
        }
      });
    }
  }


}