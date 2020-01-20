import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * This is a button that spawns a helicopter which patrols 
 * 
 * @author Kevin B
 * @version 18/01/2019
 */
public class HelicopterButton extends ConsumableButton
{
    private boolean added; //whether or not helicopter has been added 
    private int roundsAlive;
    private GreenfootImage image;
    /**
     * Creates a helicopter button which spawns a helicopter to patrol the map
     * 
     * @param added Whether or not the helicopter has been added as only 1 helicopter can be active 
     * at any given time
     * @param roundsAlive How many rounds the helicopter has been alive
     */
    public HelicopterButton(boolean added, int roundsAlive)
    {
        image = getImage();
        cost=500;//cost of the helicopter
        this.added = added;
        this.roundsAlive = roundsAlive;
        if(added)
        {
            image = new GreenfootImage("helicopterIcon2.png");
            setImage(image);
        }
    }

    public void act() 
    {
        click();
    }    
    /**
     * Checks to see if a heliocpter has been added and if enough money is availible then adds the 
     * helicopter to the map
     */
    public void click()
    {
        if(Greenfoot.mousePressed(this) && ((MyWorld)getWorld()).getMoney() >= cost && !((MyWorld)getWorld()).getAddedHeli())
        {
            addConsumable(); //adds the helicopter to the world 
            ((MyWorld)getWorld()).loseMoney(cost);
            added = true;//sets added to true when clicked
            ((MyWorld)getWorld()).setAddedHeli(true);
            image = new GreenfootImage("helicopterIcon2.png");
            setImage(image);//sets the image 
        }
    }
    /**
     * Resets the image of the button and sets it so that another helicopter can be added
     */
    public void resetImage()
    {
        added = false;
        image = new GreenfootImage("helicopterIcon.png");
        setImage(image);
    }
    /**
     * Adds the helicopter to the world at a specified point
     */
    public void addConsumable()
    {
        Helicopter heli = new Helicopter(roundsAlive);
        getWorld().addObject(heli, 0, 100);
    }
}
