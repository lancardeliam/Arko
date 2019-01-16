package sample.Server_Client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class ServerHandler extends Thread {

    private final ServerSocket server;
    public static List<Socket> clients = new ArrayList<>();
    public ServerHandler(ServerSocket server) {
        this.server = server;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket client = server.accept();
                ClientHandler handler = new ClientHandler(client);
                clients.add(client);
                handler.start();
                handler.setClientHandler(handler);
            } catch (SocketException e) {
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
            }
        }
    }
}