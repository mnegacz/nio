package net.negacz.oio.client;

import java.io.IOException;
import java.net.Socket;

import static net.negacz.oio.client.SimpleSocketHandler.handle;

class OioClient {

  public static void main(String[] args) throws IOException {
    Socket socket = new Socket("localhost", 8080);
    handle(socket);
  }
}
