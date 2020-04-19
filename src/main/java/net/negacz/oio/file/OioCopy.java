package net.negacz.oio.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

class OioCopy {

  public static void main(String[] args) throws IOException {
    File original = new File("README.md");
    File copy = new File("README.md.oio");
    try (
      InputStream in = new BufferedInputStream(new FileInputStream(original));
      OutputStream out = new BufferedOutputStream(new FileOutputStream(copy))) {

      byte[] buffer = new byte[1024];
      int lengthRead;
      while ((lengthRead = in.read(buffer)) > 0) {
        out.write(buffer, 0, lengthRead);
        out.flush();
      }
    }
  }
}
