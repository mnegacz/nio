package net.negacz.nio.client;

import java.io.IOException;
import java.nio.channels.SocketChannel;

class SelectingNioClient {

  public static void main(String[] args) throws IOException {
    SocketChannel socketChannel = SocketChannel.open();
    socketChannel.configureBlocking(false);

  }
}
