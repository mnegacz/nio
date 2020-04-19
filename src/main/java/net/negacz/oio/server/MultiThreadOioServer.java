package net.negacz.oio.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newFixedThreadPool;
import static net.negacz.OnShutdownCloser.autoCloseOnShutdown;
import static net.negacz.oio.server.SimpleSocketHandler.handle;

class MultiThreadOioServer {

  public static void main(String[] args) throws IOException {
    ServerSocket serverSocket = autoCloseOnShutdown(new ServerSocket(8081));
    ExecutorService threadPool = newFixedThreadPool(5);
    System.out.println("* ServerSocket created");
    while (true) {
      Socket socket = serverSocket.accept();
      System.out.println("* Socket accepted");
      threadPool.submit(() -> handle(socket));
    }
  }
}
