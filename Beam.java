import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * This is the visual effect created by the beam turret when it shoots
 * 
 * @author Michael, Kevin B 
 * @version 1/18/2019
 */
public class Beam extends Projectile
{
    private GreenfootSound laser = new GreenfootSound ("laser.wav");//sound effect of laser
    private int frame = 1; //inital frame
    private int animationTimer=0;//delay between frames
    private boolean playing;//whether sound is playing
    private int damage;//damage
    private Tower tower;//tower that the projectile came from
    /**
     * creates a Beam given different parameters
     * 
     * @param rotation The inital rotation of the projectile
     * @param damage The damage dealt by the projectile
     * @param tower The tower that the projectile came from
     */
    public Beam(int rotation,int damage,Tower tower)
    {
        //sets
        this.tower = tower;
        setRotation(rotation);
        playing = false;
        this.damage = damage;
    }

    public void act() 
    {
        if(!playing)
        {
            if(!((MyWorld)getWorld()).getMuted())
            {
                laser.setVolume(80);
                laser.play();
            }
            playing = true;
        }
        animate();

    }  

    /**
     * Animates the beam and spawns beam helpers to damage enemies
     */
    public void animate()
    {
        if(animationTimer!=0)
        {
            animationTimer--;
            if(frame < 7)
                getWorld().addObject(new BeamHelper(getRotation(), true,null,damage,tower), getX(), getY());
        }
        else
        {
            animationTimer=3;
            setImage("Beam" + frame + ".png");
            frame++;
        }
        if(frame==13)
        {
            getWorld().removeObject(this);
        }
    }
}
