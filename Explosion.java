import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * This class is a customizable explosion which deals damage to enemies.
 * 
 * @author Michael Jiang    
 * @version 1/18/2019
 */
public class Explosion extends Projectile
{
    private GreenfootSound exp = new GreenfootSound ("explosion.mp3"); //explosion sound effect
    private GreenfootImage image;//image of the explosion
    private int frame=1;//current frame of the explosion
    private int damage;//damage that the explosion will deal
    private boolean playing;//whether or not the sound is playing
    private int scale=0;//scale
    /**
     * Creates a standard explosion which deals a specified amount of damage each frame
     * 
     * @param damage The amount of damage that specific frames which deal to enemies
     */
    public Explosion(int damage)
    {
        image= new GreenfootImage(getImage()); //sets image to current image of the explosion
        this.damage=damage;//sets damage value 
        playing = false;//sets sound playing to false
        frame=1;//sets frame counter to first frame
    }
    /**
     * Creates a custom explosion which deals a specified amount of damage with a different image scale
     */
    public Explosion(int damage, int scale)
    {
        image= new GreenfootImage(getImage());//sets image to current image
        this.damage=damage;//sets damage value
        this.scale=scale;//sets scale value
        image.scale(scale , scale);//scales the first frame
        setImage(image);//sets image to new image
        playing = false;//sets sound playing to false
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
     * Manages the animation and damage of the explosion
     */
    public void animate()
    {
        if(!playing)//plays the sound if it is not already playing
        {
            if(!((MyWorld)getWorld()).getMuted())
            {
                exp.setVolume(40);
                exp.play();
                playing=true;
            }
        }
        //cycles to the next frame every 5 acts
        if(frame%5==0)
        {  
            image = new GreenfootImage("explosion" + frame/5 + ".png");//sets image to next image
            if(scale!=0)//does not scale the image if the not scale value is inputed
            {
                image.scale(scale, scale);
            }
            else//scales x and y sizes of the image by the default scaling values if no scaling value is inputed
            {
                image.scale((int)(image.getWidth()*1.5), (int)(image.getHeight()*1.5));
            }
            setImage(image);//sets the displayed image
            
        }
        //allows explosion to deal damage on the 15th frame
        if(frame==15)
        {
            Enemy e = (Enemy)getOneIntersectingObject(Enemy.class);
            if(e!=null)
            {
                e.takeDamage(damage);//causes the enemy to take damage
            }
        }
        frame++;
        if(frame==55)
        {
            getWorld().removeObject(this);//removes the explosion when frame reaches 55
        }
    }
}
