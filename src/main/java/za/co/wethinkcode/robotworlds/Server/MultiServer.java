package za.co.wethinkcode.robotworlds.Server;

import java.net.*;
import java.io.*;
import java.util.HashMap;

import za.co.wethinkcode.robotworlds.maze.EmptyMaze;
import za.co.wethinkcode.robotworlds.world.AbstractWorld;


public class MultiServer {

    public static void main(String[] args) throws IOException {

        ServerSocket s = new ServerSocket(Server.PORT);
        System.out.println("Server running & waiting for client connections.");

        // moved the world activaction to the multiServer from the Server
        Server.world = new AbstractWorld(new EmptyMaze());

        while(true) {
            try {
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
}