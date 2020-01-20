import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * AchievementTag is a class that displays a textbox that tells the player what type of achievement the mouse is hovering over.
 * It creates a white box with black text and changes transparency depending if the mouse is hovering over the AchievementBack class.
 * 
 * @author Jonathan
 * @version January 19th 2019
 */
public class AchievementTag extends Actor
{
    private int type; // the specific achievement
    private GreenfootImage myImage; //image of the object
    private String[] theArray = new String[] {"Beat Level 1", "Beat Level 2", "Beat Level 3"
        , "Reach Endless Level 25", "Reach Endless Level 50","Reach Endless Level 75","Reach Endless Level 100","Accumulate $100,000", "Earn 9999 XP On A Tower", "Beat Level 1 Deathless"
        , "Beat Level 2 Deathless","Beat Level 3 Deathless", "Use Airstrike 10 Times", "Use Bomber 10 Times", "Place Mine 10 Times", "Kill 1000 Knights", "Kill 100 Dragons","Kill 20 Demons"
        ,"Kill 100 Slimes", "Kill 100 Wizards", "Finish The Game!", "Cheat!" , "Beat Endless"}; //array to store the words that will display on the textbox
    private String text2; //the string that contains the specific words to be displayed
    /**
     * Creates a new AchievementTag that shows the description of the Acheivement
     * 
     * @param type      the specific achievement in an integer according the Acheivement List array in the world
     */
    public AchievementTag(int type)
    {
        this.type = type; //sets type of achievement
        text2 = theArray[type]; // sets the specific words to the achievement
        GreenfootImage tempTextImage = new GreenfootImage (text2, 20, Color.BLACK, null); // creates a new image with the text
        myImage = new GreenfootImage (tempTextImage.getWidth() + 12, tempTextImage.getHeight() + 30); //creates a new Image longer than tempTextImage so the words will not be at the edge
        myImage.setColor (Color.WHITE); //sets colour to white
        myImage.fill(); // fills the image with white
        myImage.drawImage (tempTextImage, 6, 15); // draws tempTextImage over hte box
        myImage.setColor (Color.BLACK);
        myImage.setTransparency(0); //sets the image transparency to 0 so it will no be visible when it gets spawn
        setImage(myImage);
    }

    /**
     * changes the Transparency of the image to a specific integer
     * 
     * @param a The change in transparency
     */
    public void changeTransparency(int a)
    {
        myImage.setTransparency(a);
        setImage(myImage);
    }
}
