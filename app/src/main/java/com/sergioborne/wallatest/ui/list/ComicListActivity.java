package com.sergioborne.wallatest.ui.list;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import com.sergioborne.wallatest.R;
import com.sergioborne.wallatest.app.base.BaseActivity;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.comic_list_activity) public class ComicListActivity extends BaseActivity {

  @ViewById Toolbar toolbar;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override protected void inject() {
    getApplicationInstance().getGraph().inject(this);
  }

  @AfterViews public void initToolbar() {
    setSupportActionBar(toolbar);
    toolbar.setTitle(getTitle());
  }
}
