import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * This is the abstract parent class of all projectiles in the game. Projectiles 
 * have speed and damage values, and may have an associated tower.
 * 
 * @author Kevin B, Kevin Y, Michael
 * @version 1/18/2019
 */
public abstract class Projectile extends Actor
{
    protected int damage,speed; //damage and speed values of the projectile
    protected boolean removeMe; //whether or not the projectile needs to be removed
    protected Tower myTower; //tower which created the projectile
    /**
     * Checks if the projectile has collided with an enemy. If the projectile 
     * contacts an enemy, the enemy takes damage and the projectile is removed.
     * Also removes the projectile if it is at the edge of the world.
     */
    protected void checkAndRemove()
    {
        Enemy e = (Enemy)getOneIntersectingObject(Enemy.class);
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
            e.takeDamage(damage);
            removeMe = true;
        }
        if(isAtEdge())
        {
            removeMe = true;
        }
        if(removeMe)
        {
            getWorld().removeObject(this);
        }
    }
}
