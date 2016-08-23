package com.sergioborne.wallatest.app.di;

import com.sergioborne.wallatest.app.Constants;
import com.sergioborne.wallatest.network.MarvelAPI;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module public final class NetworkModule {
  @Provides @Singleton MarvelAPI provideNetworkApi() {
    return new MarvelAPI(Constants.API_BASE_URI, Constants.PUBLIC_KEY, Constants.PRIVATE_KEY);
  }

}
