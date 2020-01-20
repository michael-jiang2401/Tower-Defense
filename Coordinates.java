/**
 * Returns coordinates to be used by the enemy's pathfinding algorithm
 * Coordinates that are returned is the enemy's target.
 * 
 * @author Kevin Biro
 * @version January 16 2019
 */
public class Coordinates  
{
    private int x;
    private int y;

    /**
     * Saves the x and y coordinates in the parameters
     * 
     * @param x The x coordinate of the target
     * @param y The y coordinate of the target
     */
    public Coordinates(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the x coordinate of the target
     * 
     * @return int The x value of the coordinate
     */
    public int getX()
    {
        return x;
    }
    
    /**
     * Returns the y coordinate of the target
     * 
     * @return int The y value of the coordinate
     */
    public int getY()
    {
        return y;
    }
}
