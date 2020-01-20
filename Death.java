import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
 
/**
 * This is an animation for the death of a certain enemies
 * 
 * @author Kevin B
 * @version 1/18/2019
 */
public class Death extends Projectile
{
    //sound effects for animation
    private GreenfootSound elec = new GreenfootSound ("electricity.wav");
    private GreenfootSound slime = new GreenfootSound ("Slime.wav");
    private GreenfootSound fire = new GreenfootSound ("fireBall.wav");
    private GreenfootImage image;//image variable
    private int frame=1;//initial frame animation
    private boolean playing,electricity,fireB;//different types of death animations
    /**
     * creates a death animation
     * 
     * @param electricity Whether or not electricity sound will play
     * @param fire Whether or not fire sound will play
     */
    public Death(boolean electricity,boolean fire)
    {
        fireB = fire;
        this.electricity = electricity;
        playing = false;
    }
    /**
     * Act - do whatever the Explosion wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        animate();
    }   
    /**
     * plays the different death animations based on what type of animation should be played
     */
    public void animate()
    {
        //plays sound effects if not already playing and should be played
        if(!playing)
        {
            if(!((MyWorld)getWorld()).getMuted())
            {
                //elec.setVolume(40);
                if(electricity)
                    elec.play();
                else if(fireB)
                    fire.play();
                else
                    slime.play();
                playing=true;
            }
        }
        //plays death animation with electricity
        if(electricity)
        {
            //animates every 3 acts
            if(frame%3==0)
            {  
                image = new GreenfootImage("death (" + frame/3 + ").gif");
                image.scale((int)(image.getWidth()*0.5) , (int)(image.getHeight()*0.5));
                setImage(image);
            }
            frame++;
            //removes on the 24 act
            if(frame==24)
            {
                getWorld().removeObject(this);
            }
        }
        //plays fire explosion death effect
        else if(fireB)
        {
            //animtes every 3 frames
            if(frame%3==0)
            {  
                image = new GreenfootImage("fireExplosion (" + frame/3 + ").gif");
                image.scale((int)(image.getWidth()*0.3) , (int)(image.getHeight()*0.3));
                setImage(image);
            }
            frame++;
            //removes on 78th frame
            if(frame==78)
            {
                getWorld().removeObject(this);
            }
        }
        //plays slime explosion animation
        else
        {
            if(frame%3==0)
            {  
                image = new GreenfootImage("slimeExplosion (" + frame/3 + ").gif");
                image.scale((int)(image.getWidth()*0.5) , (int)(image.getHeight()*0.5));
                setImage(image);
            }
            frame++;
            if(frame==63)
            {
                getWorld().removeObject(this);
            }
        }
    }
}
