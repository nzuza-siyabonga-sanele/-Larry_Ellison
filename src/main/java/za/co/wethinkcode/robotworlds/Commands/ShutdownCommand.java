package za.co.wethinkcode.robotworlds.Commands;

import za.co.wethinkcode.robotworlds.world.Robot;

public class ShutdownCommand extends Command {
    public ShutdownCommand() {
        super("off");
    }

    @Override
    public boolean execute(Robot target) {
        target.setStatus("Shutting down...");
        return false;
    }
}
