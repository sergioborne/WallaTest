package com.sergioborne.wallatest.ui.list;

import com.sergioborne.wallatest.domain.model.Comic;

/**
 * Created by sergio.cores on 14/08/2016.
 */
public interface ComicListView {

  void showComic(Comic comic);

  void showLoadingIndicator();

  void hideLoadingIndicator();

  void showBottomLoadingIndicator();

  void hideBottomLoadingIndicator();
}
