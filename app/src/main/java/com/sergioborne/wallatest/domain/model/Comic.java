package com.sergioborne.wallatest.domain.model;

import java.io.Serializable;
import java.util.List;

public class Comic implements Serializable{

  private int id;
  private String title;
  private String description;
  private String pages;
  private String thumbnailUrl;
  private String year;
  private List<String> imagesUrls;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getThumbnailUrl() {
    return thumbnailUrl;
  }

  public void setThumbnailUrl(String thumbnailUrl) {
    this.thumbnailUrl = thumbnailUrl;
  }

  public List<String> getImagesUrls() {
    return imagesUrls;
  }

  public void setImagesUrls(List<String> imagesUrls) {
    this.imagesUrls = imagesUrls;
  }

  public String getYear() {
    return year;
  }

  public void setYear(String year) {
    this.year = year;
  }
}
