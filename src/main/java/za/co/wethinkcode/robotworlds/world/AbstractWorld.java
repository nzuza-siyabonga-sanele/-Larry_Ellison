package za.co.wethinkcode.robotworlds.world;

import za.co.wethinkcode.robotworlds.Json;
import za.co.wethinkcode.robotworlds.Server.MultiServer;

import java.util.List;
import java.util.Map;

public class AbstractWorld implements IWorld{
    public static Position TOP_LEFT;
    public static Position BOTTOM_RIGHT;
    public static final Position CENTRE = new Position(0,0);
    Position position;
    protected Direction currentDirection;
    private final Obstacles maze;
    private String objectType;
    private int objectX;
    private int objectY;

    public String getObjectType() {
        return objectType;
    }

    public int getObjectX() {
        return objectX;
    }

    public int getObjectY() {
        return objectY;
    }

    @Override
    public void SetPositions(int topLeftX, int topLeftY, int bottomRightX, int bottomRightY){
        TOP_LEFT = new Position(topLeftX,topLeftY);
        BOTTOM_RIGHT = new Position(bottomRightX,bottomRightY);
    }

    public AbstractWorld(Obstacles obstacles){
        this.maze = obstacles;
        this.position = CENTRE;
        this.currentDirection = Direction.NORTH;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public UpdateResponse updatePosition(int nrSteps, Robot target) {

        int newX = this.position.getX();
        int newY = this.position.getY();

        if (Direction.NORTH.equals(this.currentDirection)) {
            newY = newY + nrSteps;
        }else if(Direction.SOUTH.equals(this.currentDirection)) {
            newY = newY - nrSteps;
        }else if(Direction.WEST.equals(this.currentDirection)) {
            newX = newX - nrSteps;
        }else if(Direction.EAST.equals(this.currentDirection)) {
            newX = newX + nrSteps;
        }

        Position newPosition = new Position(newX, newY);
        return isRobotOrObstacleNear(target, newPosition, getCurrentDirection());

    }

    public UpdateResponse isRobotOrObstacleNear(Robot target, Position newPosition, Direction directions){

        int count = 0;
        for (Obstacle obstacle :maze.getObstacles()){
            if(obstacle.getBottomLeftX() == newPosition.getX() && obstacle.getBottomLeftY() == newPosition.getY()){
                checkNearObject(newPosition.getX(), newPosition.getY(),"obstacle",directions);
                count =+ 1;
            }
            if (obstacle.blocksPath(this.position,newPosition)){
                checkNearObject(newPosition.getX(), newPosition.getY(),"obstacle", directions);
                count =+ 1;
            }
        }
        if(isRobot(target, newPosition, directions)){
            return UpdateResponse.FAILED_OBSTRUCTED;
        }else if(count > 0){
            return UpdateResponse.FAILED_OBSTRUCTED;
        }
        else if (newPosition.isIn(TOP_LEFT,BOTTOM_RIGHT)){
            this.position = newPosition;
            return UpdateResponse.SUCCESS;
        }
        return  UpdateResponse.FAILED_OUTSIDE_WORLD;
    }
    public void checkNearObject(int x, int y, String object, Direction directions){
        // step 1
        CompareRobotAndObstacle con = new CompareRobotAndObstacle(x,y, object, directions);

        con.check_For_A_Near_Object();
        objectType = CompareRobotAndObstacle.object;
        objectX = con.getNearPosition().getX();
        objectY = con.getNearPosition().getX();
    }

    public boolean isRobot(Robot target, Position newPosition, Direction directions){

        if(MultiServer.listOfplayers.size() > 0){
            for (Map.Entry<String, Json> set : MultiServer.listOfplayers.entrySet()) {
                if(!set.getKey().equals(target.getName())){

                    int x = (Integer) set.getValue().getJsonData().getJSONObject("state").getJSONArray("position").get(0);
                    int y = (Integer) set.getValue().getJsonData().getJSONObject("state").getJSONArray("position").get(1);


                    boolean checksX; boolean checksY;
                    if(target.getStatus().equals("looking")){

                        if((newPosition.getX()-target.getVisibility()) <= x && newPosition.getX() >= x  && y == newPosition.getY()){
                            checksY = true; checksX = true;
                        }else if((newPosition.getY()-target.getVisibility()) <= y && newPosition.getY() >= y && x == newPosition.getX()){
                            checksY = true; checksX = true;
                        }else {
                            checksY = false; checksX = false;
                        }
                    }else{
                        checksX = (newPosition.getX() == x);
                        checksY = (newPosition.getY() == y);
                    }

                    if(checksX && checksY){

                        checkNearObject(x, y,"robot", directions);
                        return checksX && checksY;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void updateDirection(boolean turnRight) {
        if (turnRight == true) {
            if (Direction.NORTH.equals(this.currentDirection)) {
                this.currentDirection = Direction.EAST;
            } else if (Direction.SOUTH.equals(this.currentDirection)) {
                this.currentDirection = Direction.WEST;
            } else if (Direction.WEST.equals(this.currentDirection)) {
                this.currentDirection = Direction.NORTH;
            } else if (Direction.EAST.equals(this.currentDirection)) {
                this.currentDirection = Direction.SOUTH;
            }
        } else if (turnRight == false) {
            if (Direction.NORTH.equals(this.currentDirection)) {
                this.currentDirection = Direction.WEST;
            } else if (Direction.SOUTH.equals(this.currentDirection)) {
                this.currentDirection = Direction.EAST;
            } else if (Direction.WEST.equals(this.currentDirection)) {
                this.currentDirection = Direction.SOUTH;
            } else if (Direction.EAST.equals(this.currentDirection)) {
                this.currentDirection = Direction.SOUTH;
            }
        }
    }

    @Override
    public Position getPosition() {
        return this.position;
    }

    @Override
    public Position resetPosition(int nrSteps) {
        int newX = this.position.getX();
        int newY = this.position.getY();

        if (Direction.NORTH.equals(this.currentDirection)) {
            newY = newY - nrSteps;
        }else if(Direction.SOUTH.equals(this.currentDirection)) {
            newY = newY + nrSteps;
        }else if(Direction.WEST.equals(this.currentDirection)) {
            newX = newX + nrSteps;
        }else if(Direction.EAST.equals(this.currentDirection)) {
            newX = newX - nrSteps;
        }
        return this.position = new Position(newX, newY);
    }

    @Override
    public Direction getCurrentDirection() {
        return this.currentDirection;
    }

    @Override
    public void setCurrentDirection(Direction direction) {
        this.currentDirection = direction;
    }

    @Override
    public boolean isNewPositionAllowed(Position position) {
        return position.isIn(TOP_LEFT,BOTTOM_RIGHT);
    }

    @Override
    public boolean isAtEdge() {
        return this.position.getX() == TOP_LEFT.getX()
                || this.position.getY() == TOP_LEFT.getY()
                || this.position.getX() == BOTTOM_RIGHT.getX()
                || this.position.getY() == BOTTOM_RIGHT.getY();
    }
    @Override
    public void reset() {
        this.position = IWorld.CENTRE;
        this.currentDirection = Direction.NORTH;
    }

    @Override
    public List<Obstacle> getObstacles() {
        return maze.getObstacles();
    }

    @Override
    public void showObstacles() {
        List<Obstacle> newObstacle= this.getObstacles();

        for (int i = 0; i < newObstacle.size(); i++) {
            int x= newObstacle.get(i).getBottomLeftX();
            int y= newObstacle.get(i).getBottomLeftY();
        }
    }

    @Override
    public void removeObstacle(Robot target){
        for (int i = 0; i <getObstacles().size(); i++) {
            if (target.getWorld().getPosition() ==getObstacles().get(i)){
                getObstacles().remove(i);
            }
        }
    }

    public void showObstacles(List<Obstacle> maze) {
        List<Obstacle> newObstacle= maze;
        for (int i = 0; i < newObstacle.size(); i++) {
           int x= newObstacle.get(i).getBottomLeftX();
           int y= newObstacle.get(i).getBottomLeftY();
        }
    }
}