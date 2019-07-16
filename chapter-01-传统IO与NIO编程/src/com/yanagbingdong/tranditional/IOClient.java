package com.yanagbingdong.tranditional;

import java.net.Socket;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import static com.yanagbingdong.tranditional.IOServer.PORT;

/**
 * @author ybd
 * @date 19-7-15
 * @contact yangbingdong1994@gmail.com
 */
public class IOClient {

    public static void main(String[] args) {
        new Thread(() -> {
            try {
                Socket socket = new Socket("0.0.0.0", PORT);
                while (true) {
                    socket.getOutputStream().write((LocalDateTime.now() + ": Hello World!").getBytes());
                    TimeUnit.SECONDS.sleep(2L);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
