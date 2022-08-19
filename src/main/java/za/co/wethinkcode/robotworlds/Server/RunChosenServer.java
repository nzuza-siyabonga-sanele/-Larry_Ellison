package za.co.wethinkcode.robotworlds.Server;

import za.co.wethinkcode.robotworlds.API.Server.WebServer;
import za.co.wethinkcode.robotworlds.world.ActiviteWorld;
import za.co.wethinkcode.robotworlds.world.Port;

import static za.co.wethinkcode.robotworlds.Server.MultiServer.socketServer;


public class RunChosenServer {

    private String chosenSever;

    public RunChosenServer() {

        do{
            chosenSever = Server.getInput("Which server you want to run? {api or our} ");
            if(!chosenSever.equalsIgnoreCase("api")){

            }
        }while (!chosenSever.equalsIgnoreCase("api") && !chosenSever.equalsIgnoreCase("our"));

    }

    public void startChosenServer(ActiviteWorld activite){

        if(chosenSever.equalsIgnoreCase("api")){
            WebServer server = new WebServer();
            server.start(Port.getPortNumber());
        }else if (chosenSever.equalsIgnoreCase("our")){
            socketServer(activite);
        }
    }

}
