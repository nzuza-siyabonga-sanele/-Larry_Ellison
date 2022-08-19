package za.co.wethinkcode.robotworlds.API.World;

import za.co.wethinkcode.robotworlds.Json;

public class ApiWorld {
    private String id;
    private String obstaclePosition;
    private String sizeOfWorld;
    private String robotCount;
    private String obstacleSize;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    private String state;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public void setObstaclePosition(String obstaclePosition) {
        this.obstaclePosition = obstaclePosition;
    }


    public void setSizeOfWorld(String sizeOfWorld) {
        this.sizeOfWorld = sizeOfWorld;
    }


    public void setRobotCount(String robotCount) {
        this.robotCount = robotCount;
    }


    public void setObstacleSize(String obstacleSize) {
        this.obstacleSize = obstacleSize;
    }

    /**
     * Use this convenient factory method to create a new quote.
     * Warning the ID will be null!
     * {}
     *
     * @param value
     * @param key
     * @return a Quote object
     */
    public static ApiWorld create(Json value, String key) {
        ApiWorld world = new ApiWorld();
        world.setId(key);
        world.setState(value.getJsonData().toString());
        return world;
    }
}

