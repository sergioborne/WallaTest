package com.sergioborne.wallatest.ui.list;

import android.content.Context;
import android.view.ViewGroup;
import com.annimon.stream.Stream;
import com.sergioborne.wallatest.domain.model.Comic;
import com.sergioborne.wallatest.ui.common.RecyclerViewAdapterBase;
import com.sergioborne.wallatest.ui.common.ViewWrapper;
import java.util.List;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

@EBean public class ComicsAdapter extends RecyclerViewAdapterBase<Comic, ComicItemView> {

  @RootContext Context context;

  @Override protected ComicItemView onCreateItemView(ViewGroup parent, int viewType) {
    return ComicItemView_.build(context);
  }

  @Override public void onBindViewHolder(ViewWrapper<ComicItemView> viewHolder, int position) {
    ComicItemView view = viewHolder.getView();
    Comic comic = items.get(position);

    view.bind(comic);
  }

  @Override public int getItemCount() {
    return items.size();
  }

  public void addComic(Comic comic) {
    items.add(comic);
  }

  public void addComics(List<Comic> comics) {
    Stream.of(comics).forEach(comic -> addComic(comic));
    notifyDataSetChanged();
  }
}
