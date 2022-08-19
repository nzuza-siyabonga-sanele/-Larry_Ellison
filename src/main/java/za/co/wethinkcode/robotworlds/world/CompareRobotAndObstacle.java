package za.co.wethinkcode.robotworlds.world;

public class CompareRobotAndObstacle {

    public static String object;
    private IWorld.Direction direction;
    private int otherRobotX;
    private int otherRobotY;
    private int obstacleX;
    private int obstacleY;
    public Position nearPosition;

    public CompareRobotAndObstacle(int x, int y, String object, IWorld.Direction directions) {
        this.object = object.toUpperCase();
        this.direction = directions;
        // step 1
        set_Robot_And_Obstacle(x,y);
    }

    public void set_Robot_And_Obstacle(int x, int y){

        if (object.equalsIgnoreCase("obstacle")){
            this.obstacleX = x;
            this.obstacleY = y;
        }else{
            this.otherRobotX = x;
            this.otherRobotY = y;
        }
    }

    public void check_For_A_Near_Object(){

        if(direction.equals(IWorld.Direction.NORTH)){
            check_North();
        }else if(direction.equals(IWorld.Direction.SOUTH)){
            check_South();
        }else if(direction.equals(IWorld.Direction.EAST)){
            check_East();
        }else if(direction.equals(IWorld.Direction.WEST)){
            check_West();
        }
    }

    public void onlyOneObjectFound(){

        if(object.equalsIgnoreCase("obstacle")){
            nearPosition = new Position(obstacleX, obstacleY);
        }else if(object.equals("robot")){
            nearPosition = new Position(otherRobotX, otherRobotY);
        }
    }
    public void check_North(){
        if(obstacleX == otherRobotX && obstacleY < otherRobotY){
            setTypeAndPosition("OBSTACLE",obstacleX, obstacleY);
        }else if(obstacleX == otherRobotX && obstacleY > otherRobotY){
            setTypeAndPosition("ROBOT",otherRobotX, otherRobotY);
        }else{
            onlyOneObjectFound();
        }
    }
    public void check_South(){
        if(obstacleX == otherRobotX && obstacleY > otherRobotY){
            setTypeAndPosition("OBSTACLE",obstacleX, obstacleY);
        }else if(obstacleX == otherRobotX && obstacleY < otherRobotY){
            setTypeAndPosition("ROBOT",otherRobotX, otherRobotY);
        }else{
            onlyOneObjectFound();
        }
    }
    public void check_East(){
        if(obstacleY == otherRobotY && obstacleX < otherRobotX){
            setTypeAndPosition("OBSTACLE",obstacleX, obstacleY);
        }else if(obstacleY == otherRobotY && obstacleX > otherRobotX){
            setTypeAndPosition("ROBOT",otherRobotX, otherRobotY);
        }else{
            onlyOneObjectFound();
        }
    }
    public void check_West(){
        if(obstacleY == otherRobotY && obstacleX > otherRobotX){
            setTypeAndPosition("OBSTACLE",obstacleX, obstacleY);
        }else if(obstacleY == otherRobotY && obstacleX < otherRobotX){
            setTypeAndPosition("ROBOT",otherRobotX, otherRobotY);
        }else{
            onlyOneObjectFound();
        }
    }
    public void setTypeAndPosition(String type, int objectX, int objectY){
        object = type;
        nearPosition = new Position(objectX,objectY);
    }
    public Position getNearPosition() {
        return nearPosition;
    }
}
