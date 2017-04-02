package com.novler.quotes;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.afollestad.materialdialogs.color.ColorChooserDialog;
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

public class ExportInstagramActivity extends BaseApp implements ColorChooserDialog.ColorCallback {

  String mText, mAuthor, mNovel;
  float mTextSize = 14;
  ArrayList<String> colors = new ArrayList<>();

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
        new MaterialDialog.Builder(this)
          .title("عدم دسترسی")
          .titleGravity(GravityEnum.END)
          .contentGravity(GravityEnum.END)
          .buttonsGravity(GravityEnum.END)
          .typeface(FontUtil.getTypeface(getApplicationContext(), FontUtil.FontType.IranSansLight), FontUtil.getTypeface(getApplicationContext(), FontUtil.FontType.IranSansLight))
          .content("به دلیل عدم تایید دسترسی، این قسمت از برنامه قادر به اجرا نمی‌باشد. می‌توانید مجددا از طریق تنظیمات، دسترسی این قسمت را به برنامه بدهید.")
          .positiveText("تنظیمات اپلیکیشن")
          .neutralText("بیخیال")
          .onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
              Intent intent = new Intent();
              intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
              Uri uri = Uri.fromParts("package", getPackageName(), null);
              intent.setData(uri);
              startActivity(intent);
            }
          })
          .show();
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
    } else
      saveAndExportImage();
    return;
  }

  void saveAndExportImage() {
    Bitmap bitmap = getBitmapFromView(frmMain);

    File pictureFileDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Bookmark");
    if (!pictureFileDir.mkdirs()) {
      Log.i("Test", "This path is already exist: " + pictureFileDir.getAbsolutePath());
    }
    String filename = pictureFileDir.getPath() + File.separator + System.currentTimeMillis() + ".jpg";
    File pictureFile = new File(filename);

    try {

      if (pictureFile.createNewFile()) {
          FileOutputStream oStream = new FileOutputStream(pictureFile);
          bitmap.compress(Bitmap.CompressFormat.JPEG, 100, oStream);
          oStream.flush();
          oStream.close();

          frmMain.setDrawingCacheEnabled(false); // clear drawing cache

          String type = "image/*";
          ShareUtil.createInstagramIntent(this, type, filename);
        }
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
        }
      }
      // other 'case' lines to check for other
      // permissions this app might request
    }
  }

  @OnClick(copyToClipboard)
  void copyToClipboard() {

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
  void biggerText() {
    btnSmallerText.setEnabled(true);
    if (mTextSize < 24) {
      mTextSize = mTextSize + 1;
      tvText.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTextSize);
    } else
      btnBiggerText.setEnabled(false);
  }

  @OnClick(R.id.btn_smaller)
  void smallerText() {
    btnBiggerText.setEnabled(true);
    if (mTextSize > 12) {
      mTextSize = mTextSize - 1;
      tvText.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTextSize);
    } else
      btnSmallerText.setEnabled(false);
  }

  @OnClick(R.id.pallet_circle)
  void openPallet() {
    new ColorChooserDialog.Builder(this, R.string.select_background_color)
      //.typeface(FontUtil.getTypeface(getContext(), FontUtil.FontType.IranSansLight),FontUtil.getTypeface(getContext(), FontUtil.FontType.IranSansLight))
      .titleSub(R.string.select_background_color)  // title of dialog when viewing shades of a color
      .presetsButton(R.string.back)
      .accentMode(true)  // when true, will display accent palette instead of primary palette
      .doneButton(R.string.done)  // changes label of the done button
      .cancelButton(R.string.cancel)  // changes label of the cancel button
      .backButton(R.string.back)  // changes label of the back button
      .customButton(R.string.more)
      .theme(Theme.LIGHT)
      .dynamicButtonColor(true)  // defaults to true, false will disable changing action buttons' color to currently selected color
      .show();

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

    String alpha = "#FF";
    colors.add(alpha + "16a085");
    //colors.add(alpha + "C2185B");
    //colors.add(alpha + "7B1FA2");
    colors.add(alpha + "2980b9");
    colors.add(alpha + "8e44ad");
    colors.add(alpha + "c0392b");
    //colors.add(alpha + "388E3C");
    //colors.add(alpha + "FF8F00");
    colors.add(alpha + "2c3e50");
    //colors.add(alpha + "5D4037");
    //colors.add(alpha + "37474F");
    int index = new Random().nextInt(colors.size());
    frmMain.setBackgroundColor(Color.parseColor(colors.get(index)));

    setPalletControls();
  }

  void setPalletControls() {
    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams
      (Utils.dpToPx(getApplicationContext(), 28), Utils.dpToPx(getApplicationContext(), 28));
    lp.setMargins(Utils.dpToPx(getApplicationContext(), 2), Utils.dpToPx(getApplicationContext(), 2),
      Utils.dpToPx(getApplicationContext(), 2), Utils.dpToPx(getApplicationContext(), 2));

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
    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
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

  @Override
  public void onColorSelection(@NonNull ColorChooserDialog colorChooserDialog, @ColorInt int color) {
    frmMain.setBackgroundColor(color);
  }

  @Override public void onColorChooserDismissed(@NonNull ColorChooserDialog colorChooserDialog) {

  }
}
