package za.co.wethinkcode.robotworlds.Connect_DataBase_And_API;

import za.co.wethinkcode.robotworlds.Read_World_Info.Read_Command_line_Arguments;
import za.co.wethinkcode.robotworlds.world.Obstacles;
import za.co.wethinkcode.robotworlds.world.World;
import za.co.wethinkcode.robotworlds.world.worldSize;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Restore_Data_World extends restoreResponse {

    public String getWorldName() {
        return worldName;
    }

    public int getSizeOfWorld() {
        return sizeOfWorld;
    }

    private ArrayList<String> restoreListObs = new ArrayList<>();

    public void resetWorld(){

        String[] args = {"-p", "123456789" ,"-s", String.valueOf(super.sizeOfWorld), "-o", String.valueOf(super.obstaclePosition)};

        Read_Command_line_Arguments nv = new Read_Command_line_Arguments();
        try {
            HashMap<String, Integer> argsList = nv.readArguments(args);

            Obstacles obstacles = new Obstacles(argsList);
            obstacles.addObstacle();

            World world = new World(obstacles);
            world.setWorld();

            worldSize topLeft_bottomRight = new worldSize(argsList);
            topLeft_bottomRight.size();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void setSizeOfWorld(int sizeOfWorld) {
        super.sizeOfWorld = sizeOfWorld;
    }

    public String getObstacleSize() {
        return obstacleSize;
    }

    public ArrayList<String> getObstaclePosition() {
        return obstaclePosition;
    }

    public void setObstaclePosition(String obstaclePosition) {

        String[] obstacle = obstaclePosition.replaceAll("\\[|\\]", "").split(",");

        if(obstacle.length > 1){
            for (int i = 0 ; i < obstacle.length-1 ; i++){
                Obstacles.addToListObstacle(Integer.parseInt(obstacle[i]), Integer.parseInt(obstacle[i])+1);
                String pos = "[" + obstacle[i] + "," + obstacle[i + 1] + "]";
                super.obstaclePosition.add(pos);
            }
        }
    }

    public Restore_Data_World(String worldName, int robotCount, int sizeOfWorld, String obstaclePosition) {
        super.worldName = worldName;
        super.robotCount = robotCount;
        super.sizeOfWorld = sizeOfWorld;
        setObstaclePosition(obstaclePosition);
    }
}
