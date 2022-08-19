package za.co.wethinkcode.robotworlds.world;

import za.co.wethinkcode.robotworlds.Connect_DataBase_And_API.restoreResponse;
import za.co.wethinkcode.robotworlds.Read_World_Info.Read_Command_line_Arguments;
import za.co.wethinkcode.robotworlds.Server.MultiServer;

import java.io.IOException;
import java.util.HashMap;

public class ActiviteWorld extends restoreResponse {

    public String[] commandLineArguments;

    public ActiviteWorld(String[] args) {
        this.commandLineArguments = args;
    }

    public void activite_World() throws IOException {

        Read_Command_line_Arguments nv = new Read_Command_line_Arguments();
        HashMap<String, Integer> argsList = nv.readArguments(commandLineArguments);

        Obstacles obstacles = new Obstacles(argsList);
        obstacles.addObstacle();

        World world = new World(obstacles);
        world.setWorld();

        worldSize size_Of_World = new worldSize(argsList);
        size_Of_World.size();

        Port port = new Port(argsList);
        port.setPort();

        setWorldToAPIResponse(size_Of_World, obstacles);
    }

    public void setWorldToAPIResponse(worldSize size_Of_World, Obstacles obstacles){
        sizeOfWorld = size_Of_World.getWorldSize();

        for(Obstacle obstacle : obstacles.getObstacles()){
            String pos = "[" + obstacle.getBottomLeftX() + "," + obstacle.getBottomLeftY() + "]";
            obstaclePosition.add(pos);
        }
        robotCount = MultiServer.listOfplayers.size();

    }

}
