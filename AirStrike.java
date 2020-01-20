import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Airstrike class which spawns an explosion after passing over a specific x position
 * 
 * @author Michael Jiang 
 * @version 1/18/2019
 */
public class AirStrike extends Projectile
{
    private int xPos; //the x position of where the bomb should be dropped
    private boolean dropped = false;//whether or not the bomb has been dropped
    /**
     * Creates an Airstrike which flys horizontally and drops a bomb after passing a specified 
     * x location
     * 
     * @param xPos The x position where the bomb should be dropped
     */
    public AirStrike(int xPos)
    {
        this.xPos=xPos;
        speed = 10;
       }
    /**
     * Act - do whatever the AirStrike wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        move(speed);
        if(getX()>xPos)//checks if plane has passed the x coordinate
        {
            if(!dropped)//checks if bomb has been dropped
            {
                //creates an explosion at the designated location if bomb has not been dropped
                Explosion explosion = new Explosion((((MyWorld)getWorld()).getRound()*1000));
                getWorld().addObject(explosion, xPos, getY());
                dropped = true;
            }
        }
        if(isAtEdge())//removes airplane when it has flown to the edge of the screen
        {
            getWorld().removeObject(this);
        }
    }    
}
