//package za.co.wethinkcode.robotworlds.Commands;
//
//import za.co.wethinkcode.robotworlds.Json;
//import za.co.wethinkcode.robotworlds.world.Robot;
//import za.co.wethinkcode.robotworlds.world.IWorld;
//
//import java.util.List;
//
//public class SprintCommand extends Command {
//
//
//    @Override
//    public Json execute(Robot target) {
//        int nrSteps = Integer.parseInt(getArgument().get(0));
//        while (nrSteps!=0) {
//
//            if (target.getWorld().updatePosition(nrSteps).equals(IWorld.UpdateResponse.SUCCESS)){
//                target.setStatus("Moved forward by "+nrSteps+" steps.");
//                nrSteps--;
//            }
//            else if (target.getWorld().updatePosition(nrSteps).equals(IWorld.UpdateResponse.FAILED_OUTSIDE_WORLD)) {
//                target.setStatus("Sorry, I cannot go outside my safe zone.");
//            }else if (target.getWorld().updatePosition(-nrSteps).equals(IWorld.UpdateResponse.FAILED_OBSTRUCTED)) {
//                target.setStatus("Sorry, there is an obstacle in the way.");}
//            if (nrSteps> 0){
//                target.getPrint += "\n"+target;
//            }
//        }
//        return null;
//    }
//
//    public SprintCommand(String command, List<String> argument) {
//            super("sprint", command, argument);
//        }
//    }
