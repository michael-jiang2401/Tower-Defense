import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Adds a customizable text that floats upwards from where it was added and removes itself 
 * after a certain amount of time.
 * 
 * @author Kevin Biro
 * @version January 16th 2019
 */
public class FloatingText extends Actor
{
    private GreenfootImage image;
    private int speed, timer;
    private Color textColor;

    /**
     * Constructs the text with a set float speed, display and level type.
     *
     * @param floatSpeed The speed at which the text floats and disappears
     * @param display The display of the text
     * @param levelType The level type.
     */
    public FloatingText(int floatSpeed, String display, int levelType)
    {
        image = new GreenfootImage(70,20);
        if(levelType != 1 && levelType != 4)//if level is 2,3 color is yellow
        {
            textColor = Color.YELLOW;
        }
        else//if level is 1,4 color is black
        {
            textColor = Color.BLACK;
        }
        GreenfootImage text = new GreenfootImage(display, 26 , textColor, null);

        image.drawImage(text, 10 , 0);
        setImage(image);

        timer = 255;
        speed = floatSpeed;
    }

    /**
     * Floats away upwards at the float speed.
     *
     * @param floatSpeed The speet at which is moves
     */
    public void floatAway(int floatSpeed)
    {
        timer-=floatSpeed*2;//subtract floatSpeed*2
        if(timer > 0)
        {
            setLocation(getX(), getY() - floatSpeed);
            image.setTransparency(timer);
            setImage(image);
        }
        else
            getWorld().removeObject(this);//remove once timer is less than 0
    }

    public void act() 
    {
        floatAway(speed);
    }    
}
