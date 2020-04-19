package net.negacz.netty.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpResponse;

class NettyClientHandler extends SimpleChannelInboundHandler<HttpObject> {

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) {
    if (msg instanceof HttpResponse) {
      System.out.println(msg);
    }
  }
}
