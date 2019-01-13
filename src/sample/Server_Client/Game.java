package sample.Server_Client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.Set;

public class Game extends Thread{
    public static Set<Socket> clients;

    public void setClients(Set<Socket> clients) {
        Game.clients = clients;
    }

    @Override
    public void run() {
    }



}
