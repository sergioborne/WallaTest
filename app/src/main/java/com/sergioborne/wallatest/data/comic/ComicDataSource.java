package com.sergioborne.wallatest.data.comic;

import com.sergioborne.wallatest.domain.model.Comic;

import java.util.List;

public interface ComicDataSource {

  List<Comic> getComics(int limit, int characterId);
  List<Comic> getComics(int limit, int offset, int characterId);
}