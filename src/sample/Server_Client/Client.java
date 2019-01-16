package sample.Server_Client;

import javafx.application.Platform;
import sample.Database.User;
import sample.Main;
import sample.controller.Controller_Enter;
import sample.controller.Controller_Reg;

import java.io.*;
import java.net.Socket;

public class Client extends Thread {

    private Socket socket;
    private boolean connected = false;
    private ObjectInputStream ois;
    public static String nickname;


    public void connect() {
        while (!connected) {
            try {
                socket = new Socket("localhost", 1234);
                connected = true;
                System.out.println("Connected!");
            } catch (IOException e) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                //System.out.println(e.getMessage());
            }
        }
    }

    public void send(String message, int idMessage) {
        try {
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeInt(idMessage);
            dos.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(User user, int idMessage) {
        try {
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeInt(idMessage);
            ObjectOutputStream ous = new ObjectOutputStream(socket.getOutputStream());
            ous.writeObject(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void handle() {
        new Thread(() -> {
            while (true) {
                try {
                    DataInputStream dis = new DataInputStream(socket.getInputStream());
                    if (dis.available() <= 0) {
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        continue;
                    }
                    int id = dis.readInt();
                    System.out.println(id);
                    switch (id) {
                        case 1:
                            if (dis.readBoolean()) {
                                Main.create("fxml/in.fxml");
                            } else {

                                System.out.println("error!!");
                            }
                            break;
                        case 2:
                            if (dis.readBoolean()) {
                                Main.create("fxml/enter.fxml");
                            } else {
                                System.out.println("error!!");
                            }
                            break;
                        case 3:
                            break;
                        case 4:
                            String line =  dis.readUTF();
                            System.out.println(line);
                            Platform.runLater(() -> Controller_Enter.setLabel(line));
                            break;
                        case 5:
                            Main.create("fxml/list1.fxml");
                            break;
                        case 6:
                            break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
