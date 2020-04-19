package net.negacz;

import java.io.IOException;
import java.net.ServerSocket;

import static java.lang.Runtime.getRuntime;

public class OnShutdownCloser {

  public static ServerSocket autoCloseOnShutdown(ServerSocket serverSocket) {
    getRuntime().addShutdownHook(new Thread(() -> {
      try {
        if (!serverSocket.isClosed()) {
          serverSocket.close();
        }
        System.out.println("* ServerSocket closed");
      } catch (IOException e) {
        e.printStackTrace();
      }
    }));
    return serverSocket;
  }

}
