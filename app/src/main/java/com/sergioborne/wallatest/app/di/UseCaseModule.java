package com.sergioborne.wallatest.app.di;

import com.sergioborne.wallatest.domain.interactor.UpdateComicsUseCase;
import com.sergioborne.wallatest.domain.mapper.MarvelComicsMapper;
import com.sergioborne.wallatest.domain.repository.ComicRepository;
import com.sergioborne.wallatest.network.MarvelAPI;
import dagger.Module;
import dagger.Provides;

@Module public class UseCaseModule {

  @Provides UpdateComicsUseCase provideLogoutUseCase(MarvelAPI apiInterface, ComicRepository comicRepository) {
    return new UpdateComicsUseCase(apiInterface, comicRepository, new MarvelComicsMapper());
  }
}
