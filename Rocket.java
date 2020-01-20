import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Rocket projectile shot by the rocket turret
 * 
 * @author Kevin B
 * @version 1/18/2018
 */
public class Rocket extends Projectile
{
    private GreenfootSound shot = new GreenfootSound ("shot.wav");//sound effect of shot
    private boolean playing;//whether sound is playing
    /**
     * creates a rocket given different parameters
     * 
     * @param rotation The inital rotation of the projectile
     * @param damage The damage dealt by the projectile
     * @param tower The tower that the projectile came from
     */
    public Rocket(int rotation, int damage,Tower tower)
    {
        playing = false;
        myTower = tower;//the tower this bullet came from
        speed = 15;//speed of projectile
        setRotation(rotation);//rotation of projectile
        this.damage = damage;//damage
    }

    public void act() 
    {
        if(!playing)//plays sound if not playing
        {
            if(!((MyWorld)getWorld()).getMuted())
            {
                shot.setVolume(75);
                shot.play();
                playing = true;
            }
        }
        move(speed);
        checkAndRemove();//checks and removes object if touching enemy or at edge
    }    
}
