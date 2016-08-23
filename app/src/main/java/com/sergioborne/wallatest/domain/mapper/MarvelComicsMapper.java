package com.sergioborne.wallatest.domain.mapper;

import android.util.Log;
import com.annimon.stream.Stream;
import com.sergioborne.wallatest.app.exception.DataNotFoundException;
import com.sergioborne.wallatest.data.model.ComicsResponse;
import com.sergioborne.wallatest.data.model.Date;
import com.sergioborne.wallatest.data.model.Image;
import com.sergioborne.wallatest.data.model.Result;
import com.sergioborne.wallatest.domain.model.Comic;
import java.util.ArrayList;
import java.util.List;

public class MarvelComicsMapper {

  private static final String TAG = MarvelComicsMapper.class.getSimpleName();

  public MarvelComicsMapper() {
  }

  public List<Comic> map(ComicsResponse comicsResponse) {
    List<Comic> comics = new ArrayList<>();
    for (Result result : comicsResponse.getData().getResults()) {
      Comic mappedComic = map(result);
      comics.add(mappedComic);
    }
    return comics;
  }

  public Comic map(Result result) {
    Comic comic = new Comic();
    try {
      comic.setTitle(obtainTitle(result));
      comic.setThumbnailUrl(obtainThumbnail(result));
      comic.setDescription(obtainDescription(result));
      comic.setId(obtainId(result));
      comic.setYear(obtainYear(result));
      comic.setImagesUrls(obatainImages(result));
    } catch (DataNotFoundException e) {
      Log.w(TAG, "Some data not found result");
    }
    return comic;
  }

  private String obtainTitle(Result result) throws DataNotFoundException {
    if (result.getTitle() != null) {
      return result.getTitle();
    } else {
      throw new DataNotFoundException();
    }
  }

  private String obtainThumbnail(Result result) throws DataNotFoundException {
    return parseImage(result.getThumbnail());
  }

  private String obtainDescription(Result result) throws DataNotFoundException {
    return result.getDescription();
  }

  private int obtainId(Result result) {
    return Integer.valueOf(result.getId());
  }

  private String obtainYear(Result result) throws DataNotFoundException {
    for (Date date : result.getDates()) {
      if ("onsaleDate".equals(date.getType())) {
        return date.getDate().substring(0, 4);
      }
    }
    throw new DataNotFoundException();
  }

  private List<String> obatainImages(Result result) {
    List<String> images = new ArrayList<>();
    Stream.of(result.getImages()).forEach(image -> images.add(parseImage(image)));
    return images;
  }

  private String parseImage(Image image) {
    return image.getPath() + "." + image.getExtension();
  }
}
