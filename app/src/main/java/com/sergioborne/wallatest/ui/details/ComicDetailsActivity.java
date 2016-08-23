package com.sergioborne.wallatest.ui.details;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import com.sergioborne.wallatest.R;
import com.sergioborne.wallatest.app.base.BaseActivity;
import com.sergioborne.wallatest.domain.model.Comic;
import com.sergioborne.wallatest.utils.RandomUtils;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.comic_details_activity) public class ComicDetailsActivity extends BaseActivity {

  @ViewById(R.id.toolbar_layout) CollapsingToolbarLayout collapsingToolbarLayout;
  @ViewById(R.id.app_bar) AppBarLayout appBarLayout;
  @ViewById(R.id.detail_toolbar) Toolbar toolbar;
  @ViewById ImageView toolbarCoverImageView;
  @ViewById ImageView coverThumbnail;
  @ViewById TextView titleText;
  @ViewById TextView descriptionText;
  @Extra Comic comic;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override protected void inject() {
    getApplicationInstance().getGraph().inject(this);
  }

  @AfterViews public void initToolbar() {
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
      boolean isShow = false;
      int scrollRange = -1;

      @Override
      public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (scrollRange == -1) {
          scrollRange = appBarLayout.getTotalScrollRange();
        }
        if (scrollRange + verticalOffset == 0) {
          collapsingToolbarLayout.setTitle(comic.getTitle());
          isShow = true;
        } else if(isShow) {
          collapsingToolbarLayout.setTitle(" ");
          isShow = false;
        }
      }
    });
  }

  @AfterViews void afterViews() {
    ArrayList<String> images = (ArrayList<String>) comic.getImagesUrls();
    if (images != null && images.size() > 0) {
      Picasso.with(this)
          .load(images.get(RandomUtils.getRandomNumber(0, images.size() - 1)))
          .into(toolbarCoverImageView);
    }
    Picasso.with(this).load(comic.getThumbnailUrl()).into(coverThumbnail);

    titleText.setText(comic.getTitle());
    descriptionText.setText(comic.getDescription());
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      // Respond to the action bar's Up/Home button
      case android.R.id.home:
        supportFinishAfterTransition();
        return true;
    }
    return super.onOptionsItemSelected(item);
  }
}
