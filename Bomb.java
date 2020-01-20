import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * This is bomb dropped by the bomber that shrinks in size and eventually explodes
 * 
 * @author Michael 
 * @version 1/18/2019
 */
public class Bomb extends Projectile
{
    private int timer=80;//how long the bomb drops for
    private GreenfootImage image;//image of explosion
    private GreenfootSound bomb = new GreenfootSound ("bombDrop.mp3");//sound effect
    private boolean playing;
    /**
     * Creates a bomb which drops and explodes
     */
    public Bomb()
    {
        image=getImage();
        playing = false;
    }

    public void act() 
    {
        drop();
    }    
    /**
     * Manages the bomb drop animation as well as when it should explode
     */
    public void drop()
    {
        if(!playing)//checks whether the sound is playing and plays sound
        {
            if(!((MyWorld)getWorld()).getMuted())
            {
                bomb.setVolume(40);
                bomb.play();
                playing=true;
            }
        }
        if(timer==0)//explodes once timer reaches 0
        {
            getWorld().addObject(new Explosion(((MyWorld)getWorld()).getRound()*300), getX(), getY());
            getWorld().removeObject(this);//removes bomb
        }
        //scales the image to be smaller every 5 acts
        if(timer%5==0)
        {
            image.scale((int)(image.getWidth()*.95) , (int)(image.getHeight()*.95));
        }
        timer--;
    }
}
