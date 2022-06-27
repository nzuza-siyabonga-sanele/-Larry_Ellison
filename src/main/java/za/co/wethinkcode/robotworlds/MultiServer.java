package za.co.wethinkcode.robotworlds;
import java.net.*;
import java.io.*;
import java.util.Properties;

import za.co.wethinkcode.robotworlds.Server.Server;
import za.co.wethinkcode.robotworlds.maze.EmptyMaze;
import za.co.wethinkcode.robotworlds.world.AbstractWorld;
import za.co.wethinkcode.robotworlds.world.IWorld;
import za.co.wethinkcode.robotworlds.world.ReadConfigFile;
public class MultiServer {
    public static Properties configFile;
    public static void main(String[] args) throws IOException {
        //-p 5000 -s 100 -o 10,5
        ServerSocket s;

        if(args.length > 4){
            s = new ServerSocket(Integer.parseInt(args[1]));
            Server.world = new AbstractWorld(new EmptyMaze(new String[]{args[5], args[4]}));
            Server.setposition(new String[]{args[3], args[3]});
        }else{
            ReadConfigFile read = new ReadConfigFile();
            configFile = read.getWorldInfo();
            s = new ServerSocket(Integer.parseInt(configFile.getProperty("port")));
            Server.world = new AbstractWorld(new EmptyMaze(configFile.getProperty("obstacles").split(" ")));
            Server.setposition(new String[]{configFile.getProperty("width"),configFile.getProperty("height")});
        }

        System.out.println("Server running & waiting for client connections.");

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
