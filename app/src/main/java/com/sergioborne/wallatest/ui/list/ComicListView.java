package com.sergioborne.wallatest.ui.list;

import com.sergioborne.wallatest.app.base.View;
import com.sergioborne.wallatest.domain.model.Comic;

public interface ComicListView extends View{

  void showComic(Comic comic);

  void showLoadingIndicator();

  void hideLoadingIndicator();

  void showBottomLoadingIndicator();

  void hideBottomLoadingIndicator();
}
