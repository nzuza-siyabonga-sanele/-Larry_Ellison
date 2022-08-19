package za.co.wethinkcode.robotworlds.Read_World_Info;

import java.io.IOException;
import java.util.HashMap;

public class Read_Command_line_Arguments {

    public Read_Command_line_Arguments() {}

    public HashMap<String, Integer> readArguments(String[] args) throws IOException {

        HashMap<String, Integer> commandLineArgs = new HashMap<String, Integer>();

        for(int i = 0 ; i < args.length ; i++){
            if(args[i].equals("-o")){

                String[] obs = args[i+1].replaceAll("\\[|\\]", "").split(",");

                if(obs.length > 1){
                    commandLineArgs.put("x", Integer.valueOf(obs[0]));
                    commandLineArgs.put("y", Integer.valueOf(obs[1]));
                }


            }if(args[i].equals("-s")){
                commandLineArgs.put("-s", Integer.valueOf(args[i+1]));
            }if(args[i].equals("-p")){
                commandLineArgs.put("-p", Integer.valueOf(args[i+1]));
            }
        }
        return commandLineArgs;
    }
}
