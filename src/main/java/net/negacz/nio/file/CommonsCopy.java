package net.negacz.nio.file;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

class CommonsCopy {

  public static void main(String[] args) throws IOException {
    File original = new File("README.md");
    File copy = new File("README.md.commons");
    FileUtils.copyFile(original, copy);
  }
}
