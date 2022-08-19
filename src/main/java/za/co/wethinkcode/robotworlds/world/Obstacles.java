package za.co.wethinkcode.robotworlds.world;

import za.co.wethinkcode.robotworlds.Read_World_Info.ReadConfigFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static za.co.wethinkcode.robotworlds.Server.MultiServer.configFile;

public class Obstacles {

    private final HashMap<String, Integer> commandLineArgs;

    public final static List<Obstacle> obstacle = new ArrayList<>();

    AbstractWorld world;

    public Obstacles(HashMap<String, Integer> commandLineArgs){
        this.commandLineArgs = commandLineArgs;
    }

    public void addObstacle() throws IOException {

        ReadConfigFile read = new ReadConfigFile();
        configFile = read.getWorldInfo();


        if(commandLineArgs.containsKey("x") && commandLineArgs.containsKey("y")) {
            addToListObstacle(commandLineArgs.get("x"), commandLineArgs.get("y"));
            this.obstacle.add(new SquareObstacle(commandLineArgs.get("x"), commandLineArgs.get("y")));
        }else{
            String[] args = configFile.getProperty("obstacles").split(" ");
            if(!args[0].equalsIgnoreCase("none")){
                addToListObstacle(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
            }
        }

    }

    public static void addToListObstacle(Integer x, Integer y){
        obstacle.clear();
        obstacle.add(new SquareObstacle(x, y));
    }

    public List<Obstacle> getObstacles(){return this.obstacle;}

    public boolean blocksPath(Position a, Position b) {
        return false;
    }


}
