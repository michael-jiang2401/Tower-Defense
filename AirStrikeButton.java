import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * This is a button that allows an airstrike to be spawned when it is clicked
 * 
 * @author Michael
 * @version 18/01/2019
 */
public class AirStrikeButton extends ConsumableButton
{
    
    /**
     * Creates an airstrike button 
     */
    public AirStrikeButton()
    {
        cost=100;//sets the cost to 100
        
    }
    /**
     * Act - do whatever the AirStrike wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        click();
    }
    /**
     * Adds the target of the airstrike to the world
     */
    public void addConsumable()
    {
        AirStrikeTarget target = new AirStrikeTarget(cost);//creates airstrike target class
        getWorld().addObject(target, getX(), getY());//adds the target to where the user clicks
    }
}
