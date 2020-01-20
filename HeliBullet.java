import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Helicopter bullet class that is shot by the helicopter consumable
 * 
 * @author Michael Jiang
 * @version 1/18/2019
 */
public class HeliBullet extends Projectile
{
    /**
     * Act - do whatever the heliBullet wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private int xLoc, yLoc;//x and y coordinates of the explosion to be created 
    /**
     * creates a helicopter bullet given different parameters
     * 
     * @param rotation The inital rotation of the projectile
     * @param damage The damage dealt by the projectile
     * @param xLoc The x coordinate of the explosion
     * @param yLoc the y coordinate of the explosion
     */
    public HeliBullet(int rotation, int damage, int xLoc, int yLoc)
    {
        setRotation(rotation);//sets rotation of the bullet
        this.damage = damage;//sets damage
        this.xLoc=xLoc;//sets x and y locations of target
        this.yLoc=yLoc;
        speed=20;
    }

    public void act() 
    {
        move(speed);
        checkAndRemove();
    }    
    /**
     * Method which checks and removes the helicopter bullet
     */
    protected void checkAndRemove()
    {
        //checks if the bullet is within an area around where the target is
        if((xLoc-30)<getX() && (xLoc+30)>getX() && (yLoc-30)<getY() && (yLoc+30)>getY())
        {
            //creates an explosion at target location when the bullet is within an area around it
            Explosion explosion = new Explosion(damage, 100);
            getWorld().addObject(explosion, xLoc, yLoc);
            removeMe=true;
        }
        //designates for removal when at edge
        if(isAtEdge())
        {
            removeMe = true;
        }
        //removes the bullet
        if(removeMe)
        {
            getWorld().removeObject(this);
        }
    }
}
