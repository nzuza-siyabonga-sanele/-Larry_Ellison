package za.co.wethinkcode.robotworlds.API.World;

import org.jetbrains.annotations.NotNull;
import za.co.wethinkcode.robotworlds.API.Quates;
import za.co.wethinkcode.robotworlds.API.Server.WebServer;
import za.co.wethinkcode.robotworlds.Connect_DataBase_And_API.DataBase_To_API;
import za.co.wethinkcode.robotworlds.Json;

import java.util.*;

public class TestWorldData implements Quates<ApiWorld> {
    private Map<String, ApiWorld> worlds;

    public TestWorldData() {

        worlds = new HashMap<>();
        String[] request = {"readData"};

        if(WebServer.args.length > 1) {
            request = WebServer.args;
        }

        DataBase_To_API as = new DataBase_To_API();
        as.add_To_List(request);

        for (Map.Entry<String, Json> set : as.getAll_World_Info().entrySet()) {
            this.add(set.getKey(),ApiWorld.create(set.getValue(), set.getKey()));
        }

    }

    @Override
    public ApiWorld get(String id) {
        return worlds.get(id);
    }

    @Override
    public List<ApiWorld> all() {
        return new ArrayList<>(worlds.values());
    }


    @Override
    public boolean add(String worldName, ApiWorld world) {
        worlds.put(worldName, world);
        return false;
    }
}
