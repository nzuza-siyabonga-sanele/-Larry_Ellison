package za.co.wethinkcode.robotworlds.ORM_RobotWorld;


import net.lemnik.eodsql.ResultColumn;

/**
 * TODO: javadoc ProductDO
 */
public class RobotWorldDO
{
    public int world_id;

    public String World_name;

    public int Robots;

    public int Size_Of_World;

    public String Obstacle_Size;

    public String Obstacle_Position;

    public RobotWorldDO(){}

    public RobotWorldDO(String name ){
        this.World_name = name;
    }

    public int getPrimaryKey() {
        return world_id;
    }

    @ResultColumn( value = "world_id" )
    public void setPrimaryKey( int key ) {
        this.world_id = key;
    }


    public String getWorld_name() {
        return World_name;
    }
    @ResultColumn( value = "World_name" )
    public void setWorld_name( String name ) {
        this.World_name = name;
    }


    public int getRobots() {
        return Robots;
    }
    @ResultColumn( value = "Robots" )
    public void setRobots( int robots ) {
        this.Robots = Robots;
    }


    public int getSize_Of_World() {
        return Size_Of_World;
    }
    @ResultColumn( value = "Size_Of_World" )
    public void setSize_Of_World(int size_Of_World) {
        Size_Of_World = size_Of_World;
    }

    public String getObstacle_Size() {
        return Obstacle_Size;
    }
    @ResultColumn( value = "Obstcle_Size" )
    public void setObstacle_Size(String obstacle_Size) {
        Obstacle_Size = obstacle_Size;
    }

    public String getObstacle_Position() {
        return Obstacle_Position;
    }
    @ResultColumn( value = "Obstcle_Position" )
    public void setObstacle_Position(String obstacle_Position) {
        Obstacle_Position = obstacle_Position;
    }
}