import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * This class is the bullet created by the minigun tower
 * 
 * @author Michael Jiang
 * @version 1/18/2019
 */
public class MinigunBullet extends Projectile
{
    private GreenfootSound minigun = new GreenfootSound ("minigun.wav");//sound played when minigun shoots
    private boolean playing; //whether or not the sound is shooting
    /**
     * Creates a minigun bullet
     * 
     * @param rotation The direction that the bullet has to face
     * @param damage The damage dealt by this bullet
     * @param tower The tower which created this bullet
     */
    public MinigunBullet(int rotation, int damage,Tower tower)
    {
        setRotation(rotation);//sets rotation to that of the tower
        this.damage = damage;
        playing = false;
        myTower = tower;
        speed=20;
    }
    /**
     * Act - do whatever the MinigunBullet wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if(!playing)//only plays minigun sound effect if it is not already playing
        {
            if(!((MyWorld)getWorld()).getMuted())
            {
                minigun.setVolume(70);
                minigun.play();
                playing = true;
            }
        }
        move(speed);//moves the bullet
        checkAndRemove();//checks if the bullet has hit a target or reached the edge of the world
    }    
}
