//package za.co.wethinkcode.robotworlds.Commands;
//
//import za.co.wethinkcode.robotworlds.Json;
//import za.co.wethinkcode.robotworlds.world.Robot;
//
//import java.util.List;
//
//public class ReloadCommand extends Command {
//
//    public ReloadCommand(String command, List<String> argument) {
//        super("forward",command,argument);
//    }
//
//
//    @Override
//    public Json execute(Robot target) {
//        if (target.shots < 5) {
//            target.reload();
//            target.setStatus("Shots reloaded. now you have "+target.shots+" shots.");
//
//        } else if (target.shots == 5) {
//            target.setStatus("you cannot reload, you have " + target.shots + " shots.");
//        }
//        return null;
//    }
//}