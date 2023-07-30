package javaapp;

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
 *The most simplistic protocol in the world is not 'Hello, World!' but DISCARD.
 *It's a protocol that discards any received data without any response.
 */
public class DiscardServer {

    private int port;

    public DiscardServer(int port) {
        this.port = port;
    }

    public void run() {
        System.out.println("Discard Server Started at :18000...");
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
                                            new DiscardServerHandler()
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
            System.out.println("Discard Server Stopped...");
        }
    }

    /* usage:
    public static void main(String[] args) throws Exception {
        int port = 18000;
        new DiscardServer(port).run();
    }
    */

}


class DiscardServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf in = (ByteBuf) msg;
        if (in.isReadable()) {
            //            System.out.println("=> got data byte:");
            //            System.out.print(in.readByte());
            //            System.out.println("=> got data char:");
            //            System.out.print((char) in.readByte());
            System.out.println("=> got data str:");
            System.out.println(in.toString(io.netty.util.CharsetUtil.US_ASCII));
        }

        // Discard the received data silently.
        in.release();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close(); // ctx is socket...
    }
}




