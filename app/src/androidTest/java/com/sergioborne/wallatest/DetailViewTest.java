package com.sergioborne.wallatest;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.sergioborne.wallatest.ui.list.ComicListActivity_;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class DetailViewTest {

  @Rule public ActivityTestRule<ComicListActivity_> mActivityRule =
      new ActivityTestRule<>(ComicListActivity_.class);

  @Test
  public void testGoToDetailView() throws InterruptedException {
    wait(5);
    onView(withId(R.id.comic_list)).perform(
        RecyclerViewActions.actionOnItemAtPosition(0, click()));
    wait(5);
    onView(withId(R.id.descriptionText)).check(matches(isDisplayed()));
  }

  private void wait(int seconds) throws InterruptedException {
    Thread.sleep(seconds * 1000);
  }
}
