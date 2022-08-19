package za.co.wethinkcode.robotworlds.Commands;

import com.fasterxml.jackson.databind.JsonNode;
import za.co.wethinkcode.robotworlds.Json;
import za.co.wethinkcode.robotworlds.Server.MultiServer;
import za.co.wethinkcode.robotworlds.world.Robot;

import java.util.List;
import java.util.Map;

public class LeftCommand extends Command {

    private Integer myShields;
    private Integer myShots;

    @Override
    public Json execute(Robot target) {

        Json data = new Json();

        for (Map.Entry<String, Json> set : MultiServer.listOfplayers.entrySet()) {
            if(set.getKey().equals(target.getName())){

                myShields = (Integer) set.getValue().getJsonData().getJSONObject("state").get("shields");
                myShots =  (Integer) set.getValue().getJsonData().getJSONObject("state").get("shots");
            }
        }

        target.getWorld().updateDirection(false);
        data.setMap("message", "Done");

        Json state = new Json();

        state.setMap("position", new int[]{target.getWorld().getPosition().getX(), target.getWorld().getPosition().getY()});
        state.setMap("direction", target.getWorld().getCurrentDirection());
        state.setMap("shields", myShields);
        state.setMap("shots", myShots);
        state.setMap("status", "normal");

        Json jData = new Json();
        jData.setMap("result", "OK");
        jData.setMap("data", data.getJsonData());
        jData.setMap("state", state.getJsonData());

        MultiServer.listOfplayers.put(target.getName(),jData);
        return jData;
    }

    public LeftCommand(String command, List<String> argument) {
        super("left", command, argument);
    }
}
