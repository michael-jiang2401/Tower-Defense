import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A mine which explodes when an enemy travels on top of it
 * 
 * @author Kevin Y
 * @version 1/18/2019
 */
public class Mine extends Projectile
{


    public void act() 
    {
        Enemy e = (Enemy)getOneIntersectingObject(Enemy.class);
        if(e!=null)//checks if touching enemy
        {
            Explosion explosion = new Explosion((((MyWorld)getWorld()).getRound()*500));//creates an explosion 
            getWorld().addObject(explosion, getX(), getY());
            getWorld().removeObject(this);//removes the mine
        }
    }   

}
