import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Frost projectile shot by the frost tower
 * 
 * @author Kevin Y
 * @version January 16th 2019
 */
public class FrostBeam extends Projectile
{
    private int timer;
    private int imageNum;
    private int slow,duration;
    private GreenfootImage image;
    /**
     * Creates a frost given different parameters
     * 
     * @param rotation The inital rotation of the projectile
     * @param damage The damage dealt by the projectile
     * @param tower The tower that the projectile came from
     */
    public FrostBeam(int rotation,int damage,Tower tower)
    {
        myTower = tower;
        setRotation(rotation);
        slow=damage;
        duration = 155+damage*(155/5);
        imageNum = Greenfoot.getRandomNumber(5)+1;
        setImage("frost"+imageNum+".png");
        image = getImage();
    }

    private void setTransparency()
    {
        image.setTransparency(image.getTransparency()-6);
    }

    public void act() 
    {
        move(4);
        setTransparency();
        checkAndRemove();
    }   

    /**
     * checks whether this projectile is touching an enemy or is at the edge of the world
     */
    public void checkAndRemove()
    {
        try
        {
            timer++;
            Enemy e = (Enemy)getOneIntersectingObject(Enemy.class);
            if(e!=null)
            {
                e.changeSpeed(slow, duration);
            }
            if(timer==28)
            {
                removeMe= true;
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
        catch(IllegalStateException e)
        {}
    }
}
