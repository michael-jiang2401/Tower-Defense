import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Shows a mine that follows the mouse to help place it.
 * 
 * @author Michael J, Kevin Y
 * @version January 16th 2019
 */
public class MineTarget extends Consumable
{
    private int cost;
    private int timer;
    private boolean isPlaying=false;

    /**
     * Constructs a MineTarget with a set cost
     *
     * @param cost The cost of the Mine
     */
    public MineTarget(int cost)
    {
        this.cost = cost;
    }

    /**
     * Checks if the mouse is pressed.
     *
     */
    public void click()
    {
        if(Greenfoot.mousePressed(null)&& clicked&& isTouching(PathBall.class))
        {
            clicked=false;
        }
    }

    public void act() 
    {
        if(clicked)
        {
            clickAndDrop();
            click();
        }
        else//if clicked on the path, add a mine
        {
            if(!isPlaying)
            {
                isPlaying=true;
            }
            Mine mine = new Mine();
            getWorld().addObject(mine, getX(), getY());
            ((MyWorld)getWorld()).addMineCounter();
            getWorld().removeObject(this);
        }
        if(clicked && Greenfoot.isKeyDown("ESCAPE"))//if clicked ESCAPE remove the MineTarget
        {
            ((MyWorld)getWorld()).addMoney(cost);
            getWorld().removeObject(this);
        }
    }   

}
