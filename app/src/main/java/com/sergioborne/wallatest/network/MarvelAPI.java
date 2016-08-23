package com.sergioborne.wallatest.network;

import android.support.annotation.NonNull;
import android.util.Log;
import com.sergioborne.wallatest.data.model.ComicsResponse;
import com.sergioborne.wallatest.domain.mapper.MarvelComicsMapper;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

public class MarvelAPI {
  private static final String TAG = MarvelAPI.class.getSimpleName();
  private final String BASE_PATH_API;
  public Retrofit retrofit;
  private MarvelApiInterface apiService;

  public static final String PARAM_KEY = "apikey";
  public static final String PARAM_TIMESTAMP = "ts";
  public static final String PARAM_HASH = "hash";
  private static final int SIGNUM = 1;
  private static final int BYTES = 1;
  private String publicKey;
  private String privateKey;
  private MarvelComicsMapper marvelComicsMapper;

  public MarvelAPI(@NonNull String basePath, @NonNull String publicKey,
      @NonNull String privateKey) {
    this.BASE_PATH_API = basePath;
    this.publicKey = publicKey;
    this.privateKey = privateKey;
    marvelComicsMapper = new MarvelComicsMapper();
    construct();
  }

  public MarvelApiInterface getApiService() {
    return apiService;
  }

  private void construct() {
    OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();

    Interceptor requestInterceptor = chain -> {

      Request request = chain.request().newBuilder().build();

      long timeStamp = System.currentTimeMillis();
      HttpUrl.Builder urlBuilder = request.url().newBuilder();

      urlBuilder.addEncodedQueryParameter(PARAM_TIMESTAMP, String.valueOf(timeStamp));
      urlBuilder.addEncodedQueryParameter(PARAM_KEY, publicKey);
      String hash = generateMarvelHash(timeStamp, privateKey, publicKey);
      urlBuilder.addEncodedQueryParameter(PARAM_HASH, hash);
      request = request.newBuilder().url(urlBuilder.build()).build();

      return chain.proceed(request);
    };

    clientBuilder.addInterceptor(requestInterceptor);
    OkHttpClient client = clientBuilder.build();

    retrofit = new Retrofit.Builder().baseUrl(BASE_PATH_API)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .client(client)
        .build();

    apiService = retrofit.create(MarvelApiInterface.class);
  }

  public Observable<ComicsResponse> getComics(int characterId, int limit) {
    return apiService.getComics(characterId, limit);
  }

  public Observable<ComicsResponse> getComics(int characterId, int limit, int offset) {
    Log.d(TAG, "getComics: "+characterId+" , "+limit+" , "+offset);
    return apiService.getComics(characterId, limit, offset);
  }

  private String generateMarvelHash(long timeStamp, String privateKey, String publicKey) {
    String marvelHash = timeStamp + privateKey + publicKey;
    return md5(marvelHash);
  }

  public static final String md5(final String s) {
    final String MD5 = "MD5";
    try {
      // Create MD5 Hash
      MessageDigest digest = java.security.MessageDigest.getInstance(MD5);
      digest.update(s.getBytes());
      byte messageDigest[] = digest.digest();

      // Create Hex String
      StringBuilder hexString = new StringBuilder();
      for (byte aMessageDigest : messageDigest) {
        String h = Integer.toHexString(0xFF & aMessageDigest);
        while (h.length() < 2) {
          h = "0" + h;
        }
        hexString.append(h);
      }
      return hexString.toString();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return "";
  }
}
