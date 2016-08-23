package com.sergioborne.wallatest.app.base;

public abstract class Presenter<T extends View> {

  protected T view;

  /**
   * The view must be attached to the presenter in order to work
   */
  public void attachView(T view) {
    this.view = view;
  }

  /**
   * Method that control the lifecycle of the view. It should be called in the view's
   * (Activity or Fragment) onResume() method.
   */
  public void resume() {
  }

  /**
   * Method that control the lifecycle of the view. It should be called in the view's
   * (Activity or Fragment) onPause() method.
   */
  public void pause() {
  }

  /**
   * Method that control the lifecycle of the view. It should be called in the view's
   * (Activity or Fragment) onDestroy() method.
   */
  public void destroy() {
  }
}
