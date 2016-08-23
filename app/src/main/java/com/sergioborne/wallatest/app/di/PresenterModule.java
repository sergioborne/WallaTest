package com.sergioborne.wallatest.app.di;

import com.sergioborne.wallatest.domain.interactor.UpdateComicsUseCase;
import com.sergioborne.wallatest.domain.repository.ComicRepository;
import com.sergioborne.wallatest.presenter.ComicListPresenter;
import dagger.Module;
import dagger.Provides;

@Module public class PresenterModule {

  @Provides ComicListPresenter provideComicListPresenter(UpdateComicsUseCase updateComicsUseCase,
      ComicRepository comicRepository) {
    return new ComicListPresenter(updateComicsUseCase, comicRepository);
  }
}
