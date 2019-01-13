package sample.Server_Client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.Scanner;


public class Server {
    public static void main(String[] args) throws IOException {
        start();
        work();
        end();
    }
    private static ServerSocket server;



    private static void start() throws IOException {
        server = new ServerSocket(1234,0,InetAddress.getByName("localhost"));
    }
    private static void work() throws IOException {
        ServerHandler shandler = new ServerHandler(server);
        GameSession game = new GameSession();
        game.start();
        shandler.start();
        readChat();
    }
    private  static void end() throws IOException {
        server.close();
    }

    static void readChat() throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String line = scanner.nextLine();
            if (line !=null) {
                if (line.equals("/size")) {
                    System.out.println(GameSession.clients.size());
                }
                if (line.equals("/game")) {
                    System.out.println(Game.clients.size());
                }
            }
            else try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
