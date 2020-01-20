import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * This is a button which allows users to select and use consumables
 * 
 * @author Kevin B, Michael
 * @version 18/01/2019
 */
public abstract class ConsumableButton extends Actor
{
    
    protected int cost;//the cost of the consumable
    boolean clicked = false;//whether or not the button has been clicked once
    /**
     * abstract method to add the consumable
     */
    protected abstract void addConsumable();//abstract method to add the consumable
    /**
     * Checks the user click on the button and adds a consumable
     */
    protected void click()
    {
        if(Greenfoot.mousePressed(this) && ((MyWorld)getWorld()).getMoney() >= cost)
        {
            addConsumable();//adds consumble if there is enough money
            ((MyWorld)getWorld()).loseMoney(cost);
        }
    }
    
}
