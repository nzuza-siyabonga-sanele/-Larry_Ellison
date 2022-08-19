package za.co.wethinkcode.robotworlds.Server;

import za.co.wethinkcode.robotworlds.Commands.Command;
import za.co.wethinkcode.robotworlds.Json;
import za.co.wethinkcode.robotworlds.world.Robot;

import java.util.List;


public class ErrorHandling extends Command {

    public ErrorHandling(String name,String command, List<String> argument) {
        super(name,command,argument);
    }

    @Override
    public Json execute(Robot target) {

        if(!target.getHasLaunched().equals(getName())){

            Json jData = new Json();
            jData.setMap("message",getName());

            Json jDataMsg = new Json();
            jDataMsg.setMap("result", "ERROR");
            jDataMsg.setMap("data",jData.getJsonData());

            return jDataMsg;

        }else {
            Json data = new Json();
            data.setMap("Launch Command", "To Start The Game use eg:{launch 5 5}");

            Json jData = new Json();
            jData.setMap("result", "ERROR");
            jData.setMap("message", data.getJsonData());
            return jData;
        }
    }
}