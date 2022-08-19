package za.co.wethinkcode.robotworlds.world;

import za.co.wethinkcode.robotworlds.Read_World_Info.ReadConfigFile;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;

import static za.co.wethinkcode.robotworlds.Server.MultiServer.configFile;

public class Port {

    private static ServerSocket PORT;

    private static int PortNumber;
    private final HashMap<String, Integer> commandLineArgs;

    public Port(HashMap<String, Integer> commandLineArgs) {
        this.commandLineArgs = commandLineArgs;
    }

    public static int getPortNumber() {
        return PortNumber;
    }

    public void setPort() throws IOException {

        ReadConfigFile read = new ReadConfigFile();
        configFile = read.getWorldInfo();

        if(commandLineArgs.containsKey("-p")) {
            PortNumber = commandLineArgs.get("-p");
        } else{
            PortNumber = Integer.parseInt(configFile.getProperty("port"));
        }
    }

    public static ServerSocket getPORT() throws IOException {
        PORT = new ServerSocket(PortNumber);
        return PORT;
    }

}
