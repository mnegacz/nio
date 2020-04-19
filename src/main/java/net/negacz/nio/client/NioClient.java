package net.negacz.nio.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

import static net.negacz.nio.client.SocketChannelHandler.handle;

class NioClient {

  public static void main(String[] args) throws IOException {
    SocketChannel socketChannel = SocketChannel.open();
    socketChannel.configureBlocking(false);
    boolean connected = socketChannel.connect(new InetSocketAddress("localhost", 8080));
    System.out.println("* Connected " + connected);
    while (!socketChannel.finishConnect()) {
      System.out.print("*");
    }
    System.out.println("* Connected " + socketChannel.isConnected());
    handle(socketChannel);
  }
}
