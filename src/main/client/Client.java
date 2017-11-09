package main.client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static final int PORT = 4449;

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", PORT);
        System.out.println("=== Игра 'орел / решка' ===");

        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        DataInputStream dis = new DataInputStream(inputStream);
        DataOutputStream dos = new DataOutputStream(outputStream);

        while (true) {
            System.out.println("Введите 'о' - орёл / 'р' - решка : ");
            System.out.println("Если желаете выйти из игры, введите - 'exit'");
            Scanner scanner = new Scanner(System.in);
            String message = scanner.nextLine();

            dos.writeUTF(message);
            dos.flush();

            message = dis.readUTF();
            if(message.equals("exit")){
                System.out.println("Игра закончена! Заходите ещё =)");
                System.exit(0);
            }
            System.out.println(message);
        }
    }
}

