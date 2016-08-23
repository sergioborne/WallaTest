package com.sergioborne.wallatest.app;

import android.app.Application;
import android.support.annotation.NonNull;
import com.sergioborne.wallatest.app.di.Graph;

public class MainApplication extends Application {

  private static final String TAG = MainApplication.class.getSimpleName();
  private static MainApplication instance;
  protected Graph graph;

  @NonNull public static MainApplication getInstance() {
    return instance;
  }

  @Override public void onCreate() {
    instance = this;
    super.onCreate();
    initGraphAndInject();
  }

  protected void initGraphAndInject() {
    graph = Graph.Initializer.init(this);
    graph.inject(this);
  }

  public Graph getGraph() {
    return graph;
  }
}
