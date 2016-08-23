package com.sergioborne.wallatest.network;

import com.sergioborne.wallatest.data.model.ComicsResponse;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface MarvelApiInterface {

  @GET("/v1/public/characters/{characterId}/comics") Observable<ComicsResponse> getComics(
      @Path("characterId") int characterId, @Query("limit") int limit);

  @GET("/v1/public/characters/{characterId}/comics") Observable<ComicsResponse> getComics(
      @Path("characterId") int characterId, @Query("limit") int limit, @Query("offset") int offset);
}
