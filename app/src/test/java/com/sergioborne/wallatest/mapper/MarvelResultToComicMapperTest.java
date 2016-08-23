package com.sergioborne.wallatest.mapper;

import com.sergioborne.wallatest.data.model.ComicsResponse;
import com.sergioborne.wallatest.data.model.Data;
import com.sergioborne.wallatest.data.model.Image;
import com.sergioborne.wallatest.data.model.Result;
import com.sergioborne.wallatest.data.model.Thumbnail;
import com.sergioborne.wallatest.domain.mapper.MarvelComicsMapper;
import com.sergioborne.wallatest.domain.model.Comic;
import java.util.ArrayList;
import java.util.List;
import junit.framework.Assert;
import org.junit.Test;

public class MarvelResultToComicMapperTest {

  @Test
  public void mapResultToComic() {
    MarvelComicsMapper marvelResultToComicMapper = new MarvelComicsMapper();

    Result result = new Result();

    Thumbnail thumbnail = new Thumbnail();
    thumbnail.setPath("http://www.fake.image");
    thumbnail.setExtension("jpg");

    List<Image> listMocked = new ArrayList<Image>();

    result.setTitle("Title");
    result.setThumbnail(thumbnail);
    result.setDescription("description");
    result.setId("1");
    result.setImages(listMocked);

    Comic comic = marvelResultToComicMapper.map(result);

    Assert.assertEquals(comic.getId(), 1);
    Assert.assertEquals(comic.getTitle(), "Title");
    Assert.assertEquals(comic.getDescription(), "description");
    Assert.assertEquals(comic.getThumbnailUrl(), "http://www.fake.image.jpg");
  }

  @Test
  public void mapComicResponseToComicList() {
    Result result = new Result();

    Thumbnail thumbnail = new Thumbnail();
    thumbnail.setPath("http://www.fake.image");
    thumbnail.setExtension("jpg");

    List<Image> listMocked = new ArrayList<Image>();

    result.setTitle("Title");
    result.setThumbnail(thumbnail);
    result.setDescription("description");
    result.setId("1");
    result.setImages(listMocked);

    List<Result> stubList = new ArrayList<>();
    stubList.add(result);

    Data data = new Data();
    data.setCount("1");
    data.setResults(stubList);

    ComicsResponse comicsResponse = new ComicsResponse();
    comicsResponse.setData(data);

    MarvelComicsMapper marvelComicsMapper = new MarvelComicsMapper();
    List<Comic> comics = marvelComicsMapper.map(comicsResponse);

    Assert.assertTrue(comics.size() > 0);
  }
}
