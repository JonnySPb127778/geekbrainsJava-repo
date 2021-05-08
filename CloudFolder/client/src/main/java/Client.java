import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringEncoder;
import message.Message;
import handler.JsonListDecoder;
import handler.JsonListEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {
    private static final ExecutorService THREAD_POOL = Executors.newFixedThreadPool(5);

    public static void main(String[] args) throws InterruptedException {
        try {
            new Client().start();
        } finally {
            THREAD_POOL.shutdown();
        }
    }

    public void start() throws InterruptedException {

        int cntr = 0;
//        for (int i = 0; i < 5; i++) {
//            THREAD_POOL.execute(() -> {
        final NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) {
                    ch.pipeline().addLast(
                            new LengthFieldBasedFrameDecoder(256 * 1024, 0, 3, 0, 3),
                            new LengthFieldPrepender(3),
                            new StringEncoder(),
                            new JsonListDecoder(),
                            new JsonListEncoder(),
                            new SimpleChannelInboundHandler<Message>() {
                                @Override
                                protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
                                    System.out.println("receive message: " + msg);
                                }
                            }
                    );
                }
            });


            System.out.println("Client started");

            EntityFile eFile1 = new EntityFile();
            eFile1.fileName = "probe1.txt";
            eFile1.filePath = "c:\\probe1.txt";
            eFile1.fileData = "25/04/2021";
            eFile1.fileSize = 256;

            EntityFile eFile2 = new EntityFile();
            eFile2.fileName = "probe2.txt";
            eFile2.filePath = "c:\\probe2.txt";
            eFile2.fileData = "27/03/2021";
            eFile2.fileSize = 512;

            List<Object> filesList = new List<EntityFile>;
            filesList.add(eFile1);
            filesList.add(eFile2);

            Message msg0 = new Message();
            msg0.setBody(filesList);

            ChannelFuture channelFuture = bootstrap.connect("localhost", 9000).sync();
            while (true) {
                final String message = String.format("[%s] %s", LocalDateTime.now(), Thread.currentThread().getName());
                System.out.println("Try to send message: " + message);
                //channelFuture.channel().write(++cntr + "\t");
                channelFuture.channel().writeAndFlush(msg0);
                //channelFuture.channel().writeAndFlush(message + System.lineSeparator());
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
//            });
//        }
    }
}
