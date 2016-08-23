package com.sergioborne.wallatest.app.di;

import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module public class ApplicationModule {

  private final Application application;

  public ApplicationModule(Application application) {
    this.application = application;
  }

  @Provides @Singleton @ForApplication Context providesApplicationContext() {
    return application;
  }

  @Provides @Singleton Application providesApplication() {
    return application;
  }

  @Provides @Singleton ContentResolver contentResolver(@ForApplication Context context) {
    return context.getContentResolver();
  }
}