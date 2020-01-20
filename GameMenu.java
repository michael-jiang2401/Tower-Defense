import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The game menu is used to display the pause menu, win menu, and lose menu during the game.
 * Contains many buttons such as mute, play, retry, go to menu, etc.
 * 
 * @author Kevin Biro
 * @version January 16th 2019
 */
public class GameMenu extends Actor
{
    //sounds when spawned based on conditions
    private GreenfootSound win = new GreenfootSound ("tada.wav");
    private GreenfootSound lose = new GreenfootSound ("lose.wav");
    private GreenfootImage image;
    private Button reset,menu,play,save,mute;
    private boolean added,gameOver;
    private Button[] buttons;
    private int round,maxRound,numEnemies;

    /**
     * Constructs a game menu with given conditions about the current game.
     *
     * @param gameOver Whether or not the game is over
     * @param muted Whether or not the game is muted
     * @param round The current round
     * @param maxRound The max round in the level
     * @param numEnemies The number of enemies in the game at the point it is created.
     */
    public GameMenu(boolean gameOver,boolean muted,int round,int maxRound,int numEnemies)
    {
        //save values
        this.numEnemies = numEnemies;
        this.round = round;
        this.maxRound = maxRound;
        this.gameOver = gameOver;
        //create buttons
        reset = new Button("RetryButton.png","RetryButton2.png");
        menu = new Button("MenuButton.png","MenuButton2.png");
        play = new Button("PlayButton.png","PlayButton2.png");
        save = new Button("SaveButton.png","SaveButton2.png");
        if(!muted)
            mute = new Button("MuteButton.png","MuteButton2.png");
        else
            mute = new Button("UnMuteButton.png","UnMuteButton2.png");
        if(gameOver)
        {
            image = new GreenfootImage("GameOverMenu.png");
            lose.play();
        }
        else if(round >= maxRound && maxRound != -1 && numEnemies < 1)
        {
            image = new GreenfootImage("WinMenu.png");
            win.play();
        }
        else
        {
            image = new GreenfootImage("PauseMenu.png");
        }
        buttons = new Button[5];
        buttons[0] = reset;
        buttons[1] = menu;
        buttons[2] = play;
        buttons[3] = save;
        buttons[4] = mute;
        setImage(image);
        added = false;
    }

    /**
     * Removes all the buttons in the buttons arrayList
     */
    public void removeButtons()
    {
        for(Button button : buttons)
        {
            getWorld().removeObject(button);
        }
        getWorld().removeObject(this);
    }

    public void act() 
    {
        //checks button clicks and does something different based on the button
        if(!added && gameOver)//add buttons if game over
        {
            getWorld().addObject(reset,getX()-80,getY()+80);
            getWorld().addObject(menu,getX()+80,getY()+80);
            added = true;
        }
        else if (!added && round >= maxRound && maxRound != -1 && numEnemies < 1)//add buttons if paused
        {
            getWorld().addObject(reset,getX(),getY()+30);
            getWorld().addObject(menu,getX()+60,getY()+80);
            getWorld().addObject(mute,getX()-60,getY()+80);
            added = true;
        }
        else if(!added)//add buttons if game won
        {
            getWorld().addObject(reset,getX()-60,getY());
            getWorld().addObject(menu,getX()+80,getY()+80);
            getWorld().addObject(save,getX()-80,getY()+80);
            getWorld().addObject(mute,getX(),getY()+80);
            getWorld().addObject(play,getX()+60,getY());
            added = true;
        }
        if(Greenfoot.mouseClicked(reset))
        {
            reset.resetLevel();
        }
        if(Greenfoot.mouseClicked(menu))
        {
            menu.goToMenu();
        }
        if(Greenfoot.mouseClicked(play))
        {
            play.playGame();
            removeButtons();
        }
        if(Greenfoot.mouseClicked(save))
        {
            save.saveGame();
        }
        if(Greenfoot.mouseClicked(mute))
        {
            mute.muteGame();
        }
    }    
}
