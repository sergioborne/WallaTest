package com.sergioborne.wallatest.presenter;

import com.sergioborne.wallatest.app.base.Presenter;
import com.sergioborne.wallatest.domain.interactor.UpdateComicsUseCase;
import com.sergioborne.wallatest.domain.model.Comic;
import com.sergioborne.wallatest.domain.repository.ComicRepository;
import com.sergioborne.wallatest.ui.list.ComicListView;
import java.util.List;
import rx.Subscriber;

/**
 * Created by sergio.cores on 14/08/2016.
 */
public class ComicListPresenter extends Presenter {

  private static final String TAG = ComicListPresenter.class.getSimpleName();

  private ComicListView view;
  private UpdateComicsUseCase updateComicsUseCase;
  private ComicRepository comicRepository;
  private boolean fetching = false;

  public ComicListPresenter(UpdateComicsUseCase updateComicsUseCase,
      ComicRepository comicRepository) {
    this.updateComicsUseCase = updateComicsUseCase;
    this.comicRepository = comicRepository;
  }

  @Override public void resume() {
    //Do nothing
  }

  @Override public void pause() {
    //Do nothing
  }

  @Override public void destroy() {
    updateComicsUseCase.unsubscribe();
  }

  public void setView(ComicListView view) {
    this.view = view;
  }

  public void fetchMoreComics() {
    if (!fetching) {
      fetching = true;
      int comicCount = comicRepository.count();
      if (comicCount == 0) {
        view.showLoadingIndicator();
      } else {
        view.showBottomLoadingIndicator();
      }
      updateComicsUseCase.execute(comicCount, new Subscriber<List<Comic>>() {
        @Override public void onCompleted() {
          fetching = false;
          view.hideLoadingIndicator();
          view.hideBottomLoadingIndicator();
        }

        @Override public void onError(Throwable e) {
          fetching = false;
          view.hideLoadingIndicator();
          view.hideBottomLoadingIndicator();
          e.printStackTrace();
        }

        @Override public void onNext(List<Comic> comics) {
        }
      });
    }
  }

  public void subscribeToComicUpdates() {
    comicRepository.observeChanges().subscribe(new Subscriber<Comic>() {
      @Override public void onCompleted() {

      }

      @Override public void onError(Throwable e) {

      }

      @Override public void onNext(Comic comic) {
        view.showComic(comic);
      }
    });
  }

  public void clearComics(){
    comicRepository.erase();
  }
}
