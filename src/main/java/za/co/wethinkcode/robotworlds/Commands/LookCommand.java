package za.co.wethinkcode.robotworlds.Commands;

import za.co.wethinkcode.robotworlds.world.Position;
import za.co.wethinkcode.robotworlds.world.Robot;

public class LookCommand extends Command {



    public boolean execute(Robot target) {

        Position robotPosition = target.getWorld().getPosition();
        return true;
    }
    public LookCommand() {

        super("look");
    }
}
