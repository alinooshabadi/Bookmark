package com.novler.quotes;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.novler.quotes.util.FontUtil;
import com.novler.quotes.util.ShareUtil;
import com.novler.quotes.util.Utils;
import com.novler.quotes.util.customLayout.FancyButton;
import com.novler.quotes.util.customLayout.SquareLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.novler.quotes.R.id.copyToClipboard;
import static com.novler.quotes.R.id.frame;

public class ExportInstagramActivity extends BaseApp {

  String mText, mAuthor, mNovel;
  float mTextSize = 14;
  ArrayList<String> colors = new ArrayList<String>();

  @BindView(frame)
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

  @BindView(R.id.btn_bigger)
  FancyButton btnBiggerText;
  @BindView(R.id.btn_smaller)
  FancyButton btnSmallerText;
  @BindView(R.id.pallet)
  LinearLayout pallet;

  @OnClick(R.id.shareToInstagram)
  void exportToInstagram(View view) {
    if (ContextCompat.checkSelfPermission(ExportInstagramActivity.this,
      android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
      != PackageManager.PERMISSION_GRANTED) {

      // Should we show an explanation?
      if (ActivityCompat.shouldShowRequestPermissionRationale(ExportInstagramActivity.this,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

        // Show an explanation to the user *asynchronously* -- don't block
        // this thread waiting for the user's response! After the user
        // sees the explanation, try again to request the permission.

      } else {

        // No explanation needed, we can request the permission.

        ActivityCompat.requestPermissions(ExportInstagramActivity.this,
          new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
          1);

        // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
        // app-defined int constant. The callback method gets the
        // result of the request.
      }
    }
    else
      saveAndExportImage();

  }

  void saveAndExportImage()
  {
    Bitmap bitmap = getBitmapFromView(frmMain);

    File pictureFileDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Bookmark");
    if (!pictureFileDir.mkdirs()) {
      Log.i("Test", "This path is already exist: " + pictureFileDir.getAbsolutePath());
    }
    String filename = pictureFileDir.getPath() + File.separator + System.currentTimeMillis() + ".jpg";
    File pictureFile = new File(filename);

    try {
      pictureFileDir.mkdir();
      boolean f = pictureFile.createNewFile();
      FileOutputStream oStream = new FileOutputStream(pictureFile);
      bitmap.compress(Bitmap.CompressFormat.JPEG, 100, oStream);
      oStream.flush();
      oStream.close();

      frmMain.setDrawingCacheEnabled(false); // clear drawing cache

      String type = "image/*";
      ShareUtil.createInstagramIntent(this, type, filename);
    } catch (Exception e) {
      e.printStackTrace();
      Toast.makeText(this, "There was an issue saving the image.", Toast.LENGTH_SHORT).show();
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode,
                                         String permissions[], int[] grantResults) {
    switch (requestCode) {
      case 1: {
        // If request is cancelled, the result arrays are empty.
        if (grantResults.length > 0
          && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

          saveAndExportImage();

        } else {

          // permission denied, boo! Disable the
          // functionality that depends on this permission.
        }
        return;
      }

      // other 'case' lines to check for other
      // permissions this app might request
    }
  }

  @OnClick(copyToClipboard)
  void copyToClipboard(View view) {
    String copiedText = Utils.clearText(mText
      + "\r\n" + "\r\n"
      + mNovel
      + "\r\n"
      + mAuthor
      + "\r\n"
      + "@novler"
    );
    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
    ClipData clip = ClipData.newPlainText("bookmark", copiedText);
    clipboard.setPrimaryClip(clip);
    Toast.makeText(this, "متن در حافظه کپی شد", Toast.LENGTH_SHORT).show();
  }

  @OnClick(R.id.btn_bigger)
  void biggrText(View view) {
    btnSmallerText.setEnabled(true);
    if (mTextSize < 20) {
      mTextSize = mTextSize + 1;
      tvText.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTextSize);
    } else
      btnBiggerText.setEnabled(false);
  }

  @OnClick(R.id.btn_smaller)
  void smallerText(View view) {
    btnBiggerText.setEnabled(true);
    if (mTextSize > 12) {
      mTextSize = mTextSize - 1;
      tvText.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTextSize);
    } else
      btnSmallerText.setEnabled(false);
  }

  private Bitmap getBitmapFromView(View view) {
    //Define a bitmap with the same size as the view
    Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
    //Bind a canvas to it
    Canvas canvas = new Canvas(returnedBitmap);
    //Get the view's background
    Drawable bgDrawable = view.getBackground();
    if (bgDrawable != null) {
      //has background drawable, then draw it on the canvas
      bgDrawable.draw(canvas);
    } else {
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
    overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    setContentView(R.layout.activity_export_instagram);
    ButterKnife.bind(this);

    getIntents();
    setToolbar();
    tvText.setText(mText);
    tvAuthor.setText(mAuthor);
    tvNovel.setText(mNovel);
    tvNovel.setTypeface(FontUtil.getTypeface(getApplicationContext(), FontUtil.FontType.IranSansBold));
    tvAuthor.setTypeface(FontUtil.getTypeface(getApplicationContext(), FontUtil.FontType.IranSansBold));

    //AutofitHelper.create(tvText);

    String alpha = "#FF";
    colors.add(alpha + "d32f2f");
    colors.add(alpha + "C2185B");
    colors.add(alpha + "7B1FA2");
    colors.add(alpha + "512DA8");
    colors.add(alpha + "1976D2");
    colors.add(alpha + "00796B");
    colors.add(alpha + "388E3C");
    colors.add(alpha + "FF8F00");
    colors.add(alpha + "E64A19");
    colors.add(alpha + "5D4037");
    colors.add(alpha + "37474F");
    int index = new Random().nextInt(colors.size());
    frmMain.setBackgroundColor(Color.parseColor(colors.get(index)));

    setPalletControls();
  }

  void setPalletControls() {
    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams
      (Utils.dpToPx(getApplicationContext(), 18), Utils.dpToPx(getApplicationContext(), 18));
    lp.setMargins(Utils.dpToPx(getApplicationContext(), 1), Utils.dpToPx(getApplicationContext(), 1),
      Utils.dpToPx(getApplicationContext(), 1), Utils.dpToPx(getApplicationContext(), 1));

    for (int i = 0; i < colors.size(); i = i + 1) {
      FancyButton btnColored = new FancyButton(this);
      btnColored.setBackgroundColor(Color.parseColor(colors.get(i)));
      btnColored.setDrawingCacheBackgroundColor(Color.parseColor(colors.get(i)));
      btnColored.setRadius(42);
      btnColored.setFocusBackgroundColor(Color.WHITE);
      btnColored.setText("");
      btnColored.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          frmMain.setBackgroundColor(v.getDrawingCacheBackgroundColor());
        }
      });
      pallet.addView(btnColored, lp);
    }

  }

  void setToolbar() {
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
