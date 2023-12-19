package org.rsm.service;

import java.io.InputStream;
import java.util.Random;

public class Upload {
  private Upload() {
    throw new IllegalStateException("Upload class");
  }

  static Random rand = new Random();

  public static void doUpload(String key, InputStream data, int size) throws RuntimeException {
    System.out.println(key);
    System.out.println(data.toString());
    System.out.println(size);
    
    var r = rand.nextInt(12);
    System.out.println("Upoad random value:" + r);

    // Throws exception in 1 in 11 uploads on average
    if (r > 0 && r % 11 == 0) {
      throw new RuntimeException ("Upload unexpected exception occurred.");
    }
  }

}
