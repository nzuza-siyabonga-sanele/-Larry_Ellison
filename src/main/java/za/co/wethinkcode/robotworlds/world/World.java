package za.co.wethinkcode.robotworlds.world;

public class World {

    private final Obstacles obstacles;

    public static AbstractWorld world;


    public World(Obstacles obstacles) {
        this.obstacles = obstacles;
    }

    public void setWorld(){
        this.world = new AbstractWorld(obstacles);
    }

    public static AbstractWorld getWorld() {
        return world;
    }
}
