import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * This is a gun which sticks onto the helicopter and can fire bullets
 * 
 * @author Michael Jiang
 * @version 18/01/2019
 */
public class HelicopterGun extends Actor
{
   
    private int reload = 100;
    private HeliBullet bullet;
    private HeliDisplay reloadBar=new HeliDisplay();
    private Helicopter heli;
    /**
     * creates a helicopter gun which aims towards the mouse and fires bullets
     * 
     * @param heli The helicopter which the gun sticks onto
     */
    public HelicopterGun(Helicopter heli)
    {
        this.heli=heli;
    }
    /**
     * Act - do whatever the HelicopterGun wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        aim();
        reloadBar.adjustHealth(reload);
    }    
    /**
     * Adds reload bar when helicopter is added to world
     */
    public void addedToWorld()
    {
        getWorld().addObject(reloadBar, 890, 60);
    }
    /**
     * Removes reload bar when helicopter is removed 
     */
    public void removeHB()
    {
        getWorld().removeObject(reloadBar);
    }
    /**
     * Aims the gun towards the location of the mouse and fires a bullet when space is pressed
     */
    public void aim()
    {
        setRotation(heli.getRotation());//sets rotation to that of the helicopter
        setLocation(heli.getX(),heli.getY());
        move(28);//moves the gun foward relative to the helicopter
        MouseInfo mi = Greenfoot.getMouseInfo();
        if(mi!=null)
        {

            turnTowards(mi.getX(), mi.getY());//turns gun towards mouse
        }
        if (mi!= null)
        {
            if(reload==100)//checks if cooldown for shooting is done
            {
                if(Greenfoot.isKeyDown("space"))//shoots a bullet when space is pressed
                {
                    bullet = new HeliBullet(getRotation(), ((MyWorld)getWorld()).getRound()*3000, mi.getX(), mi.getY());
                    getWorld().addObject(bullet,  getX(), getY());
                    bullet.move(20);
                    reload=0;
                }

            }

            else
            {
                reload++;
            }
        }
    }

}
