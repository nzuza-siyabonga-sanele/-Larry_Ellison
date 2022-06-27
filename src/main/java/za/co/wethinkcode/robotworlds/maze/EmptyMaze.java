package za.co.wethinkcode.robotworlds.maze;

import za.co.wethinkcode.robotworlds.world.Position;
import za.co.wethinkcode.robotworlds.world.Obstacle;
import za.co.wethinkcode.robotworlds.world.SquareObstacle;

import java.util.ArrayList;
import java.util.List;

public class EmptyMaze implements Maze {

    List<Obstacle> obstacle = new ArrayList<>();
    public EmptyMaze(String[] args){
        if(!args[0].equals("none")){
            this.obstacle.add(new SquareObstacle(Integer.parseInt(args[0]), Integer.parseInt(args[1])));
        }

    }

    @Override
    public List<Obstacle> getObstacles() {
        return this.obstacle;
    }

    @Override
    public boolean blocksPath(Position a, Position b) {
        return false;
    }

}
