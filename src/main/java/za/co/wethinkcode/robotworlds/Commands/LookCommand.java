package za.co.wethinkcode.robotworlds.Commands;

import za.co.wethinkcode.robotworlds.Json;
import za.co.wethinkcode.robotworlds.world.Robot;

import java.util.List;

public class LookCommand extends Command {


    public LookCommand(String command, List<String> argument) {
        super("look", command, argument);
    }

    public Json execute(Robot target) {


        return null;
    }

}
