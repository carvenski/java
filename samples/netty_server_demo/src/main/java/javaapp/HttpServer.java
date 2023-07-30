package javaapp;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

import io.netty.buffer.ByteBuf;

import io.netty.bootstrap.ServerBootstrap;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Http Server
 */
public class HttpServer {

    private int port;

    public HttpServer(int port) {
        this.port = port;
    }

    public void run() {
        System.out.println("Http Server Started at :18000...");
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                                     @Override
                                     public void initChannel(SocketChannel ch) throws Exception {
                                         ch.pipeline().addLast(
                                            new HttpServerHandler()
                                         );
                                     }
                    })
                .option(ChannelOption.SO_BACKLOG, 10) //number of connections queued
                .childOption(ChannelOption.SO_KEEPALIVE, true); // socket keepalive

            // Bind and start to accept incoming connections.
            ChannelFuture f = b.bind(port).sync();

            // Wait until the server socket is closed.
            f.channel().closeFuture().sync();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
            System.out.println("Http Server Stopped...");
        }
    }

    /* usage:
    public static void main(String[] args) throws Exception {
        int port = 18000;
        new HttpServer(port).run();
    }
    */

}


class HttpServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf in = (ByteBuf) msg;
        if (in.isReadable()) {
            System.out.println("=> got data str:");
            System.out.println(in.toString(io.netty.util.CharsetUtil.US_ASCII));

            String resp = "HTTP/1.0 200 OK\n" +
                 "Date: fuck\n" +
                 "Server: fuck\n" +
                 "Last-Modified: fuck\n" +
                 "Content-Length: 25\n" +
                 "Content-Type: text/plain\n" +
                 "Connection: Closed\n" +
                 "\n" +
                 "...hello...http...fuck...";
            ByteBuf b = ctx.alloc().buffer(resp.length()*2);
            b.writeBytes(resp.getBytes());
            ctx.write(b);
            ctx.flush();
            ctx.close(); // close connection
       }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        System.out.println("Exception happen:");
        cause.printStackTrace();
        ctx.close();
    }
}




