//package za.co.wethinkcode.robotworlds.Commands;
//
//import za.co.wethinkcode.robotworlds.Json;
//import za.co.wethinkcode.robotworlds.world.Robot;
//import za.co.wethinkcode.robotworlds.world.IWorld;
//
//import java.util.List;
//
//public class ForwardCommand extends Command {
//
//    public ForwardCommand(String command, List<String> arguments) {
//        super("forward", command, arguments);
//    }
//
//    @Override
//    public Json execute(Robot target) {
//        if (target.getWorld().updatePosition(nrSteps).equals(IWorld.UpdateResponse.SUCCESS)){
//            target.setStatus("Moved forward by "+nrSteps+" steps.");
//        }else if (target.getWorld().updatePosition(nrSteps).equals(IWorld.UpdateResponse.FAILED_OUTSIDE_WORLD)) {
//            target.setStatus("Sorry, I cannot go outside my safe zone.");
//        }else if (target.getWorld().updatePosition(nrSteps).equals(IWorld.UpdateResponse.FAILED_OBSTRUCTED)) {
//            target.setStatus("Sorry, there is an obstacle in the way.");}
//        return null;
//    }
//
//
//}
//
