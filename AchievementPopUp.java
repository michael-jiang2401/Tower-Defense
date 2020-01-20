import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * AchievementPopUp is a class that tells the player that he/she has unlocked an achievement.
 * The class displays words that tell the player what the achievement is and slowly fades away
 * 
 * @author Jonathan
 * @version January 19th 2019
 */
public class AchievementPopUp extends Actor
{
    private GreenfootImage image;
    private int counter; //counter to keep track of the number of acts that passed
    /**
     * Creates a new AchievementPopUp with the string that it wants to display
     * 
     * @param display   A string that displays what acheivement the player has unlocked
     */
    public AchievementPopUp(String display)
    {
        image = new GreenfootImage("AchievementPopUp.png"); // creates a new image with a template
        GreenfootImage text = new GreenfootImage(display, 20 , Color.WHITE, null); // makes a new image with the specific text
        image.drawImage(text, 65 , 32); // draws the text onto the image template
        image.setTransparency(100); // sets the image transparency to 100
        setImage(image);
    }

    public void act()
    {
        counter ++;
        if (counter < 5) // in 5 acts, moves this object down to achieve a sliding down effect
        {
            setLocation(getX(), getY() + 10); // moves the object down 10 pixels every act
            image.setTransparency(image.getTransparency()+30); // increases transparency to make it appear out of nowhere
            setImage(image);
        }
        if (counter > 180 && counter < 680)
        {
            if (counter % 2 ==0) // prevents it from happeening every act
            {
                image.setTransparency(image.getTransparency()-1); // slowly decreases the transparency to achieve a fading effect
                setImage(image);
            }
        }
        if (image.getTransparency() < 5) // removes the object if the transparency is low enough
        {
            getWorld().removeObject(this);
        }
    }
}
