package net.negacz.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.logging.LoggingHandler;

import java.util.concurrent.CompletableFuture;

import static io.netty.buffer.Unpooled.EMPTY_BUFFER;
import static io.netty.handler.codec.http.HttpMethod.GET;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import static io.netty.handler.logging.LogLevel.INFO;

class CompletableFutureClient {

  static CompletableFuture<HttpResponse> request(HttpRequest request) throws InterruptedException {
    var workerGroup = new NioEventLoopGroup();
    var response = new CompletableFuture<HttpResponse>();
    var bootstrap = new Bootstrap();
    bootstrap.group(workerGroup)
      .channel(NioSocketChannel.class)
      .handler(new ChannelInitializer<SocketChannel>() {
        @Override
        public void initChannel(SocketChannel ch) {
          ch.pipeline()
            .addLast(
              new LoggingHandler(INFO),
              new HttpClientCodec(),
              new SimpleChannelInboundHandler<HttpResponse>() {
                @Override
                protected void channelRead0(ChannelHandlerContext ctx, HttpResponse msg) {
                  response.complete(msg);
                }
              }
            );
        }
      });

    var channel = bootstrap.connect("example.org", 80).sync().channel();
    channel.writeAndFlush(request);
    return response;
  }

  public static void main(String[] args) throws InterruptedException {
    request(new DefaultFullHttpRequest(HTTP_1_1, GET, "/", EMPTY_BUFFER))
      .thenAccept(response -> {
        System.out.println(Thread.currentThread().getName());
        System.out.println(response);
      });
  }
}
