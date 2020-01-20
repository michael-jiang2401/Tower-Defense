import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Housing for the selectable towers and consumables with cost prices underneath.
 * 
 * @author Kevin Biro
 * @version January 16th 2019
 */
public class TowerBar extends Actor
{
    private GreenfootImage image;
    /**
     * Builds the towerbar with cost prices at the bottow depending if it is showing 
     * towers or consumables
     * 
     * @param tower Whether or not towers are currently showing.
     */
    public TowerBar(boolean tower)
    {
        if(tower)
        {
            image = getImage();
            image.drawString("$500", 195, 75);
            image.drawString("$100", 285, 75);
            image.drawString("$500", 375, 75);
            image.drawString("$100", 465, 75);
            image.drawString("$1500", 550, 75);
            image.drawString("$100", 645, 75);
            image.drawString("$10", 735, 75);
        }
        else
        {
            image = getImage();
            image.drawString("$100", 330, 79);
            image.drawString("$100", 420, 79);
            image.drawString("$100", 505, 79);
            image.drawString("$500", 600, 79);
        }
    }
}
