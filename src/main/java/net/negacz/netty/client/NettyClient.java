package net.negacz.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.HttpClientCodec;

import static io.netty.buffer.Unpooled.EMPTY_BUFFER;
import static io.netty.handler.codec.http.HttpMethod.GET;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

class NettyClient {

  public static void main(String[] args) throws InterruptedException {
    var workerGroup = new NioEventLoopGroup();

    try {
      var bootstrap = new Bootstrap();
      bootstrap.group(workerGroup)
        .channel(NioSocketChannel.class)
        .handler(new ChannelInitializer<SocketChannel>() {
          @Override
          public void initChannel(SocketChannel ch) {
            ch.pipeline()
              .addLast(new HttpClientCodec())
              .addLast(new NettyClientHandler());
          }
        });

      var channel = bootstrap.connect("localhost", 8080).sync().channel();

      var request = new DefaultFullHttpRequest(HTTP_1_1, GET, "/", EMPTY_BUFFER);
      channel.writeAndFlush(request);
      channel.closeFuture().sync();
    } finally {
      workerGroup.shutdownGracefully();
    }
  }
}
