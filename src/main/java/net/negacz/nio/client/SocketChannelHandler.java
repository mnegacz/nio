package net.negacz.nio.client;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import static java.nio.charset.StandardCharsets.UTF_8;

class SocketChannelHandler {

  static void handle(SocketChannel socketChannel) throws IOException {
    System.out.println("* Handling socket");
    sendRequest(socketChannel);
    printResponse(socketChannel);
    socketChannel.close();
    System.out.println("* Socket closed");
    System.out.println("* Socket handled");
  }

  static void sendRequest(SocketChannel socketChannel) throws IOException {
    System.out.println("* Sending request");
    ByteBuffer buffer = ByteBuffer.allocate(48);
    buffer.clear();
    buffer.put("GET / HTTP/1.0\r\n".getBytes(UTF_8));
    buffer.put("\r\n".getBytes(UTF_8));

    buffer.flip();
    while (buffer.hasRemaining()) {
      System.out.print("*");
      socketChannel.write(buffer);
    }
    System.out.println("* Request sent");
  }

  static void printResponse(SocketChannel socketChannel) throws IOException {
    System.out.println("* Printing response");

    ByteBuffer buffer = ByteBuffer.allocate(48);
    buffer.clear();
    int read;
    while ((read = socketChannel.read(buffer)) != -1) {
      System.out.print("*");
      if (read > 0) {
        System.out.println();
        buffer.flip();
        String value = UTF_8.decode(buffer).toString();
        System.out.println(value);
      }
      buffer.clear();
    }
    System.out.println("* Response printed");
  }
}
