import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A big red X that fades away after being added to the world
 * 
 * @author Kevin Biro
 * @version January 16th 2019
 */
public class X extends Actor
{
    private int timer;
    private GreenfootImage image;
    /**
     * Constructs the X by setting the time alive and image
     */
    public X()
    {
        timer = 200;
        image = getImage();
        image.setTransparency(200);
        setImage(image);
    }
    
    /**
     * Decreases the timer until it reaches 0. The object will then be removed
     */
    public void act() 
    {
        timer--;
        image.setTransparency(timer);
        setImage(image);
        if(timer < 1)
            getWorld().removeObject(this);
    }
}
