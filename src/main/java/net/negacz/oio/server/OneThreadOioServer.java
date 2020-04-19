package net.negacz.oio.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static net.negacz.OnShutdownCloser.autoCloseOnShutdown;
import static net.negacz.oio.server.SimpleSocketHandler.handle;

class OneThreadOioServer {

  public static void main(String[] args) throws IOException {
    ServerSocket serverSocket = autoCloseOnShutdown(new ServerSocket(8080));
    System.out.println("* ServerSocket created");
    while (true) {
      Socket socket = serverSocket.accept();
      System.out.println("* Socket accepted");
      handle(socket);
      socket.close();
      System.out.println("* Socket closed");
    }
  }
}
