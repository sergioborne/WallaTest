/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sergioborne.wallatest.domain.interactor;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

/**
 * Abstract class for a Use Case (Interactor in terms of Clean Architecture).
 * This interface represents a execution unit for different use cases (this means any use case
 * in the application should implement this contract).
 *
 * By convention each UseCase implementation will return the result using a {@link Subscriber}
 * that will execute its job in a background thread and will post the result in the UI thread.
 */
public abstract class UseCase<T> {

  private Subscription subscription = Subscriptions.empty();
  private SchedulerProvider schedulerProvider = new SchedulerProvider() {
    @Override public Scheduler computationThread() {
      return Schedulers.computation();
    }

    @Override public Scheduler uiThread() {
      return AndroidSchedulers.mainThread();
    }
  };

  protected UseCase() {

  }

  /**
   * Builds an {@link Observable} which will be used when executing the current {@link UseCase}.
   */
  protected abstract Observable<T> buildUseCaseObservable();

  /**
   * Executes the current use case.
   *
   * @param useCaseSubscriber The guy who will be listen to the observable build
   * with {@link #buildUseCaseObservable()}.
   */
  @SuppressWarnings("unchecked") public void execute(@NonNull Subscriber<T> useCaseSubscriber) {
    this.subscription = this.buildUseCaseObservable()
        .subscribeOn(schedulerProvider.computationThread())
        .observeOn(schedulerProvider.uiThread())
        .subscribe(useCaseSubscriber);
  }


  protected Scheduler backgroundThread() {
    return schedulerProvider.computationThread();
  }

  protected Scheduler uiThread() {
    return schedulerProvider.uiThread();
  }

  @VisibleForTesting public void setSchedulerProvider(SchedulerProvider schedulerProvider) {
    this.schedulerProvider = schedulerProvider;
  }

  /**
   * Unsubscribes from current {@link Subscription}.
   */
  public void unsubscribe() {
    if (!subscription.isUnsubscribed()) {
      subscription.unsubscribe();
    }
  }

  public interface SchedulerProvider {
    Scheduler computationThread();

    Scheduler uiThread();
  }
}
