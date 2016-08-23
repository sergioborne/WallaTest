package com.sergioborne.wallatest.utils;

import android.util.Log;

import com.bluelinelabs.logansquare.typeconverters.StringBasedTypeConverter;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONObjectConverter extends StringBasedTypeConverter<JSONObject> {
  private static final String TAG = JSONObjectConverter.class.getSimpleName();

  @Override public JSONObject getFromString(String string) {
    JSONObject jsonObject;
    try {
      jsonObject = new JSONObject(string);
    } catch (JSONException e) {
      Log.e(TAG, e.getMessage());
      jsonObject = new JSONObject();
    }

    return jsonObject;
  }

  @Override public String convertToString(JSONObject object) {
    return object != null ? object.toString() : null;
  }
}
