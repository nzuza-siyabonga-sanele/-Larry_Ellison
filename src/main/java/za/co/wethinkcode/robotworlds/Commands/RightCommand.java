//package za.co.wethinkcode.robotworlds.Commands;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import za.co.wethinkcode.robotworlds.world.Robot;
//
//public class RightCommand extends Command {
//
//    @Override
//    public JsonNode execute(Robot target) {
//        target.getWorld().updateDirection(true);
//        target.setStatus("Turned right.");
//
//        return null;
//    }
//
//    public RightCommand() {
//        super("right");
//    }
//}
