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
 * Time Server
 */
public class TimeServer {

    private int port;

    public TimeServer(int port) {
        this.port = port;
    }

    public void run() {
        System.out.println("Time Server Started at :18000...");
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
                                            new TimeServerHandler()
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
            System.out.println("Time Server Stopped...");
        }
    }

    /* usage:
    public static void main(String[] args) throws Exception {
        int port = 18000;
        new TimeServer(port).run();
    }
    */

}


class TimeServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf in = (ByteBuf) msg;
        System.out.println("=>" + in.toString(io.netty.util.CharsetUtil.US_ASCII));

        // return time
        ByteBuf b = ctx.alloc().buffer(4);
        b.writeInt( (int)(System.currentTimeMillis() / 1000L + 2208988800L) );
        ctx.write(b);
        // ctx.close();
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




