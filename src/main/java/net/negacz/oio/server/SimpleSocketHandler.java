package net.negacz.oio.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import static java.util.concurrent.TimeUnit.SECONDS;

class SimpleSocketHandler {

  static void handle(Socket socket) {
    try {
      System.out.println("* Handling socket");
      BufferedReader reader = printRequest(socket);
      sleep();
      PrintWriter writer = sendResponse(socket);

      reader.close();
      writer.close();
      socket.close();
      System.out.println("* Socket closed");
      System.out.println("* Socket handled");
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
  }

  static void sleep() throws InterruptedException {
    SECONDS.sleep(5);
  }

  static BufferedReader printRequest(Socket socket) throws IOException {
    System.out.println("* Printing request");
    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    while (true) {
      String line = reader.readLine();
      if (line == null || line.isEmpty()) {
        System.out.println("* Request printed");
        return reader;
      }
      System.out.println(line);
    }
  }

  static PrintWriter sendResponse(Socket socket) throws IOException {
    System.out.println("* Sending response");
    PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
    writer.println("HTTP/1.1 200 OK");
    writer.println("Content-Length: 13");
    writer.println("Content-Type: text/plain; charset=utf-8");
    writer.println("");
    writer.println("Hello World!");
    writer.flush();
    System.out.println("* Response sent");
    return writer;
  }
}
