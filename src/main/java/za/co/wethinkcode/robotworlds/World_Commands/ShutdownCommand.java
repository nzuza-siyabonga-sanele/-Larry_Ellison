package za.co.wethinkcode.robotworlds.World_Commands;

public class ShutdownCommand {

    public ShutdownCommand() {
        System.out.println("Shutting Down Server...");
    }
    public void stop(){
        System.exit(0);
    }
}
