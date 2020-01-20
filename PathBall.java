import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Placed on the path that the enemies take
 * 
 * @author Kevin Biro
 * @version January 16th 2019
 */
public class PathBall extends Actor
{
    private int timer,type,transp;
    private GreenfootImage image;
    private boolean destroy; 
    /**
     * Constructs a pathBall with a set type to distinguish between 2 different enemy paths
     * 
     * @param num The type of the pathBall
     */
    public PathBall(int num)
    {
        type = num;
        destroy = false;
        image = getImage();
        image.setTransparency(120);
        setImage(image);
        timer = 0;
    }

    /**
     * Changes the transparency of the pathBall if the level is 3
     * 
     * @param w The world being added to
     */
    public void addedToWorld(World w)
    {
        if(((MyWorld)getWorld()).getLevelType() == 3)
        {
            transp = 170;
            image.setTransparency(transp);
            setImage(image);
        }
    }

    /**
     * Sets the pathBall to destroy if the type corresponds to the pathBall type
     * 
     * @param num The type of the pathBall
     */
    public void destroy(int num)
    {
        if(type == num)
            destroy = true;
    }

    public void act() 
    {
        if(isTouching(PathBall.class))//if touching another ball, remove to prevent overlap
            destroy = true;
        if(destroy)
        {
            getWorld().removeObject(this);
        }
    }    
}
