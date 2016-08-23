package com.sergioborne.wallatest.domain.repository;

import com.sergioborne.wallatest.domain.model.Comic;
import java.util.List;
import rx.Observable;

public interface ComicRepository {

  void add(Comic comic);

  void add(List<Comic> comics);

  List<Comic> getAll();

  Comic findById(int id);

  int count();

  void erase();

  boolean isEmpty();

  /**
   * Observe the updates received
   */
  Observable<Comic> observeChanges();

}
