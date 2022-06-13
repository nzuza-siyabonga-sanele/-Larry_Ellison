//package za.co.wethinkcode.robotworlds.Commands;
//
//import za.co.wethinkcode.robotworlds.Json;
//import za.co.wethinkcode.robotworlds.world.Robot;
//import za.co.wethinkcode.robotworlds.world.IWorld;
//
//import java.util.List;
//
//public class BackCommand extends Command {
//
//    @Override
//    public Json execute(Robot target) {
//        int nrSteps = Integer.parseInt(getArgument().get(0));
//        if (target.getWorld().updatePosition(-nrSteps).equals(IWorld.UpdateResponse.SUCCESS)){
//            target.setStatus("Moved back by "+nrSteps+" steps.");
//        }else if (target.getWorld().updatePosition(-nrSteps).equals(IWorld.UpdateResponse.FAILED_OUTSIDE_WORLD)) {
//            target.setStatus("Sorry, I cannot go outside my safe zone.");
//        }else if (target.getWorld().updatePosition(-nrSteps).equals(IWorld.UpdateResponse.FAILED_OBSTRUCTED)) {
//            target.setStatus("Sorry, there is an obstacle in the way.");}
//        return null;
//    }
//
//    public BackCommand(String command, List<String> argument) {
//
//        super("backward", command, argument);
//    }
//}
