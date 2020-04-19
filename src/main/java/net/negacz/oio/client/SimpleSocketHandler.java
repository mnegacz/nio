package net.negacz.oio.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

class SimpleSocketHandler {

  static void handle(Socket socket) {
    try {
      System.out.println("* Handling socket");
      PrintWriter writer = sendRequest(socket);
      BufferedReader reader = printResponse(socket);
      writer.close();
      reader.close();
      socket.close();
      System.out.println("* Socket closed");
      System.out.println("* Socket handled");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  static PrintWriter sendRequest(Socket socket) throws IOException {
    System.out.println("* Sending request");
    PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
    writer.println("GET / HTTP/1.0");
    writer.println("");
    writer.flush();
    System.out.println("* Request sent");
    return writer;
  }

  static BufferedReader printResponse(Socket socket) throws IOException {
    System.out.println("* Printing response");
    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    while (true) {
      String line = reader.readLine();
      if (line == null || line.isEmpty()) {
        System.out.println("* Response printed");
        return reader;
      }
      System.out.println(line);
    }
  }
}
