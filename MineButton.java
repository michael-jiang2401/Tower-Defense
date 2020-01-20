import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * This is a button which spawns amine which can be set on the ground and explodes when an enemy steps over it. 
 * An explosion is created which damages the enemy
 * 
 * @author Kevin Y 
 * @version 18/01/2019
 */
public class MineButton extends ConsumableButton
{
   
    
    private GreenfootImage image;
    /**
     * Creates a mine button which allows users to spawn mines when clicked
     */
    public MineButton()
    {
        image = this.getImage();
        image.scale(65 ,65);//scales the button to fit within display
        cost=100;
    }
    /**
     * Act - do whatever the AirStrike wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        click();//checks whether or not the button has been clicked
    }    
    /**
     * checks whether or not there is enough money for a mine to be purchased
     */
    public void addConsumable()
    {
        MineTarget mine = new MineTarget(cost);
        getWorld().addObject(mine, getX(), getY());//adds mine to world
    }
}
