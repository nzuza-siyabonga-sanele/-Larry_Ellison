package za.co.wethinkcode.robotworlds.API;

import java.util.Collection;
import java.util.List;

public interface Quates<T> {
    /**
     * Get a single quote by id.
     * @param id the Id of the quote
     * @return a Quote
     */
    T get(String id);


    /**
     * Get all quotes in the database
     * @return A list of quotes
     */
    List<T> all();

    /**
     * Add a single quote to the database.
     *
     * @param world the world to add
     * @return the newly added Quote
     */
//    boolean add(T world);
    boolean add(String t,T world);

}
