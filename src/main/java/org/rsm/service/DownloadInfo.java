package org.rsm.service;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class DownloadInfo {

  @Getter
  public int size; // bytes
  @Getter
  public String originalFileName;
  @Getter
  public String fileKey;
  @Getter
  public String downloadURL;
  
}
