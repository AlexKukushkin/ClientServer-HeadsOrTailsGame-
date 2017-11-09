package main.gameserver;

import java.io.*;
import java.net.Socket;
import java.util.Random;

public class ThreadConnectClient extends Thread {
    private Socket socket;
    int balance = 100;
    final Random random = new Random();

    public ThreadConnectClient(Socket s) throws IOException {
        socket = s;
        System.out.println("IP клиента : " + s.getInetAddress().toString());
    }

    @Override
    public void run() {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        String headsOrTails;

        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        DataInputStream dis = new DataInputStream(inputStream);
        DataOutputStream dos = new DataOutputStream(outputStream);

        while (true) {
            String message = null;
            String response;
            headsOrTails = getResult();

            try {
                message = dis.readUTF();
                switch (message) {
                    case "о":
                        if ("о".equals(headsOrTails)) {
                            balance = balance + 100;
                            response = "Вы выйграли! ОРЁЛ\nВаш текущий баланс : " + String.valueOf(balance);
                        } else {
                            balance = balance - 50;
                            response = "Вы проиграли. РЕШКА\nВаш текущий баланс : " + String.valueOf(balance);
                        }
                        dos.writeUTF(String.valueOf(response));
                        dos.flush();
                        break;

                    case "р":
                        if ("р".equals(headsOrTails)) {
                            balance = balance + 100;
                            response = "Вы выйграли! РЕШКА\nВаш текущий баланс : " + String.valueOf(balance);
                        } else {
                            balance = balance - 50;
                            response = "Вы проиграли. ОРЁЛ\nВаш текущий баланс : " + String.valueOf(balance);
                        }
                        dos.writeUTF(String.valueOf(response));
                        dos.flush();
                        break;

                    case "exit":
                        dos.writeUTF("exit");
                        dos.flush();
                        inputStream.close();
                        outputStream.close();

                    default:
                        continue;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(message);
        }
    }

    public String getResult() {
        String headsOrTails;
        boolean result = random.nextBoolean();
        if (result) {
            headsOrTails = "о";
        } else {
            headsOrTails = "р";
        }
        return headsOrTails;
    }
}
