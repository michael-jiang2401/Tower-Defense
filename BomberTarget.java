import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Shows the path that the bomber will take before placing it
 * 
 * @author Michael J
 * @version January 16th 2019
 */
public class BomberTarget extends Consumable
{
    protected int cost;
    private GreenfootImage image; 
    private int timer=150;

    /**
     * Constructs the BomberTarget with a set cost
     *
     * @param cost The cost of the bomber
     */
    public BomberTarget(int cost)
    {
        this.cost = cost;
        image=this.getImage();
        image.setTransparency(timer);
    }

    /**
     * The click and drop functionality of the bomberTarget
     *
     */
    public void clickAndDrop()
    {
        if(clicked)
        {
            MouseInfo mouse = Greenfoot.getMouseInfo();
            setLocation(480, ((int)mouse.getY()/40)*40+20);
        }
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
            fade();//fade away when clicked
        }
        if(clicked && Greenfoot.isKeyDown("ESCAPE"))//remove if user presses ESCAPE
        {
            ((MyWorld)getWorld()).addMoney(cost);
            getWorld().removeObject(this);
        }
    }    

    /**
     * Fades the bomber target when clicked by changing the transparency
     *
     */
    public void fade()
    {
        timer-=2;
        image.setTransparency(timer);
        setImage(image);
        if(timer < 2)//add the bomber when faded
        {
            Bomber bomber = new Bomber();
            getWorld().addObject(bomber, 0, getY());
            ((MyWorld)getWorld()).addBomberCounter();
            getWorld().removeObject(this);
        }
    }
}
