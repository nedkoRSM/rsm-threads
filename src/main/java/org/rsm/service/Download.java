package org.rsm.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Download {
  private Download() {
    throw new IllegalStateException("Download class");
  }

  static Random rand = new Random();
  // Exceeds size limit;
  // static int size = 2 * 1024 * 1024;

  // Meets the size limit:
  static int size = 10 * 1024;

  static int count = 100;

  static File dir = new File("./download-data");
  static String ext = ".txt";

  public static List<DownloadInfo> getDownloadInfos(long packageId) {

    System.out.println("Download package ID: " + packageId); // not used
    System.out.println("Download size: " + size);

    var bytes = new byte[size];
    BufferedOutputStream bos = null;
    FileOutputStream fos = null;
    String name;
    List<DownloadInfo> results = new ArrayList<>();

    try {
      for (File file : dir.listFiles()) {
        file.delete();
      }

      for (int i = 0; i < count; i++) {
        // Only allowed extensions
        String[] exts = { ".txt", ".tf", ".css", ".pdf", ".java", ".md", ".py",
            ".doc" };
        // Contains 1 disallowed extension
        // String[] exts = { ".txt", ".tf", ".css", ".pdf", ".java", ".md", ".py",
        // ".doc", ".cmd" };

        // Contains many disallowed extensions
        // String[] exts = { ".txt", ".tf", ".css", ".pdf", ".java", ".md", ".py",
        // ".doc", ".cmd", ".com", ".dll", ".dmg",
        // ".exe", ".iso",
        // ".jar", ".js" };

        var ext = exts[rand.nextInt(exts.length)];
        name = String.format("%s%s", System.currentTimeMillis(), rand.nextInt(100000) + ext);
        var file = new File(dir, name);

        fos = new FileOutputStream(file);
        bos = new BufferedOutputStream(fos);

        rand.nextBytes(bytes);
        bos.write(bytes);

        bos.flush();
        bos.close();
        fos.flush();
        fos.close();

        // size is set above // bytes
        // name is set above
        var key = "key" + name;
        var url = "http://some_url.com/" + name;
        results.add(new DownloadInfo(size, name, key, url));
      }

    } catch (FileNotFoundException fnfe) {
      System.out.println("File not found" + fnfe);
    } catch (IOException ioe) {
      System.out.println("Download error: while writing to file" + ioe);
    } finally {
      try {
        if (bos != null) {
          bos.flush();
          bos.close();
        }
        if (fos != null) {
          fos.flush();
          fos.close();
        }
      } catch (Exception e) {
        System.out.println("Download error: while closing streams: " + e.getMessage());
      }
    }
    return results;
  }
}
