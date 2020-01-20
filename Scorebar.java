import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Score bar to keep track of all the variables such as; rocks, food, wood, gold,
 * population, day, status, and average age.
 * 
 * @author Mr.Cohen 
 * @version 2018-10-24
 */
public class Scorebar extends Actor
{
    private GreenfootImage bg;
    private int timer;
    private int money, round, lives;
    private String level;
    /**
     * Constructs a score bar that fits at the top of the screen
     */
    public Scorebar()
    {
        //set the image size
        bg = new GreenfootImage(130,100);
    }

    /**
     * Updates values when added to the world
     * @param w The world it is being added to
     */
    public void addedToWorld(World w)
    {
        //display the bars
        displayStats();
    }

    /**
     * Updates the top bar with all the resources and statistics from the world
     */
    public void displayStats()
    {
        bg.clear();
        String display = "";
        money = ((MyWorld)getWorld()).getMoney();//get world statistics
        round = ((MyWorld)getWorld()).getRound();
        lives = ((MyWorld)getWorld()).getLives();
        if(((MyWorld)getWorld()).getMaxRounds() != -1)
            display = "\u2665" + lives + "\n"+"$" + money + "\n"+"Round: " + round + "/" + ((MyWorld)getWorld()).getMaxRounds();
        else
            display = "\u2665" + lives + "\n"+"$" + money + "\n"+"Round: " + round;
        GreenfootImage text = new GreenfootImage(display, 22 , Color.BLACK, null);

        bg.drawImage(text, 10 , 0);
        bg.setTransparency(180);
        setImage(bg);
    }

    /**
     * Updates scorebar every 8 acts to reduce lag. 
     */
    public void act() 
    {
        //timer increase
        timer++;
        //update every 8 frames
        if(timer %10==0)
            displayStats();
    }    
}