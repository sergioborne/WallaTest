package com.sergioborne.wallatest.domain.interactor;

import android.support.annotation.NonNull;
import android.util.Log;
import com.sergioborne.wallatest.app.Constants;
import com.sergioborne.wallatest.data.model.ComicsResponse;
import com.sergioborne.wallatest.domain.mapper.MarvelComicsMapper;
import com.sergioborne.wallatest.domain.model.Comic;
import com.sergioborne.wallatest.domain.repository.ComicRepository;
import com.sergioborne.wallatest.network.MarvelAPI;
import java.util.List;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.internal.util.SubscriptionList;
import rx.subjects.BehaviorSubject;

public class UpdateComicsUseCase extends UseCase<List<Comic>> {

  private static final int MAX_COMICS_FIRST_REQUEST = 20;
  private static final int MAX_COMICS_PER_REQUEST = 10;

  private static final String TAG = UpdateComicsUseCase.class.getSimpleName();
  private static BehaviorSubject<List<Comic>> subject = null;
  private SubscriptionList subscriptionList = null;
  private MarvelAPI apiInterface;
  private MarvelComicsMapper mapper;
  private ComicRepository comicRepository;
  private int offset = 0;

  public UpdateComicsUseCase(MarvelAPI apiInterface, ComicRepository comicRepository, MarvelComicsMapper mapper) {
    this.apiInterface = apiInterface;
    this.mapper = mapper;
    this.comicRepository = comicRepository;
  }

  @Override protected Observable<List<Comic>> buildUseCaseObservable() {
    if (subject != null && !subject.hasCompleted() && !subject.hasThrowable()) {
      return subject;
    }

    subject = BehaviorSubject.create();
    subject.observeOn(uiThread());
    subject.subscribeOn(backgroundThread());

    updateComics(offset);
    return subject;
  }

  public void execute(@NonNull int offset, @NonNull Subscriber<List<Comic>> useCaseSubscriber) {
    this.offset = offset;
    this.execute(useCaseSubscriber);
  }

  private void updateComics(int offset) {
    Log.d(TAG, "updateComics: " + offset);
    subscriptionList = new SubscriptionList();

    Observable<ComicsResponse> observable =
        apiInterface.getComics(Constants.CAPTAIN_AMERICA_ID, MAX_COMICS_PER_REQUEST, offset);

    Subscription subscription;
    subscription = observable.subscribeOn(backgroundThread())
        .observeOn(uiThread())
        .subscribe(comicsResponse -> {
          List<Comic> comics = mapper.map(comicsResponse);
          comicRepository.add(comics);
          subject.onNext(comics);
          subject.onCompleted();
        }, throwable -> {
          subject.onError(throwable);
          subject.onCompleted();
        });
    subscriptionList.add(subscription);
  }

  @Override public void unsubscribe() {
    super.unsubscribe();
    if (subscriptionList != null) {
      subscriptionList.unsubscribe();
      subscriptionList = null;
    }
    subject = null;
  }
}
