package com.sergioborne.wallatest.ui.list;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.widget.ImageView;
import android.widget.TextView;
import com.sergioborne.wallatest.R;
import com.sergioborne.wallatest.domain.model.Comic;
import com.squareup.picasso.Picasso;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.comic_element) public class ComicItemView extends CardView {

  @ViewById(R.id.item_content_container) CardView cardView;

  @ViewById TextView titleTextView;

  @ViewById ImageView coverImageView;

  private Context context;

  public ComicItemView(Context context) {
    super(context);
    this.context = context;
  }

  public void bind(Comic comic) {
    titleTextView.setText(comic.getTitle());

    Picasso.with(context).load(comic.getThumbnailUrl()).into(coverImageView);
  }
}
