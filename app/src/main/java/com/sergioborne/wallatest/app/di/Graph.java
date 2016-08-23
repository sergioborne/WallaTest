package com.sergioborne.wallatest.app.di;

import android.app.Application;

import com.sergioborne.wallatest.ui.details.ComicDetailsActivity;
import com.sergioborne.wallatest.ui.list.ComicListFragment;
import com.sergioborne.wallatest.ui.list.ComicListActivity;
import com.sergioborne.wallatest.app.MainApplication;

import javax.inject.Singleton;

import dagger.Component;

@Singleton @Component(modules = {
    ApplicationModule.class, NetworkModule.class, PresenterModule.class, UseCaseModule.class,
    RepositoryModule.class
}) public interface Graph {

  void inject(MainApplication mainApplication);

  void inject(ComicListActivity comicListActivity);

  void inject(ComicDetailsActivity comicDetailsActivity);

  void inject(ComicListFragment comicListFragment);

  final class Initializer {

    public static Graph init(Application application) {

      return DaggerGraph.builder().applicationModule(new ApplicationModule(application)).build();
    }
  }
}
