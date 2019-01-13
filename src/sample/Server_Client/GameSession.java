package sample.Server_Client;

import com.mysql.cj.xdevapi.Collection;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

public class GameSession extends Thread {
    public static Set<Socket> clients = Collections.newSetFromMap(new ConcurrentHashMap<>());
    public void run() {
        while (true) {
            if (clients.size() == 2) {
                System.out.println("создаю!");
                Game game = new Game();
                game.setClients(clients);
                game.start();
                send();
            }
        }
    }

    public void send() {
        for (Socket socket : clients) {
            try {
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                dos.writeInt(5);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        clients.clear();
    }
}
