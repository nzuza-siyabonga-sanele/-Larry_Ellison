package za.co.wethinkcode.robotworlds.world;

import za.co.wethinkcode.robotworlds.Commands.Command;
import za.co.wethinkcode.robotworlds.Json;
import za.co.wethinkcode.robotworlds.Server.Server;

import java.util.ArrayList;
import java.util.Properties;


public class Robot {

    private final int visibility;
    private String status;
    private String name;
    private int shots;
    private int shield;
    private ArrayList<String> History;
    IWorld world;


    public Robot(String name, IWorld world, Properties configFile) {
        this.world = world;
        this.name = name;
        this.status = "Ready";
        this.visibility = Integer.parseInt(configFile.getProperty("visibility"));
    }

    public String getHasLaunched() {return Server.hasLaunched;}

    public int getVisibility() {return visibility;}

    public void setHasLaunched(String hasLaunched) {Server.hasLaunched = hasLaunched;}

    public int getShots() {
        return shots;
    }

    public int getShield() {
        return shield;
    }

    public int shot(){
        return this.shots=this.shots-1;
    }

    public void reload(){
        this.shots=5;
    }

    public String getStatus() {
        return this.status;
    }

    public ArrayList<String> getHistory() {
        return this.History;
    }


    public void appendToHistory(String command) {
        this.History.add(command);
    }

    public Json handleCommand(Command command) {
        return command.execute(this);
    }

    @Override
    public String toString() {
        return "[" + this.getWorld().getPosition().getX() + "," + this.getWorld().getPosition().getY() + "] "
                + this.name + "> " + this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public IWorld getWorld() {
        return this.world;
    }

    public void setShieldAndShots(String shield, String shot) {
        this.shield = Integer.parseInt(shield);
        this.shots = Integer.parseInt(shot);
    }
}