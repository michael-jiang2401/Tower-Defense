import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Can be purchased by the user and follows the mouse until placed on the screen.
 * 
 * @author Michael J
 * @version January 16th 2019
 */
public abstract class Consumable extends Actor
{
    protected boolean clicked=true;

    /**
     * Checks if the screen is clicked
     *
     */
    protected void click()
    {
        if(Greenfoot.mousePressed(null)&& clicked)
        {
            clicked=false;
        }
    }

    /**
     * Follows the mouse
     *
     */
    protected void clickAndDrop()
    {
        if(clicked)
        {
            MouseInfo mouse = Greenfoot.getMouseInfo();
            setLocation(mouse.getX(), mouse.getY());
        }
    }

}
