package com.sergioborne.wallatest.domain.repository;

import android.content.res.Resources;
import com.annimon.stream.Stream;
import com.sergioborne.wallatest.domain.model.Comic;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * This is a simple implementation of a repository using in memory data.
 */

public class SimpleComicRepository implements ComicRepository {

  private HashMap<Integer, Comic> comicsHashMap;
  private PublishSubject<Comic> subject = PublishSubject.create();

  public SimpleComicRepository() {
    init();
  }

  private void init() {
    comicsHashMap = new HashMap<>();
  }

  @Override public void add(Comic comic) {
    if (!comicsHashMap.containsKey(comic.getId())) {
      comicsHashMap.put(comic.getId(), comic);
      subject.onNext(comic);
    }
  }

  @Override public void add(List<Comic> comics) {
    Stream.of(comics).forEach(comic -> add(comic));
  }

  @Override public List<Comic> getAll() {
    return new ArrayList<>(comicsHashMap.values());
  }

  @Override public Comic findById(int id) {
    if (comicsHashMap.containsKey(id)) {
      return comicsHashMap.get(id);
    } else {
      throw new Resources.NotFoundException();
    }
  }

  @Override public int count() {
    return comicsHashMap.size();
  }

  @Override public void erase() {
    comicsHashMap.clear();
  }

  @Override public boolean isEmpty() {
    return comicsHashMap.isEmpty();
  }

  @Override public Observable<Comic> observeChanges() {
    return subject.asObservable();
  }
}
