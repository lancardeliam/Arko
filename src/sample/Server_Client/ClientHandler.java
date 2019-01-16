package sample.Server_Client;


import sample.Database.DatabaseHandler;
import sample.Database.User;
//import sample.settings.UserMessage;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientHandler extends Thread {

    private Socket client;
    private String nickname = "unknown";
    private DatabaseHandler handler = new DatabaseHandler();
    private static boolean created = false;
    private ObjectInputStream ois;
    private ClientHandler clientHandler;

    public Socket getClient() {
        return client;
    }

    public void setClientHandler(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    public ClientHandler getClientHandler() {
        return clientHandler;
    }

    public String getNickname() {
        return nickname;
    }

    ClientHandler(Socket client) {
        this.client = client;
    }

    public void run() {
        while (!client.isClosed()) {
            try {
                if (!readData())
                    Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendCurrentPlayers(String message, int idMessage) throws IOException {
        DataOutputStream dos = new DataOutputStream(client.getOutputStream());
        dos.writeInt(idMessage);
        dos.writeUTF(message);
    }

    public void send(String message, int idMessage) throws IOException {
        for (Socket entry : ServerHandler.clients) {
            try {
                DataOutputStream dos = new DataOutputStream(entry.getOutputStream());
                dos.writeInt(idMessage);
                dos.writeUTF(message);
            } catch (SocketException ignored) {
            }
        }
    }

    public void send(Boolean free, int idMessage) throws IOException {
     DataOutputStream dos = new DataOutputStream(client.getOutputStream());
     dos.writeInt(idMessage);
     dos.writeBoolean(free);
    }

    private boolean readData() {
        try {
            DataInputStream dis = new DataInputStream(client.getInputStream());
            User user;
            int counter ;
            ResultSet set;
            if (dis.available() <= 0) {
                return false;
            }
            int id;
            id = dis.readInt();
            switch (id) {
                case 1:
                    ois = new ObjectInputStream(client.getInputStream());
                     user = (User) ois.readObject();
                    set = handler.checkUser(user);
                     counter = 0;
                    while (set.next()){
                        counter++;
                    }
                    if (counter>0){
                        send(false,1);
                    }
                    else {
                        handler.singUpUser(user);
                        send(true,1);
                    }
                    ois = null;
                    break;
                case 2:
                    ois = new ObjectInputStream(client.getInputStream());
                    user = (User) ois.readObject();
                     set = handler.checkUser(user);
                     counter = 0;
                    while (set.next()){
                        counter++;
                    }
                    if (counter>0){
                        send(true,2);
                    }
                    else {

                        send(false,2);

                    }
                    ois = null;
                    break;
                case 3:
                    dis.readUTF();
                    GameSession.clients.add(client);
                    send(String.valueOf(GameSession.clients.size()) + " из 5 ",4);
                    break;
                case 4:
                    dis.readUTF();
                    sendCurrentPlayers(String.valueOf(GameSession.clients.size()) + " из 5 ",4);
                    break;
                case 5:
                    dis.readUTF();
                    GameSession.clients.remove(client);
                    send(String .valueOf(GameSession.clients.size())+" из 5 " ,4 );

            }

        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }
}
