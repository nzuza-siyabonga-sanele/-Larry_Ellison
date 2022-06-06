package za.co.wethinkcode.robotworlds.Commands;

import za.co.wethinkcode.robotworlds.world.Robot;

public class RepairCommand extends Command {

    public RepairCommand() {
        super("repair");
    }


    @Override
    public boolean execute(Robot target) {
        target.repair();
        return true;
    }
}
