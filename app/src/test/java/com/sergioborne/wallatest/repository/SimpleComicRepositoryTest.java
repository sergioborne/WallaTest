package com.sergioborne.wallatest.repository;

import com.sergioborne.wallatest.domain.model.Comic;
import com.sergioborne.wallatest.domain.repository.SimpleComicRepository;
import java.util.ArrayList;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


/**
 * Created by sergio.cores on 22/08/2016.
 */
@RunWith(MockitoJUnitRunner.class) public class SimpleComicRepositoryTest {

  SimpleComicRepository simpleComicRepository;

  @Before public void setup() {
    simpleComicRepository = new SimpleComicRepository();
  }

  @Test public void addComicToRepository() {
    Comic mockedComic = mock(Comic.class);
    when(mockedComic.getId()).thenReturn(1);

    simpleComicRepository.add(mockedComic);

    Assert.assertFalse(simpleComicRepository.isEmpty());
  }

  @Test public void deleteComicFromRepository() {
    Comic mockedComic = mock(Comic.class);
    when(mockedComic.getId()).thenReturn(1);

    simpleComicRepository.add(mockedComic);
    simpleComicRepository.erase();

    Assert.assertTrue(simpleComicRepository.isEmpty());
  }

  @Test public void findByIdShouldReturnCorrectComic() {
    int id = 1;
    Comic comic = new Comic();
    comic.setDescription("asd");
    comic.setThumbnailUrl("http");
    comic.setTitle("title");
    comic.setYear("2016");
    comic.setId(id);
    comic.setImagesUrls(new ArrayList<>());

    simpleComicRepository.add(comic);

    Assert.assertNotNull(simpleComicRepository.findById(1));
    assertThat(comic).isEqualToComparingFieldByField(simpleComicRepository.findById(id));
  }


}
