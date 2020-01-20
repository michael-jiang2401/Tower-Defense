import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The background of the achievements page.
 * 
 * @author Kevin Y
 * @version January 16th 2019
 */
public class AchievementBackground extends Actor
{
    /**
     * Scales the image to the size of the game screen
     */
    public AchievementBackground()
    {
        GreenfootImage image = getImage();
        image.scale(960, 640);
        setImage(image);
    }
}
