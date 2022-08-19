package za.co.wethinkcode.robotworlds.world;

import za.co.wethinkcode.robotworlds.Read_World_Info.ReadConfigFile;
import za.co.wethinkcode.robotworlds.Server.MultiServer;

import java.io.IOException;
import java.util.HashMap;

import static za.co.wethinkcode.robotworlds.Server.MultiServer.configFile;

public class worldSize{

    private final HashMap<String, Integer> commandLineArgs;

    public Integer getWorldSize() {
        return worldSize;
    }

    private Integer worldSize;

    public worldSize(HashMap<String, Integer> commandLineArgs) {
        this.commandLineArgs = commandLineArgs;
    }


    public void size() throws IOException {

        ReadConfigFile read = new ReadConfigFile();
        configFile = read.getWorldInfo();

        if(commandLineArgs.containsKey("-s")) {
            this.worldSize = commandLineArgs.get("-s");
            setPosition(new String[]{String.valueOf(commandLineArgs.get("-s")),
                    String.valueOf(commandLineArgs.get("-s"))});
        }else {
            this.worldSize = Integer.valueOf(configFile.getProperty("size"));
            setPosition(new String[]{configFile.getProperty("size"),configFile.getProperty("size")});
        }
    }
    public static void setPosition(String[] worldSize){

        int bottomRightY = -1 * Integer.parseInt(worldSize[1])/2;
        int bottomRightX = Integer.parseInt(worldSize[0])/2;
        int topLeftY = Integer.parseInt(worldSize[1])/2;
        int topLeftX = -1 * Integer.parseInt(worldSize[0])/2;

        if(topLeftY >= 1){

            MultiServer.numberOfPlayers = ((Integer.parseInt(worldSize[0]) * Integer.parseInt(worldSize[0])))+
                    ((Integer.parseInt(worldSize[1])+Integer.parseInt(worldSize[1]))+1);
        }else {
            MultiServer.numberOfPlayers = Integer.parseInt(worldSize[0]);
        }

        MultiServer.numberOfPlayers -= World.getWorld().getObstacles().size();
        World.getWorld().SetPositions(topLeftX, topLeftY, bottomRightX, bottomRightY);
    }
}
