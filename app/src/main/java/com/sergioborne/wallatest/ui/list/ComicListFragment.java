package com.sergioborne.wallatest.ui.list;

import android.content.res.Configuration;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import com.sergioborne.wallatest.R;
import com.sergioborne.wallatest.app.MainApplication;
import com.sergioborne.wallatest.domain.model.Comic;
import com.sergioborne.wallatest.presenter.ComicListPresenter;
import com.sergioborne.wallatest.ui.common.ItemTouchListenerAdapter;
import com.sergioborne.wallatest.ui.details.ComicDetailsActivity_;
import javax.inject.Inject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.comic_list_fragment) public class ComicListFragment extends Fragment
    implements ComicListView, SwipeRefreshLayout.OnRefreshListener {

  private final static String TAG = ComicListFragment.class.getSimpleName();

  @ViewById RecyclerView comicList;
  @ViewById SwipeRefreshLayout swipeRefreshLayout;
  @ViewById ProgressBar progressBar;

  @Bean ComicsAdapter adapter;

  @Inject ComicListPresenter presenter;

  private GridLayoutManager mGridLayoutManager;

  public ComicListFragment() {
    MainApplication.getInstance().getGraph().inject(this);
  }

  @AfterViews public void init() {
    Log.d(TAG, "init");
    presenter.setView(this);
    initRecyclerView();
    presenter.subscribeToComicUpdates();
    presenter.fetchMoreComics();
  }

  private void initRecyclerView() {
    Log.d(TAG, "initRecyclerView");
    mGridLayoutManager = new GridLayoutManager(getContext(), 2);
    comicList.setLayoutManager(mGridLayoutManager);
    comicList.setAdapter(adapter);
    comicList.addOnScrollListener(new BottomScrollListener());
    comicList.addOnItemTouchListener(
        new ItemTouchListenerAdapter(comicList, new ComicClickListener()));
    swipeRefreshLayout.setOnRefreshListener(this);
  }

  @Override public void showComic(Comic comic) {
    Log.d(TAG, "showComic: " + comic.getId());
    adapter.addComic(comic);
    adapter.notifyItemInserted(adapter.getItemCount());
    adapter.notifyDataSetChanged();
  }

  @Override public void showLoadingIndicator() {
    swipeRefreshLayout.post(() -> {
      if (swipeRefreshLayout != null) swipeRefreshLayout.setRefreshing(true);
    });
  }

  @Override public void hideLoadingIndicator() {
    if (swipeRefreshLayout != null) swipeRefreshLayout.setRefreshing(false);
  }

  @Override public void showBottomLoadingIndicator() {
    if (progressBar != null) progressBar.setVisibility(View.VISIBLE);
  }

  @Override public void hideBottomLoadingIndicator() {
    if (progressBar != null) progressBar.setVisibility(View.INVISIBLE);
  }

  @Override public void onRefresh() {
    adapter.clearItems();
    presenter.clearComics();
    presenter.fetchMoreComics();
  }

  private class BottomScrollListener extends RecyclerView.OnScrollListener {
    @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
      int lastVisibleItemPosition = mGridLayoutManager.findLastCompletelyVisibleItemPosition() + 1;
      int modelsCount = mGridLayoutManager.getItemCount();

      if (lastVisibleItemPosition > modelsCount - 4) {
        presenter.fetchMoreComics();
      }
    }
  }

  private class ComicClickListener
      implements ItemTouchListenerAdapter.RecyclerViewOnItemClickListener {

    @Override public void onItemClick(RecyclerView parent, View clickedView, int position) {
      Comic comic = adapter.getItemAt(position);
      //((ComicListActivity) getActivity()).goToDetailsView(clickedView, comic);

      ActivityOptionsCompat activityOptions =
          ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
              // Now we provide a list of Pair items which contain the view we can transitioning
              // from, and the name of the view it is transitioning to, in the launched activity
              new Pair<View, String>(clickedView.findViewById(R.id.coverImageView),
                  "thumbnailCover"));

      // Now we can start the Activity, providing the activity options as a bundle
      ComicDetailsActivity_.intent(getContext())
          .comic(comic)
          .withOptions(activityOptions.toBundle())
          .start();
    }

    @Override public void onItemLongClick(RecyclerView parent, View clickedView, int position) {
      //Do nothing
    }
  }

  @Override public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    refreshGridColumns();
  }

  /*
  Workaround to update the column num when orientation changed
   */
  private void refreshGridColumns() {
    ((GridLayoutManager) comicList.getLayoutManager()).setSpanCount(
        getResources().getInteger(R.integer.grid_column_num));
  }
}
