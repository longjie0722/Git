package com.java1824.happyzwg;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class PlayCardsClient {
    private  final int port = 9999;
    private String IAper;

    private Socket socket;

    private InputStream is;
    private OutputStream os;

    public PlayCardsClient(String IAper) {
        this.IAper = IAper;
    }

    public void starrtConnet(){
        try {
            this.socket = new Socket(IAper,port);
            this.is = this.socket.getInputStream();
            this.os = this.socket.getOutputStream();
            new Thread(this.writeRun).start();
            new Thread(this.readRun).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Runnable writeRun = new Runnable() {
        @Override
        public void run() {
            Scanner scanner = new Scanner(System.in);
            try {
                while (true) {
                    // 得到用户的输入信息
                    String next = scanner.next();
                    if(!PlayCardsCondition.conditionClient(next)){
                        System.out.println("你违反了游戏规则!");
                        continue;
                    }
                    os.write(next.getBytes());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    };

    public Runnable readRun = new Runnable() {
        @Override
        public void run() {
            try {
                while (true) {
                    byte[] bytes = new byte[1024];
                    int read = is.read(bytes);
                    if (read < 0) {
                        break;
                    }
                    String msg = new String(bytes, 0, read);
                    System.out.println(msg);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    };

}
