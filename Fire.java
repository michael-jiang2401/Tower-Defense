import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
 
/**
 * Fire proejctile shot by the flamethrower turret
 * 
 * @author Michael 
 * @version 1/18/2019
 */
public class Fire extends Projectile
{
    private int timer;
    private int imageNum;//image number of the flame
    private GreenfootImage image;
    /**
     * creates a fire given different parameters
     * 
     * @param rotation The inital rotation of the projectile
     * @param damage The damage dealt by the projectile
     * @param tower The tower that the projectile came from
     */
    public Fire(int rotation, int damage, Tower tower)
    {
        myTower = tower;
        setRotation(rotation);
        this.damage=damage;
        imageNum = Greenfoot.getRandomNumber(5)+1;
        setImage("fire"+imageNum+".png");//sets image to any of the 5 possible fire images
        image = getImage();
    }
    //increases transparceny every act
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
            //gains exp for turret if enemy is killed
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
                e.takeDamage(damage); //causes enemies to take damage if they touch the fires
            }
            //removes object after 28 acts
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
