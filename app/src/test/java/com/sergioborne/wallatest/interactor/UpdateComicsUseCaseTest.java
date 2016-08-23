package com.sergioborne.wallatest.interactor;

import com.sergioborne.wallatest.data.model.ComicsResponse;
import com.sergioborne.wallatest.domain.interactor.UpdateComicsUseCase;
import com.sergioborne.wallatest.domain.interactor.UseCase;
import com.sergioborne.wallatest.domain.mapper.MarvelComicsMapper;
import com.sergioborne.wallatest.domain.model.Comic;
import com.sergioborne.wallatest.domain.repository.ComicRepository;
import com.sergioborne.wallatest.network.MarvelAPI;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.runners.MockitoJUnitRunner;
import rx.Observable;
import rx.Scheduler;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class) public class UpdateComicsUseCaseTest {

  ComicRepository comicRepository;
  MarvelAPI marvelAPI;
  MarvelComicsMapper comicsMapper;
  UpdateComicsUseCase updateComicsUseCase;
  ComicsResponse comicsResponse;

  UseCase.SchedulerProvider schedulerProvider = new UseCase.SchedulerProvider() {
    private Scheduler scheduler = Schedulers.newThread();
    private Scheduler ui = Schedulers.newThread();

    @Override public Scheduler computationThread() {
      return scheduler;
    }

    @Override public Scheduler uiThread() {
      return ui;
    }
  };

  @Before public void setUp() {
    comicRepository = mock(ComicRepository.class);
    marvelAPI = mock(MarvelAPI.class);
    comicsMapper = mock(MarvelComicsMapper.class);
    comicsResponse = mock(ComicsResponse.class);

    updateComicsUseCase = new UpdateComicsUseCase(marvelAPI, comicRepository, comicsMapper);

    updateComicsUseCase.setSchedulerProvider(schedulerProvider);
  }

  @Test public void testSuccess() {
    Observable<ComicsResponse> comicsResponseObservable = Observable.just(comicsResponse);

    when(marvelAPI.getComics(anyInt(), anyInt(), anyInt())).thenReturn(comicsResponseObservable);
    when(comicsMapper.map(Matchers.<ComicsResponse>any())).thenReturn(new ArrayList<Comic>());

    TestSubscriber<List<Comic>> testSubscriber = new TestSubscriber<>();

    updateComicsUseCase.execute(0, testSubscriber);
    testSubscriber.awaitTerminalEvent();
    updateComicsUseCase.unsubscribe();

    testSubscriber.assertReceivedOnNext(new ArrayList<>());
    testSubscriber.assertCompleted();

    verify(comicRepository).add(new ArrayList<Comic>());
  }


}
