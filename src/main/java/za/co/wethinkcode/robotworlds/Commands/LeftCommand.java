//package za.co.wethinkcode.robotworlds.Commands;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import za.co.wethinkcode.robotworlds.world.Robot;
//
//public class LeftCommand extends Command {
//
//    @Override
//    public JsonNode execute(Robot target) {
//        target.getWorld().updateDirection(false);
//        target.setStatus("Turned left.");
//        return null;
//    }
//
//    public LeftCommand() {
//        super("left");
//    }
//}
