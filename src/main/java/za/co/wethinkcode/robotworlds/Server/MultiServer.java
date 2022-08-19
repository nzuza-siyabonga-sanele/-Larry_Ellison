package za.co.wethinkcode.robotworlds.Server;

import za.co.wethinkcode.robotworlds.Json;
import za.co.wethinkcode.robotworlds.World_Commands.executeCommand;
import za.co.wethinkcode.robotworlds.world.ActiviteWorld;
import za.co.wethinkcode.robotworlds.world.Port;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Properties;
import java.util.Scanner;

public class MultiServer {
    public static Properties configFile;
    public static int numberOfPlayers;
    public static HashMap<String, Json> listOfplayers = new HashMap<>();

    public static void main(String[] args) throws IOException {

        ActiviteWorld activite = new ActiviteWorld(args);
        activite.activite_World();

        RunChosenServer run = new RunChosenServer();

        MultiServer listen = new MultiServer();
        listen.listenServer(args);

        run.startChosenServer(activite);

    }

    public static void socketServer(ActiviteWorld activite){
        System.out.println("Server running & waiting for client connections.");

        while(true) {
            try {
                ServerSocket s = Port.getPORT();
                Socket socket = s.accept();
                System.out.println("Connection: " + socket);

                Runnable r = new Server(socket);
                Thread task = new Thread(r);
                task.start();

            } catch(IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void listenServer(String[] args)throws IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Scanner scanner = new Scanner(System.in);
                String worldCommand;
                do {
                    System.out.println("> What must I do next?");
                    worldCommand = scanner.nextLine().trim();
                    new executeCommand(worldCommand);
                } while (true);

            }
        }).start();
    }



}