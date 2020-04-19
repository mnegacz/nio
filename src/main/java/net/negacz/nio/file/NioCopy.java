package net.negacz.nio.file;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

class NioCopy {

  public static void main(String[] args) throws IOException {
    RandomAccessFile original = new RandomAccessFile("README.md", "rw");
    FileChannel originalChannel = original.getChannel();
    RandomAccessFile copy = new RandomAccessFile("README.md.nio", "rw");
    FileChannel copyChannel = copy.getChannel();
    originalChannel.transferTo(0, originalChannel.size(), copyChannel);
  }
}
