import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Bomber class which drops a row of bombs
 * 
 * @author Michael Jiang
 * @version 1/18/2019
 */
public class Bomber extends Projectile
{
   
    private int counter;//counter in between bomb drops
    /**
     * Creates a bomber setting the bomb counter to 0. The bomber will fly horizontally
     * across the screen while dropping bombs
     */
    public Bomber()
    {
        counter=0;
        speed = 5;
    }
     /**
     * Act - do whatever the Bomber wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        move(speed);
        dropBomb();
        if(isAtEdge())
        {
            getWorld().removeObject(this);
        }
    }    
    /**
     * manages bomb dropping
     */
    public void dropBomb()
    {
        if(counter%20==0)//checks if the counter is divisible by 20
        {
            //creates a bomb every 20 acts
            getWorld().addObject(new Bomb(), getX(), getY());
        }
        counter++;//increases counter
    }
}
