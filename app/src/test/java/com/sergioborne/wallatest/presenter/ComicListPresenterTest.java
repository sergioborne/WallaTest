package com.sergioborne.wallatest.presenter;

import android.test.suitebuilder.annotation.LargeTest;
import com.sergioborne.wallatest.domain.interactor.UpdateComicsUseCase;
import com.sergioborne.wallatest.domain.model.Comic;
import com.sergioborne.wallatest.domain.repository.ComicRepository;
import com.sergioborne.wallatest.ui.list.ComicListView;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import rx.Observable;
import rx.Subscriber;
import rx.subjects.PublishSubject;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class) @LargeTest public class ComicListPresenterTest {

  ComicListPresenter comicListPresenter;
  UpdateComicsUseCase updateComicsUseCase;
  ComicRepository comicRepository;
  ComicListView view;

  @Before public void setup() {
    updateComicsUseCase = mock(UpdateComicsUseCase.class);
    comicRepository = mock(ComicRepository.class);
    view = mock(ComicListView.class);
    comicListPresenter = new ComicListPresenter(updateComicsUseCase, comicRepository);
    comicListPresenter.setView(view);
  }

  @Test public void testFetchMoreComics() {
    doAnswer(invocation -> {
      Subscriber<List<Comic>> subscriber =
          (Subscriber<List<Comic>>) invocation.getArguments()[1];
      Observable.just(new ArrayList<Comic>())
          .subscribe(subscriber);
      return null;
    }).when(updateComicsUseCase).execute(anyInt(), anyObject());

    when(comicRepository.count()).thenReturn(10);
    comicListPresenter.fetchMoreComics();

    verify(view).hideLoadingIndicator();
    verify(view).hideBottomLoadingIndicator();
    verify(view).showBottomLoadingIndicator();
  }

  @Test public void testFetchMoreComicsEmptyRepository() {
    doAnswer(invocation -> {
      Subscriber<List<Comic>> subscriber =
          (Subscriber<List<Comic>>) invocation.getArguments()[1];
      Observable.just(new ArrayList<Comic>())
          .subscribe(subscriber);
      return null;
    }).when(updateComicsUseCase).execute(anyInt(), anyObject());

    when(comicRepository.count()).thenReturn(0);

    comicListPresenter.fetchMoreComics();

    verify(view).hideLoadingIndicator();
    verify(view).hideBottomLoadingIndicator();
    verify(view).showLoadingIndicator();
  }

  @Test public void testFetchMoreComicsError() {
    PublishSubject<List<Comic>> subject = PublishSubject.create();
    doAnswer(invocation -> {
      Subscriber<List<Comic>> subscriber =
          (Subscriber<List<Comic>>) invocation.getArguments()[1];
      subject
          .subscribe(subscriber);
      return null;
    }).when(updateComicsUseCase).execute(anyInt(), anyObject());

    when(comicRepository.count()).thenReturn(0);

    comicListPresenter.fetchMoreComics();
    subject.onError(new Throwable());

    verify(view).hideLoadingIndicator();
    verify(view).hideBottomLoadingIndicator();
  }

  @Test public void testClearComics() {
    comicListPresenter.clearComics();
    verify(comicRepository).erase();
  }

  @Test public void testLifeCycle() {
    comicListPresenter.resume();
    comicListPresenter.pause();
    comicListPresenter.destroy();

    verify(updateComicsUseCase).unsubscribe();
  }

}
