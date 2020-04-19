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

  static void handle(Socket socket) throws IOException {
    sleep();
    BufferedReader reader = printRequest(socket);
    PrintWriter writer = respondWithHelloWorld(socket);

    reader.close();
    writer.close();
  }

  static void sleep() {
    try {
      SECONDS.sleep(5);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  static BufferedReader printRequest(Socket socket) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    while (true) {
      String line = reader.readLine();
      if (line == null || line.isEmpty()) {
        return reader;
      }
      //System.out.println(line);
    }
  }

  static PrintWriter respondWithHelloWorld(Socket socket) throws IOException {
    PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
    writer.println("HTTP/1.1 200 OK");
    writer.println("Content-Length: 13");
    writer.println("Content-Type: text/plain; charset=utf-8");
    writer.println("");
    writer.println("Hello World!");
    writer.flush();
    return writer;
  }
}
