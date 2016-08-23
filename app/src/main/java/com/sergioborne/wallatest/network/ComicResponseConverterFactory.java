package com.sergioborne.wallatest.network;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ComicResponseConverterFactory extends Converter.Factory{
  private GsonConverterFactory factory;

  public ComicResponseConverterFactory(GsonConverterFactory factory) {
    this.factory = factory;
  }

  @Override
  public Converter<ResponseBody, ?> responseBodyConverter(final Type type,
      Annotation[] annotations, Retrofit retrofit) {
    // e.g. WrappedResponse<Person>
    Type wrappedType = new ParameterizedType() {
      @Override
      public Type[] getActualTypeArguments() {
        // -> WrappedResponse<type>
        return new Type[] {type};
      }

      @Override
      public Type getOwnerType() {
        return null;
      }

      @Override
      public Type getRawType() {
        return Response.class;
      }
    };
    Converter<ResponseBody, ?> gsonConverter = factory
        .responseBodyConverter(wrappedType, annotations, retrofit);
    return new ResponseBodyConverter(gsonConverter);
  }

  public class ResponseBodyConverter<T>
      implements Converter<ResponseBody, T> {
    private Converter<ResponseBody, Response<T>> converter;

    public ResponseBodyConverter(Converter<ResponseBody,
        Response<T>> converter) {
      this.converter = converter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
      Response<T> response = converter.convert(value);
      if (response.code() == 0) {
        return response.body();
      }
      // RxJava will call onError with this exception
      throw new Error(response.message());
    }
  }
}
