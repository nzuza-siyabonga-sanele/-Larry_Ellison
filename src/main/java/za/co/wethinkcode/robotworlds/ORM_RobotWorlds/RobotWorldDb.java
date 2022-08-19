package za.co.wethinkcode.robotworlds.ORM_RobotWorld;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.lemnik.eodsql.QueryTool;
import za.co.wethinkcode.robotworlds.SQL_DataBase.DBconnection;
import za.co.wethinkcode.robotworlds.Connect_DataBase_And_API.Response_To_WebServer_Get_World;


/**
 * RecipeDbDemo, but this time with PreparedStatements.
 */
public class RobotWorldDb implements DBconnection
{
    public static final String DISK_DB_URL_PREFIX = "jdbc:sqlite:";
    public static final String SEPARATOR = "\t\t";
    private String dbUrl = null;
    public static List<RobotWorldDO> listOfWorlds = new ArrayList<>();

    public RobotWorldDb(String[] args ) {
        processCmdLineArgs( args );
        DBconnector(args);

    }

    private void useTheDb(Connection connection, String[] args) throws SQLException {

        RobotWorldDAI productQuery = QueryTool.getQuery(connection, RobotWorldDAI.class);

        createRobotWorldTable( productQuery );

        if(args[0] == "readData"){
            listOfWorlds = get_World_objects();
        }else if(args[0] == "selected"){
            listOfWorlds.add(selectedWorld( productQuery , args ));
        } else if (args[0].equals("insert")){
            createData( productQuery, args );
        } else if (args[0] == "restore"){
            readAllData( productQuery );
        }

        deleteData( productQuery );
    }

    private List<RobotWorldDO> get_World_objects() {

        List<RobotWorldDO> selectedWorld = new Response_To_WebServer_Get_World().response_Json_World();
        selectedWorld.forEach( p -> displayProduct( p ) );
        return selectedWorld;
    }

    private void readAllData(RobotWorldDAI dai ) {

        final int productCount = dai. getNumberOfProducts();
        display( Integer.toString( productCount ) + " WORLDS." );

        final List<RobotWorldDO> allProducts = dai.getAllProducts();
        display( "\nALL WORLDS:" );
        allProducts.forEach( p -> displayProduct( p ) );

    }

    private RobotWorldDO selectedWorld(final RobotWorldDAI dai, String[] args){

        RobotWorldDO selectedWorld = dai.getProduct(args[1]);
        return selectedWorld;

    }

    private void createRobotWorldTable( final RobotWorldDAI dai ){
        dai.createIfNotEXISTS();
    }

    private void createData(final RobotWorldDAI dai, String[] args)
        throws SQLException
    {
        dai.addProduct( args[1], 0, Integer.parseInt(args[2]), "0.1x0.1", args[3]);
    }

    private void deleteData( final RobotWorldDAI dai )
        throws SQLException
    {
        System.out.println("\nSUCCESSFUL... ;");
    }

    private void displayAllProducts(final RobotWorldDAI dai, String siya) {
        final List<RobotWorldDO> allProducts = dai.getAllProducts();
        allProducts.forEach( p -> displayProduct( p ) );
    }

    private void displayProduct( RobotWorldDO p ){
        if( p == null ){
            display( SEPARATOR + "errrrr..." );
        }else{
            display( SEPARATOR + p.getPrimaryKey() + SEPARATOR + p.getWorld_name() + SEPARATOR + p.getRobots()+
                    SEPARATOR + p.getSize_Of_World()+ SEPARATOR + p.getObstacle_Size() + SEPARATOR + p.getObstacle_Position() );
        }
    }

    private void display( String s ){
        System.out.println( s );
    }

    private void processCmdLineArgs( String[] args ) {
        if( args.length == 2 && args[ 0 ].equals( "-f" ) ){
            final File dbFile = new File( args[ 1 ] );
            if( dbFile.exists() ){
                dbUrl = DISK_DB_URL_PREFIX + args[ 1 ];
            }else{
                dbUrl = DB_URL;
            }
        }else{
            dbUrl = DB_URL;
        }
    }

    @Override
    public void DBconnector() {}

    @Override
    public void DBconnector(String[] args) {
        try( Connection connection = DriverManager.getConnection( dbUrl )){
            useTheDb( connection, args);
            connection.close();
        }catch( SQLException e ){
            throw new RuntimeException( e );
        }
    }

}