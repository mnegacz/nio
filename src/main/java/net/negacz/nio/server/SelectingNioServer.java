package net.negacz.nio.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

import static java.nio.channels.SelectionKey.OP_ACCEPT;
import static java.nio.channels.SelectionKey.OP_READ;
import static java.nio.channels.SelectionKey.OP_WRITE;
import static java.nio.charset.StandardCharsets.UTF_8;
import static net.negacz.OnShutdownCloser.autoCloseOnShutdown;

class SelectingNioServer {

  public static void main(String[] args) throws IOException {
    ServerSocketChannel serverSocketChannel = autoCloseOnShutdown(ServerSocketChannel.open());
    serverSocketChannel.socket().bind(new InetSocketAddress(8082));
    serverSocketChannel.configureBlocking(false);
    System.out.println("* ServerSocket created");

    Selector selector = Selector.open();
    serverSocketChannel.register(selector, OP_ACCEPT);
    while (true) {
      if (selector.select() > 0) {
        Set<SelectionKey> selectedKeys = selector.selectedKeys();
        Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
        while (keyIterator.hasNext()) {
          SelectionKey key = keyIterator.next();
          if (key.isValid()) {
            if (key.isAcceptable()) {
              handleAccept(serverSocketChannel, selector);
            } else if (key.isReadable()) {
              handleRequest(key);
            } else if (key.isWritable()) {
              handleResponse(key);
            }
          }
          keyIterator.remove();
        }
      }
    }
  }

  private static void handleAccept(ServerSocketChannel serverSocketChannel, Selector acceptSelector) throws IOException {
    SocketChannel socketChannel = serverSocketChannel.accept();
    System.out.println("* Socket accepted");
    socketChannel.configureBlocking(false);
    socketChannel.register(acceptSelector, OP_READ);
  }

  private static void handleRequest(SelectionKey key) throws IOException {
    System.out.println("* Printing request");
    SocketChannel socketChannel = (SocketChannel) key.channel();
    ByteBuffer buffer = ByteBuffer.allocate(48);
    buffer.clear();
    socketChannel.read(buffer);
    buffer.flip();
    String value = UTF_8.decode(buffer).toString();
    System.out.println(value);
    System.out.println("* Request printed");
    key.interestOps(OP_WRITE);
  }

  private static void handleResponse(SelectionKey key) throws IOException {
    System.out.println("* Sending response");
    SocketChannel socketChannel = (SocketChannel) key.channel();
    ByteBuffer buffer = ByteBuffer.allocate(48);
    buffer.clear();
    buffer.put("HTTP/1.1 200 OK".getBytes(UTF_8));
    socketChannel.write(buffer);
    System.out.println("* Response sent");
    socketChannel.close();
  }
}
