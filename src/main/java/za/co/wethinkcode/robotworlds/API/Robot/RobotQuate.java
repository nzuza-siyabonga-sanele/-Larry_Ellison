package za.co.wethinkcode.robotworlds.API.Robot;

import za.co.wethinkcode.robotworlds.Json;

public class RobotQuate {


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;
    private String state;


    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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
    public static RobotQuate create(Json value, String key) {
        RobotQuate robot = new RobotQuate();
        robot.setId(key);
        robot.setState(value.getJsonData().toString());
        return robot;
    }
}

