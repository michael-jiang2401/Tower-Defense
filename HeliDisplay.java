import greenfoot.*;
/**
 * This is a circular health bar created to display an integer value
 * 
 * @author Kevin Y, modified by Michael
 * @version January 19th 2019
 */ 
public class HeliDisplay extends Actor
{
    int radius = 100;//size of healthbar
    int maxMana = 100, curMana = maxMana;
    GreenfootImage heli=new GreenfootImage("heliIcon.png");
    /**
     * creates a circular value display to show the helicopter's shooting delay
     */
    public HeliDisplay()
    {
        updateImage();
        heli.scale(radius-10, radius/2);
    }
    /**
     * adjust the value in the display and updates dispaly
     * 
     * @param adjustment The new value to be displayed
     */
    public void adjustHealth(int adjustment)
    {
        curMana = adjustment;
        if (curMana < 0) curMana = 0;
        if (curMana > maxMana) curMana = maxMana;
        updateImage();
    }
    /**
     * returns the current value of the display
     * 
     * @return the value being displayed
     */
    public int getHealth()
    {
        return curMana;
    }
    /**
     * updates the image which displays the value of the bar
     * taken from danpost and modified by Kevin Y
     */
    private void updateImage()
    {
        GreenfootImage base = new GreenfootImage(radius, radius);
        base.setColor(Color.GREEN);
        if (getWorld() == null)
        {
            base.fillOval(0, 0, radius-1, radius-1);
            
        }
        else
        {
            int lastX = radius/2, lastY = 0;
            Actor actor = new Actor(){};
            getWorld().addObject(actor, getX(), getY());
            for(int i =0; i < curMana * 360 / maxMana; i++)
            {
                actor.setRotation(90 + i);
                actor.move(radius / 2 - 1);
                int xLoc = actor.getX() - getX();
                int yLoc = actor.getY() - getY();
                actor.setLocation(getX(), getY());
                int[] xCord = new int[] { radius/ 2, lastX, radius / 2 + xLoc };
                int[] yCord = new int[] { radius / 2, lastY, radius / 2 + yLoc };
                base.fillPolygon(xCord, yCord, 3);
                lastX = radius / 2 + xLoc;
                lastY = radius / 2 + yLoc;
            }
            getWorld().removeObject(actor);
        }
        base.fillRect(radius/2, radius/2, 1, radius/2);
        base.drawImage(heli, 8, 26);
        for (int i=0; i<3; i++) 
        {
            base.drawOval(i, i, radius - 1 - 2 * i, radius - 1 - 2*i);
            
        }
        base.setTransparency(150);
        setImage(base);
    }
}