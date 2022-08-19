package za.co.wethinkcode.robotworlds.Connect_DataBase_And_API;

import za.co.wethinkcode.robotworlds.Json;

import java.util.ArrayList;

public class restoreResponse {
    public static String worldName = "Ready";
    public static int robotCount;
    public static int sizeOfWorld;
    public static String obstacleSize = "0.01x0.01";
    public static ArrayList<String> obstaclePosition = new ArrayList<>();


    public Json response(){

        Json state = new Json();
        state.setMap("worldName", worldName);
        state.setMap("robotCount", robotCount);
        state.setMap("sizeOfWorld", sizeOfWorld);
        state.setMap("obstacleSize", obstacleSize);
        state.setMap("obstaclePosition", obstaclePosition);

        return state;
    }



}
