package com.sergioborne.wallatest.app.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.sergioborne.wallatest.app.MainApplication;

public abstract class BaseActivity extends AppCompatActivity {
  protected String TAG = BaseActivity.class.getSimpleName();

  public BaseActivity() {
  }

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    inject();
  }

  @NonNull public MainApplication getApplicationInstance() {
    return (MainApplication) getApplication();
  }

  protected abstract void inject();

  @Override protected void onStart() {
    super.onStart();
  }

  @Override protected void onResume() {
    super.onResume();
  }

  @Override protected void onPause() {
    super.onPause();
  }

  @Override protected void onStop() {
    super.onStop();
  }

  @Override protected void onDestroy() {
    super.onDestroy();
  }

  @Override protected void onPostResume() {
    super.onPostResume();
  }
}
