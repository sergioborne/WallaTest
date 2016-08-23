package com.sergioborne.wallatest.app.di;

import com.sergioborne.wallatest.domain.repository.ComicRepository;
import com.sergioborne.wallatest.domain.repository.SimpleComicRepository;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module public class RepositoryModule {

  @Provides @Singleton
  public ComicRepository provideComicRepository() {
    return new SimpleComicRepository();
  }
}
