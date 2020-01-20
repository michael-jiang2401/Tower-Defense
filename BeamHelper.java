import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * This is a hidden projectile which acts as the projectile for the beam tower
 * 
 * @author Kevin B
 * @version 1/18/2019
 */
public class BeamHelper extends Projectile
{
    private GreenfootImage image;//image of the projectile
    private boolean doesDamage;//whether or not the beam actually does damage
    private Laser laser;//laser turret which shot this bullet
    /**
     * creates a beam helper given different parameters
     * 
     * @param rotation The inital rotation of the projectile
     * @param damage The damage dealt by the projectile
     * @param tower The tower that the projectile came from
     * @param laser The laser turret which the projectile came from
     */
    public BeamHelper(int rotation, boolean damage, Laser laser, int dmg,Tower tower)
    {
        myTower = tower;//tower which shot this
        image = getImage();
        image.setTransparency(0);//makes the bullet invisible
        setImage(image);
        setRotation(rotation);//sets rotation of bullet
        this.damage = dmg;//sets damage 
        doesDamage = damage;//sets whether or not this projectile will do damage
        this.laser = laser;
    }

    public void checkAndRemove()
    {
        Enemy e = (Enemy)getOneIntersectingObject(Enemy.class);
        //increases exp of tower if this projectile kills an enemy
        if(e!=null && !e.getDead())
        {
            if(e.getHealth() - damage < 1 && e.enemyType.equals("Knight"))
            {
                myTower.increaseXP(1);
            }
            else if(e.getHealth() - damage < 1 && e.enemyType.equals("Slime"))
            {
                myTower.increaseXP(10);
            }
            else if(e.getHealth() - damage < 1 && e.enemyType.equals("Wizard"))
            {
                myTower.increaseXP(50);
            }
            else if(e.getHealth() - damage < 1 && e.enemyType.equals("Demon"))
            {
                myTower.increaseXP(200);
            }
            e.takeDamage(damage);//damages the enenmy
        }
        //removes when at edge
        if(isAtEdge())
        {
            getWorld().removeObject(this);
        }
    }

    public void act() 
    {
        if(doesDamage){
            move(50);
            checkAndRemove();
        }
        else
        {
            move(10);
            Enemy e = (Enemy)getOneIntersectingObject(Enemy.class);
            if(e != null)
            {
                laser.setShoot(true);
            }
            if(isAtEdge())
            {
                getWorld().removeObject(this);
            }
        }
    }    
}
