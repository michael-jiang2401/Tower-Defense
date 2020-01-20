import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Spawned by the world and cannot be removed.
 * 
 * @author Kevin Biro
 * @version January 16th 2019
 */
public class Obstacle extends Tower
{
    private GreenfootImage image;
    private int timer,transparency;
    private boolean fade;

    /**
     *Constructs the obstacle with a set fade timer.
     */
    public Obstacle()
    {
        timer = 50;
        towerType = 0;
        image = getImage();
        transparency = 50;
        image.setTransparency(50);
        setImage(image);
        fade = false;
    }

    /**
     *Changes the transparency of the obstacle when added to the world if on level 3 
     *to make it more visible.
     *
     *@param w The world being added to.
     */
    public void addedToWorld(World w)
    {
        if(((MyWorld)getWorld()).getLevelType()==3)
        {
            transparency = 150;
            timer = 150;
            image.setTransparency(transparency);
            setImage(image);
        }
    }

    public void act() 
    {
        fadeTransp();//fades the transparency randomly     
    }    

    /**
     *Fades the transparency randomly. Increases the transparency then lowers it back to its 
     *original transparency
     */
    public void fadeTransp()
    {
        if(Greenfoot.getRandomNumber(600) == 1)
        {
            fade = true;//start the fade
        }
        if(fade)
        {
            if(timer < transparency + 50)//increase the transparency
            {
                timer++;
                image.setTransparency(timer);
            }
            else
            {
                fade = false;
            }
        }
        else
        {
            if(timer > transparency)//decrease the transparency
            {
                timer--;
                image.setTransparency(timer);  
            }
        }
    }

    /**
     *Mute the sounds if any are playing
     */
    public void muteSounds(){}
}
