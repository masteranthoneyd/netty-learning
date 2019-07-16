package com.yanagbingdong.tranditional;


import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author ybd
 * @date 19-7-15
 * @contact yangbingdong1994@gmail.com
 */
public class IOServer {


    static final int PORT = 7000;

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(PORT);

        new Thread(() -> {
            try {
                while (true) {
                    /* 1. 阻塞获取新连接 */
                    Socket socket = serverSocket.accept();

                    /* 2. 每个新连接创建一个新的线程读取数据 */
                    new Thread(() -> {
                        try {
                            InputStream in = socket.getInputStream();

                            int length;
                            byte[] data = new byte[1027];
                            while ((length = in.read(data)) != -1) {
                                System.out.println(new String(data, 0, length));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }).start();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

}
