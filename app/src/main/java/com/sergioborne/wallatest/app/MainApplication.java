package com.sergioborne.wallatest.app;

import android.app.Application;
import android.support.annotation.NonNull;
import com.bluelinelabs.logansquare.LoganSquare;
import com.sergioborne.wallatest.app.di.Graph;
import com.sergioborne.wallatest.utils.JSONObjectConverter;
import org.json.JSONObject;

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
    initComponents();
  }

  protected void initGraphAndInject() {
    graph = Graph.Initializer.init(this);
    graph.inject(this);
  }

  protected void initComponents() {
    LoganSquare.registerTypeConverter(JSONObject.class, new JSONObjectConverter());
  }

  public Graph getGraph() {
    return graph;
  }
}
