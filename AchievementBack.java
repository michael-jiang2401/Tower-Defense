import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * AchievementBack is a class that aids the hitbox of the Achievement Class by creating a transparent image that can easily detect if the mouse is hovering over it.
 * It creates a transparent image the same size as the Achievement Image
 * 
 * @author Jonathan
 * @version January 16th 2019
 */
public class AchievementBack extends Actor
{
    private GreenfootImage g;
    /**
     * Creates A new AchievementBack with a image size of the Achievement Image 
     * 
     * @param a The width of the image
     * @param b The height of the image
     */
    public AchievementBack(int a, int b)
    {
        g = new GreenfootImage(a,b); // creates a transparent greenfootImage the same size as the Achievement
        setImage(g);
    }
}
