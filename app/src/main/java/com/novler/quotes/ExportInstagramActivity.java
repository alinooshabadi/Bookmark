package com.novler.quotes;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.novler.quotes.util.FontUtil;
import com.novler.quotes.util.customLayout.SquareLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.grantland.widget.AutofitHelper;

public class ExportInstagramActivity extends BaseApp {

  String mText,mAuthor, mNovel;

  @BindView(R.id.export)
  Button btnExport;

  @BindView(R.id.frame)
  SquareLayout frmMain;

  @BindView(R.id.toolbar)
  Toolbar toolbar;
  @BindView(R.id.titleBar)
  TextView tvTitleBar;

  @BindView(R.id.text)
  TextView tvText;
  @BindView(R.id.quote_author)
  TextView tvAuthor;
  @BindView(R.id.quote_novel)
  TextView tvNovel;


  @OnClick(R.id.export)
  void exportToInstagram(View view)
  {

    Bitmap bitmap = getBitmapFromView(frmMain);

    File pictureFileDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"Handcare");

    if(!pictureFileDir.mkdirs()) {
      Log.i("Test", "This path is already exist: " + pictureFileDir.getAbsolutePath());
    }
    String filename = pictureFileDir.getPath() + File.separator+ System.currentTimeMillis()+".jpg";
    File pictureFile = new File(filename);

    try {
      pictureFile.createNewFile();
      FileOutputStream oStream = new FileOutputStream(pictureFile);
      bitmap.compress(Bitmap.CompressFormat.JPEG, 100, oStream);
      oStream.flush();
      oStream.close();
    } catch (IOException e) {
      e.printStackTrace();
      Log.i("TAG", "There was an issue saving the image.");
    }
    frmMain.setDrawingCacheEnabled(false); // clear drawing cache
  }
  private Bitmap getBitmapFromView(View view) {
    //Define a bitmap with the same size as the view
    Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
    //Bind a canvas to it
    Canvas canvas = new Canvas(returnedBitmap);
    //Get the view's background
    Drawable bgDrawable =view.getBackground();
    if (bgDrawable!=null) {
      //has background drawable, then draw it on the canvas
      bgDrawable.draw(canvas);
    }   else{
      //does not have background drawable, then draw white background on the canvas
      canvas.drawColor(Color.WHITE);
    }
    // draw the view on the canvas
    view.draw(canvas);
    //return the bitmap
    return returnedBitmap;
  }
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_export_instagram);
    ButterKnife.bind(this);


    getIntents();
    SetToolbar();
    tvText.setText(mText);
    tvAuthor.setText(mAuthor);
    tvNovel.setText(mNovel);
    tvNovel.setTypeface(FontUtil.getTypeface(getApplicationContext(), FontUtil.FontType.IranSansBold));
    tvAuthor.setTypeface(FontUtil.getTypeface(getApplicationContext(), FontUtil.FontType.IranSansBold));

    AutofitHelper.create(tvText);


    ArrayList<String> colors = new ArrayList<String>();
    String alpha = "#FF";
    colors.add(alpha+ "c62828");
    colors.add(alpha+ "AD1457");
    colors.add(alpha+ "6A1B9A");
    colors.add(alpha+ "4527A0");
    colors.add(alpha+ "0288D1");
    colors.add(alpha+ "2979FF");
    colors.add(alpha+ "D84315");
    colors.add(alpha+ "4E342E");
    colors.add(alpha+ "d50000");
    colors.add(alpha+ "FF3D00");

    int index = new Random().nextInt(colors.size());
    frmMain.setBackgroundColor(Color.parseColor(colors.get(index)));

  }

  void SetToolbar() {
    tvTitleBar.setText(mNovel);
    toolbar.setTitle("");
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

  public void getIntents() {
    Bundle b = getIntent().getExtras();

    if (b != null) {
      mText = b.getString("text");
      mNovel = b.getString("title");
      mAuthor = b.getString("author");
    }


  }
}
