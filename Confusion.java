import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Animated confusion stars that appear over towers that are confused
 * 
 * @author Kevin Biro
 * @version January 16th 2019
 */
public class Confusion extends Actor
{
    private int frame,timer;
    private GreenfootImage image;

    /**
     * Constructs the class and gets the current image
     */
    public Confusion()
    {
        frame = 0;
        image = getImage();
    }

    /**
     * Act - do whatever the Confusion wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        animate();
    }    

    /**
     * Animates the confusion for 200 acts
     *
     */
    public void animate()
    {
        if(frame%5==0)
        {  
            image = new GreenfootImage("confusion" + frame/5 + ".png");
            image.scale((int)(image.getWidth()*0.3) , (int)(image.getHeight()*0.3));
            setImage(image);
        }
        frame++;
        timer++;
        if(frame==20)
        {
            frame = 0;
        }
        if(timer > 200)
            getWorld().removeObject(this);
    }
}
