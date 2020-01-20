import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A single 1x1 block that is used to adjust the enemy path.
 * 
 * @author Kevin Biro
 * @version January 16th 2019
 */
public class Wall extends Tower
{
    /**
     *Constructs a wall with a set cost and type.
     *
     *@param towerType The type of the tower
     *@param canDrag Whether or not the tower can be dragged
     *@param valid Whether or not the location of the tower is valid.
     */
    public Wall(int towerType, boolean canDrag, boolean valid)
    {
        //initialize variables
        towerCost = 10;
        trueTowerValue = towerCost;
        towerValue = towerCost * 2/3;

        this.towerType = towerType;
        isLocationValid = valid;
        this.canDrag = canDrag;
        range = 50;
    }

    /**
     *Mutes the sounds that are playing
     */
    public void muteSounds(){}

    public void act() 
    {
        dragAndDrop(20,1);//drag and drop functionality
    }   

    /**
     *Makes a copy of the wall
     */
    protected void makeCopy()
    {
        getWorld().addObject(new Wall(towerType, true,true),getX(),getY());
    }

}