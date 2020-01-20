import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Fire projectile class created by the ring turret 
 * 
 * @author Kevin Y
 * @version 1/18/2019
 */
public class FireProjectile extends Projectile
{
    int travelTime;//how long the fire travels
    /**
     * creates a fire projectile given different parameters
     * 
     * @param rotation The inital rotation of the projectile
     * @param decay How long the projectile will decay for
     * @param tower The tower that the projectile came from
     */
    public FireProjectile(int rotation,int decay,Tower tower)
    {
        myTower = tower;//tower which shot this
        setRotation(rotation);//rotation of projectile
        travelTime = decay;//how long this projectile decays fire
        damage = tower.getDamage();//damage of tower
        speed=10;
    }
    public void act() 
    {
        move(speed);
        travelTime--;
        //removes the projectile after a specified amount of acts
        if (travelTime < 0)
        {
            removeMe = true;
        }
        else if(isAtEdge())
        {
            removeMe = true;
        }
        checkAndRemove();
        //deals damage or removes object when at edge
    }      
}
