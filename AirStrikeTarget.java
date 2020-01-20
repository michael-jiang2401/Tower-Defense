import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Crosshairs that help the user target the airstrike before using it.
 * 
 * @author Michael J
 * @version January 16th 2019
 */
public class AirStrikeTarget extends Consumable
{
    private int cost;
    private int timer;
    private GreenfootImage image;
    private boolean isPlaying=false;
    private GreenfootSound jet = new GreenfootSound ("jet.mp3");

    /**
     * Constructs the AirStrike target with a set cost
     *
     * @param cost The cost of the AirStrikeTarget
     */
    public AirStrikeTarget(int cost)
    {
        this.cost = cost;
        jet.setVolume(50);
        image=getImage();
        timer=image.getTransparency();
        setImage(image);
    }

    public void act() 
    {

        if(clicked)
        {
            clickAndDrop();
            click();
        }
        else
        {
            if(!isPlaying && !((MyWorld)getWorld()).getMuted())
            {
                jet.play();
                isPlaying=true;
            }
            fade();//fade when clicked
        }
        if(clicked && Greenfoot.isKeyDown("ESCAPE"))//is user presses ESCAPE, removeObject
        {
            ((MyWorld)getWorld()).addMoney(cost);
            getWorld().removeObject(this);
        }
    }   

    /**
     * Fades the airstrike target when clicked by changing the transparency
     *
     */
    public void fade()
    {
        timer-=2;
        image.setTransparency(timer);
        setImage(image);
        if(timer < 2)
        {
            AirStrike airStrike = new AirStrike(getX());
            getWorld().addObject(airStrike, 0, getY());
            ((MyWorld)getWorld()).addAirStrikeCounter();
            getWorld().removeObject(this);
        }
    }
}
