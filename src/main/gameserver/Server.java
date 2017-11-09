package main.gameserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    public static final int PORT = 4449;
    static ServerSocket socket;
    public static List<ThreadConnectClient> clients = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        System.out.println("Игровой сервер запущен !!!");
        socket = new ServerSocket(PORT);
        while (true) {
            ThreadConnectClient client = new ThreadConnectClient(socket.accept());
            clients.add(client);
            client.start();
        }
    }
}
