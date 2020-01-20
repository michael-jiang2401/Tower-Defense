import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * This is a button which spawns an bomber which drops bombs affecting 3 rows 
 * 
 * @author Michael 
 * @version 18/01/2019
 */
public class BomberButton extends ConsumableButton
{
    
    private GreenfootImage image;
    public BomberButton()
    {
        image = this.getImage();
        image.scale(65 ,65);//resizes the button
        cost=100;//sets cost
    }
    /**
     * Act - do whatever the BomberButton wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        click();//checks whether or not this has been clicked
    }    
    /**
     * Adds the target path of the bomber to the world
     */
    public void addConsumable()
    {
        BomberTarget target = new BomberTarget(cost);//adds bomber target to world
        getWorld().addObject(target, getX(), getY());
    }
}
