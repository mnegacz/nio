package net.negacz.netty.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.LastHttpContent;

import static io.netty.buffer.Unpooled.copiedBuffer;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import static io.netty.util.CharsetUtil.UTF_8;

class NettyServerHandler extends SimpleChannelInboundHandler<HttpObject> {

  @Override
  public void channelReadComplete(ChannelHandlerContext ctx) {
    ctx.flush();
  }

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) {
    if (msg instanceof HttpRequest) {
      System.out.println(msg);
    }
    if (msg instanceof LastHttpContent) {
      ByteBuf content = copiedBuffer("Hello World!", UTF_8);
      FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, content);
      response.headers().set(CONTENT_TYPE, "text/plain");
      response.headers().set(CONTENT_LENGTH, content.readableBytes());
      ctx.writeAndFlush(response);
      ctx.close();
    }
  }
}
