package com.sergioborne.wallatest.utils;

import java.util.Random;

public final class RandomUtils {

  public static int getRandomNumber(int min, int max) {
    Random random = new Random();
    return random.nextInt(max - min + 1) + min;
  }
}
