import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.io.IOException;
/**
 * Button can be clicked on to do a specific thing. Buttons have two images, it
 * changes when hovering over the button.
 * 
 * @author Kevin Biro
 * @version January 16th 2019
 */
public class Button extends Actor
{
    private GreenfootSound button = new GreenfootSound ("button.wav");
    private GreenfootImage image;
    private String image1,image2;

    /**
     * Constructs the button with two images
     * 
     * @param img1 The first image that is always shown
     * @param img2 The second image that is shown when hovering over it
     */
    public Button(String img1,String img2)
    {
        update(img1,img2);
    }

    /**
     * Updates the images
     *
     * @param img1 The first image that is always shown
     * @param img2 The second image that is shown when hovering over it
     */
    public void update(String img1,String img2)
    {
        image1 = img1;
        image2 = img2;
        image = new GreenfootImage(img1);
        setImage(image);
    }

    /**
     * Resets the level when clicked
     *
     */
    public void resetLevel()
    {
        ((MyWorld)getWorld()).resetLevel();
    }

    /**
     * Resumes the game when clicked
     */
    public void playGame()
    {
        ((MyWorld)getWorld()).setGameOver(false);
        ((MyWorld)getWorld()).setMenuOpen(false);
    }

    /**
     * Saves the game when clicked
     */
    public void saveGame()
    {
        try
        {
            Save.saveFile(((MyWorld)getWorld()).getCopy(), ((MyWorld)getWorld()));
        }
        catch(IOException e)
        {
            System.out.println("Error saving game");
        }
        //Save.saveHighscore(((MyWorld)getWorld()));
    }

    /**
     * Mutes the game and changes the images to show that it is muted and vice versa
     */
    public void muteGame()
    {
        if(((MyWorld)getWorld()).getMuted())
        {
            image1 = "MuteButton.png";
            image2 = "MuteButton2.png";
            image = new GreenfootImage(image1);
            setImage(image);
            ((MyWorld)getWorld()).setMuted(false);
        }
        else
        {
            image1 = "UnMuteButton.png";
            image2 = "UnMuteButton2.png";
            image = new GreenfootImage(image1);
            setImage(image);
            ((MyWorld)getWorld()).setMuted(true);
        }
    }

    /**
     * Quits the game and goes to the menu
     */
    public void goToMenu()
    {
        ((MyWorld)getWorld()).goToMenu();
    }

    /**
     * Checks mouse hover and changes the image to show depth
     *
     * @param img1 The first image that is always shown
     * @param img2 The second image that is shown when hovering over it
     */
    public void checkHover(String img1, String img2)
    {
        MouseInfo mouse = Greenfoot.getMouseInfo();
        if(Greenfoot.mouseMoved(this))
        {
            image = new GreenfootImage(img2);
            setImage(image);
        }
        else if(Greenfoot.mouseMoved(null) && ! Greenfoot.mouseMoved(this))
        {
            image = new GreenfootImage(img1);
            setImage(image);
        }
    }

    public void act() 
    {
        if(Greenfoot.mousePressed(this))
        {
            button.play();
        }
        checkHover(image1,image2);
    }    
}
