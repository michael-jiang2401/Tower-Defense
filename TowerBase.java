
import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * This class is the base of the tower that can create any Tower in the game. A TowerBase has 
 * a tower type, a number of upgrades and an exp value. When creating a tower, the variables used 
 * to create it are passed on to the tower.
 * 
 * @author Kevin Biro
 * @version January 16th 2019
 */
public class TowerBase extends Actor
{
    private int towerType, num, numUpgrades,xp;
    private Tower tower;
    private boolean canDrag, valid;
    /**
     * Creates a tower base with a specified type, number of upgrades and exp.
     * 
     * @param towerType The type of the tower that will get created on spawn
     * @param canDrag Whether or not the tower created can be dragged
     * @param valid Whether or not the tower placement is valid
     * @param xp The current exp of the tower when created.
     */
    public TowerBase(int towerType, boolean canDrag, boolean valid, int num, int xp)
    {
        this.towerType = towerType;
        this.canDrag = canDrag;
        this.valid = valid;
        this.numUpgrades = num;
        this.xp = xp;
    }

    /**
     * Returns whether or not the towerbase is touching the towerBar
     * 
     * @return boolean Whether or not the towerbase is touching the towerBar
     */
    public boolean getTouchingBar()
    {
        if(isTouching(TowerBar.class))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * When the towerBase is added to the world, it creates a tower based on its tower type. 
     * The variables used to construct the towerbase are used to construct the tower
     * 
     * @param w The world that the tower is being added to
     */
    public void addedToWorld(World w)
    {
        if(towerType == 0)
        {
            tower = new Obstacle();
        }
        if(towerType==1)
        {
            tower = new Turret(towerType,canDrag,valid,numUpgrades,xp);
        }
        else if(towerType==2)
        {
            tower = new Wall(towerType, canDrag,valid);
        }
        if(towerType == 3)
        {
            tower = new RingTurret(towerType,canDrag,valid,numUpgrades,xp);
        }
        else if(towerType==4)
        {
            tower = new Laser(towerType, canDrag,valid,numUpgrades,xp);
        }
        else if(towerType==5)
        {
            tower = new Flame(towerType, canDrag, valid,numUpgrades,xp);
        }
        else if(towerType==6)
        {
            tower = new Frost(towerType, canDrag, valid,numUpgrades,xp);
        }
        else if(towerType==7)
        {
            tower = new Minigun(towerType, canDrag, valid,numUpgrades,xp);
        }
        if(towerType != 2)
            getWorld().addObject(tower, this.getX(), this.getY());
    }

    /**
     * Removes the towerbase and the tower from the world
     */
    public void remove()
    {
        getWorld().removeObject(tower);
        getWorld().removeObject(this);
    }

    /**
     * Checks if the tower is in a valid location when let go and removes it from the world if not.
     */
    public void checkAndRemove()
    {
        if(tower.getCanDrag())
            setLocation(tower.getX(), tower.getY());
        if(!tower.getValidity() && !tower.getCanDrag())
            getWorld().removeObject(this);
    }

    /**
     * Returns the reference to the tower
     * 
     * @return Tower The tower of the towerBase
     */
    public Tower getTower()
    {
        return tower;
    }
    
    /**
     * Constantly checks if its location is valid in the world and removes the towerbase if not.
     */
    public void act() 
    {
        try
        {
            checkAndRemove();
        }
        catch(IllegalStateException e)
        {
            getWorld().removeObject(this);
        }
    }    
}
