import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
/**
 * Achievement is a class that keeps track of the achievements that the player has accomplished throughout the game.
 * It changes the colour of the achievement image to differeniate if the player has attained it or not
 * 
 * @author Kevin Y 
 * @version January 16th 2019
 */
public class Achievement extends Actor
{
    private boolean unlocked; // to check if the player has unlocked the achievement
    private int type; // the specific achievement based on the achievementList array in the world
    private GreenfootImage image; // the image of the achievement
    private GreenfootImage backImage;
    private AchievementTag aT; // the acheivementTag class linked to the achievement
    private AchievementBack aB;  // the acheivementBack class linked to the achievement
    private boolean Spawn = false; // whether the achievement has been spawned or not
    /**
     * Creates a new Achievement which displays whether the specific achievement has been unlocked or not
     * 
     * @param unlocked  boolean to check if the achievement has been unlocked
     * @param type      int corresponding to the specific achievement
     */
    public Achievement(boolean unlocked, int type)
    {
        image = new GreenfootImage("Achievement ("+type+").png");

        checkscale(); //scales image
        setImage(image);
        this.unlocked = unlocked;
        this.type = type;
        aT = new AchievementTag(type); //makes a new AcheivementTag with the specific tag to the Achievement
        aB = new AchievementBack(image.getHeight(), image.getWidth()); // makes a new AchievementBack with an identical image size to this image
        checkunlocked(); // checks if the achievement has been unlocked
    }  

    /**
     * checks whether the achievement has been unlocked and turns the image grey if it has not been yet
     */
    private void checkunlocked()
    {
        if (!unlocked)
        {
            Processor.greyScale(image.getAwtImage());
        }
    }

    /**
     * checks whether the image imported is too wide and scales it down to 100 by 100 pixels if it is
     */
    private void checkscale()
    {
        if (image.getWidth()>100) //if image is too big
        {
            image.scale(100,100);
        }
    }

    public void act()
    {
        spawn(); //spawns the AchievementTag and AchievementBack
        MouseInfo mouse = Greenfoot.getMouseInfo();
        if(Greenfoot.mouseMoved(aB)) // checks to see if the mouse if hovering over the AchievementBack
        {
            aT.changeTransparency(220); // sets the AchievementTag transparency so it is visible

        }
        else if(Greenfoot.mouseMoved(null) && ! Greenfoot.mouseMoved(aB))
        {
            aT.changeTransparency(0); // sets the AchievementTag transparency to 0 when the mouse is no longer hovering over it
        }
    }

    /**
     * spawns the AchievementTag and AcheivementBack onto the location of the Achievement only when the Achievment has been added to the world
     */
    private void spawn()
    {
        if (!Spawn)
        {
            getWorld().addObject(aT, getX(), getY()); //adds it to the Achievements location
            getWorld().addObject(aB, getX(), getY());
            Spawn = true; // prevents multiple spawnings of objects
        }
    }
}
