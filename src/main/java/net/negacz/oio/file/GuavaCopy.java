package net.negacz.oio.file;

import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;

class GuavaCopy {
  public static void main(String[] args) throws IOException {
    File original = new File("README.md");
    File copy = new File("README.md.guava");
    Files.copy(original, copy);
  }
}
