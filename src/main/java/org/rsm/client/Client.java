package org.rsm.client;

import org.rsm.service.*;
import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import java.util.HashSet;
import java.util.List;

public class Client {
  public static void main(String[] args) throws Exception {
    var dir = new File("./download-data");
    var totalSize = 0;
    var totalSuccess = 0;
    var totalFail = 0;
    long totalTime = 0;
    
    long start = System.currentTimeMillis();
    var results = Download.getDownloadInfos(1L);
    var files = dir.listFiles();
    System.out.println("Downloaded results: " + results.size());

    var disallowedExts = List.of(".cmd", ".com", ".dll", ".dmg", ".exe", ".iso", ".jar", ".js");
    var fileNames = new HashSet<String>();

    // process results
    try {
      for (File f : files) {
        if (totalSize > 100 * 1024 * 1024) {
          throw new Exception("Size limit exceeded.");
        }

        var filename = f.getName();
        System.out.println(filename);
        var extension = filename.substring(filename.lastIndexOf("."));
        System.out.println(extension);
        if (disallowedExts.contains(extension)) {
          throw new Exception("Illegal extension detected.");
        }
        System.out.println("Processing file name: " + filename);
        if (fileNames.contains(filename)) {
          throw new Exception("Duplicate file name detected.");
        }

        // update current stats
        fileNames.add(filename);
        System.out.println("Processing single file size:" + f.length());
        totalSize += f.length();
        System.out.println("Processing total size: " + totalSize);

        try {
          // upload with possible exception
          InputStream is = new FileInputStream(f);
          Upload.doUpload("ID_0001", is, 0);
          totalSuccess += 1;
        } catch (RuntimeException re) {
          totalFail += 1;
        }
      }
    } finally {
      var finish = System.currentTimeMillis();
      totalTime = finish - start;

      System.out.println("--------------------------");
      System.out.println("FINAL REPORT:");
      System.out.println("Total size: " + totalSize);
      System.out.println("Total elapsed time: " + totalTime + " ms");
      System.out.println("Total successful files: " + totalSuccess);
      System.out.println("Total failed files: " + totalFail);
    }
  }
}
