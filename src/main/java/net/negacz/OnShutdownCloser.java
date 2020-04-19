package net.negacz;

import java.io.IOException;
import java.net.ServerSocket;
import java.nio.channels.ServerSocketChannel;
import java.util.concurrent.ExecutorService;

import static java.lang.Runtime.getRuntime;

public class OnShutdownCloser {

  public static ServerSocketChannel autoCloseOnShutdown(ServerSocketChannel serverSocketChannel) {
    onShutdown(() -> {
      try {
        serverSocketChannel.close();
        System.out.println("* ServerSocketChannel closed");
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
    return serverSocketChannel;
  }

  public static ExecutorService autoCloseOnShutdown(ExecutorService executorService) {
    onShutdown(() -> executorService.shutdownNow());
    return executorService;
  }

  public static ServerSocket autoCloseOnShutdown(ServerSocket serverSocket) {
    onShutdown(() -> {
      try {
        if (!serverSocket.isClosed()) {
          serverSocket.close();
        }
        System.out.println("* ServerSocket closed");
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
    return serverSocket;
  }

  private static void onShutdown(Runnable runnable) {
    getRuntime().addShutdownHook(new Thread(runnable));
  }

}
