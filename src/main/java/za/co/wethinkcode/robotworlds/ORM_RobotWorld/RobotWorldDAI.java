package za.co.wethinkcode.robotworlds.ORM_RobotWorld;

import net.lemnik.eodsql.BaseQuery;
import net.lemnik.eodsql.Select;
import net.lemnik.eodsql.Update;

import java.util.List;

public interface RobotWorldDAI extends BaseQuery
{
    @Update(
        "CREATE TABLE IF NOT EXISTS My_world" +
                "(world_id INTEGER not NULL PRIMARY KEY AUTOINCREMENT, " +
                " World_name VARCHAR(50) not NULL UNIQUE, " +
                " Robots INTEGER not NULL," +
                " Size_Of_World INTEGER not NULL, " +
                " Obstacle_Size VARCHAR(50), " +
                " Obstacle_Position VARCHAR(50)) "
    )
    void createIfNotEXISTS();

    //<editor-fold desc="QUERY METHODS">
    /** A db query with NO parameters */
    @Select(
            "SELECT * "
                    + "FROM my_world p "
    )
    List<RobotWorldDO> getAllProducts();

    /** A db Query with one parameter */
    @Select(
            "SELECT * "
                    + "FROM my_world p "
                    + "WHERE p.World_name = ?{1}"
    )
    RobotWorldDO getProduct( String worldName );

    @Select( "SELECT count(*) FROM my_world" )
    public int getNumberOfProducts();
    //</editor-fold>

    //<editor-fold desc="DATA INSERT METHODS">
    /** Create a new Product... */
    @Update(
        "INSERT OR REPLACE INTO my_world ( World_name, Robots, Size_Of_World, Obstacle_Size, Obstacle_Position ) "
                + "VALUES (?{1}, ?{2}, ?{3}, ?{4}, ?{5})"
    )
    void addProduct(  String name ,int robots ,int size ,String obs ,String Obstacles );

    /** Create a new Product... */
    @Update(
        "INSERT OR REPLACE INTO my_world ( World_name, Size_Of_World  ) "
            + "VALUES (?{1.World_name}, ?{2})"
    )
    void addProduct( RobotWorldDO product, int styleId );

    /** Create a new Product, but rely on auto-increment constraint for the `id` primary key */
    @Update(
        "INSERT OR REPLACE INTO my_world ( World_name, Robots, Size_Of_World ) "
            + "VALUES (?{1}, ?{2}, ?{3})"
    )
    int addProduct( String name, int robots, int size  );
    //</editor-fold>

    //<editor-fold desc="DATA UPDATE METHODS">
    @Update(
        "UPDATE my_world "
            + "SET World_name = ?{2} "
            + "WHERE world_id = ?{1} "
    )
    void updateProductName( int productId, String newName );

    @Update(
        "UPDATE my_world "
            + "SET World_name = ?{1.World_name} "
            + "WHERE world_id = ?{1.primaryKey} "
    )
    void updateProductName( RobotWorldDO p );
    //</editor-fold>


}
