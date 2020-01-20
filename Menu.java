import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;
import java.util.NoSuchElementException;
import java.awt.image.BufferedImage;
/**
 * World that holds the menu class, is the interface for all the level selecting, achievement,
 * highscores and instructions.
 * 
 * @author Kevin B, Jonathan, Kevin Y
 * @version January 16th 2019
 */
public class Menu extends World
{
    //bg music for menu
    private GreenfootSound menu = new GreenfootSound ("menu.wav");
    //buttons
    private Button howToPlay,info,load,highscores,play,achievements,back;
    private Button level1, level2, level3, endless,right,left;
    //scanner
    private Scanner readFile;
    //highscore
    private int[][] highscoreList = new int[10][4];
    //achievement list
    private boolean[] achievementList = new boolean[22];
    //highscores
    private Highscores highscore;
    private int highscoreLevel;
    //check if it is loaded
    private boolean loadAvailable,infoClicked;
    //achievement background
    private AchievementBackground achievementBackground;
    private GreenfootImage title = new GreenfootImage("Title.png");
    /**
     * Constructor for objects of class Menu.
     * 
     */
    public Menu()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(960, 640, 1); 
        //load achievement from achievement.txt
        loadAchievements("achievements.txt");
        //set load as false
        loadAvailable = false;
        //set highscore level
        highscoreLevel = 1;
        //declare all the buttons in the menu 
        howToPlay = new Button("Info.png","Info2.png");
        info = new Button("howToPlay.png","howToPlay.png");
        play = new Button("Play2.png","Play1.png");
        load = new Button("Load2.png", "Load1.png");
        level1 = new Button("Level1-2.png", "Level1-1.png");
        level2 = new Button("Level2-2.png", "Level2-1.png");
        level3 = new Button("Level3-2.png", "Level3-1.png");
        endless = new Button("Endless2.png", "Endless1.png");
        back = new Button("BackButton2.png", "BackButton1.png");
        highscores = new Button("highscores2.png","highscores1.png");
        right = new Button("RightArrow2.png", "RightArrow1.png");
        left = new Button("LeftArrow2.png", "LeftArrow1.png");
        achievements = new Button("Achievements2.png","Achievements1.png");
        achievementBackground = new AchievementBackground();
        //add objects in the main start menu
        addObject(play, 680, 260);
        addObject(load, 680, 360);
        addObject(highscores,680,460);
        addObject(achievements,680, 560);
        addObject(howToPlay,920,600);
        //instructions are not clicked
        infoClicked = false;
        //set background
        GreenfootImage bg = getBackground();
        //add title
        title.scale((int)(title.getWidth()*1.3), (int)(title.getHeight()*1.3));
        bg.drawImage(title, 440,40);
        setBackground(bg);
        checkLoad();
    }

    /**
     * checks if there is currently a load file
     */
    public void checkLoad()
    {
        //name of save file
        String fileName = "SaveInfo.txt";
        //number of lines
        boolean moreLines = true;
        int numLines = 0;
        int reachedBottom = 0;
        // If the file opening operation is successful, update the text in the open file button
        try 
        {
            //read the file
            readFile = new Scanner(new File(fileName));
            //while more lines
            while(moreLines)
            {
                //try reading file
                try
                {
                    String s = readFile.nextLine();
                    numLines++;
                }
                catch (NoSuchElementException e){
                    //if no more lines stop reading
                    moreLines = false;
                }
            }

        }
        catch (FileNotFoundException e) //catches if incorrect file is inputted because it does not exist
        {
            //file not found
            System.out.println("File not Found");
        }
        finally
        {
            if (readFile != null)
            {
                //close scanner
                readFile.close();
            }
        }
        if(numLines > 2)
        {
            //if more than 2 lines there is a load file
            loadAvailable = true;
        }
    }

    /**
     * show achievement
     * @param page which page the user is currently on
     */
    public void addAchievements(int page)
    {
        //if page is 1
        if (page == 1)
        {
            //add all the achievement icons
            for(int i = 0; i < /*achievementList.length*/4; i++)
            {
                addObject(new Achievement(achievementList[i],i), 100+i * 250, 170);
            }
            for(int i = 0; i < /*achievementList.length*/4; i++)
            {
                addObject(new Achievement(achievementList[i+4],i+4), 100+i * 250, 350);
            }
            for(int i = 0; i < /*achievementList.length*/4; i++)
            {
                addObject(new Achievement(achievementList[i+8],i+8), 100+i * 250, 530);
            }
        }
        //if page is 2
        else if (page == 2)
        {
            //add all the achievement icons
            for(int i = 0; i < /*achievementList.length*/4; i++)
            {
                addObject(new Achievement(achievementList[i+12],i+12), 100+i * 250, 170);
            }
            for(int i = 0; i < /*achievementList.length*/4; i++)
            {
                addObject(new Achievement(achievementList[i+16],i+16), 100+i * 250, 350);
            }
            for(int i = 0; i < /*achievementList.length*/2; i++)
            {
                addObject(new Achievement(achievementList[i+20],i+20), 100+i * 250, 530);
            }
        }
    }

    /**
     * remove the achievements when leaving the achievement page
     */
    public void removeAchievements()
    {
        //for every achievement remove it
        for (Object obj : getObjects(Achievement.class)) //'getWorld().getObjects(...)' if called from an Actor subclass
        {
            Achievement n = (Achievement) obj;
            removeObject(n);
        }
    }

    /**
     * load the achievement from a save file
     * @param file      the file to load from
     */
    public void loadAchievements(String file)
    {
        //file to load into
        String fileName = file;
        boolean moreLines = true;
        int numLines = 0;
        //try reading the file
        try 
        {
            //open scanner
            readFile = new Scanner(new File(fileName));
            while(moreLines)
            {
                try
                {
                    //read next line
                    String s = readFile.nextLine();
                    achievementList[numLines] = Boolean.parseBoolean(s);
                }
                catch (NoSuchElementException e){
                    //if no more lines stop reading
                    moreLines = false;
                }
                numLines++;
            }
        }
        catch (FileNotFoundException e) //catches if incorrect file is inputted because it does not exist
        {
            //file not found
            System.out.println("File not Found");
        }
        finally
        {
            if (readFile != null)
            {
                //close scanner
                readFile.close();
            }
        }
    }

    /**
     * load highscores
     * @param file      the file to load from
     */
    public void loadHighscores(String file)
    {
        //file name
        String fileName = file;
        //varaibles for reading a files
        boolean moreLines = true;
        int numLines = 0;
        // If the file opening operation is successful, update the text in the open file button
        try 
        {
            //read file
            readFile = new Scanner(new File(fileName));
            //if there are more lines
            while(moreLines)
            {
                try
                {
                    //read file
                    String s = readFile.nextLine();
                    numLines++;
                }
                catch (NoSuchElementException e){
                    //if no more lines, stop reading
                    moreLines = false;
                }
            }
            //read file 
            readFile = new Scanner(new File(fileName));
            moreLines = true;
            //declare highscore list
            highscoreList = new int[numLines/4][4];
            //number lines
            numLines = 0;
            //while more lines
            while(moreLines)
            {
                try{
                    //run 4 times
                    for(int i = 0; i < 4; i++)
                    {
                        //read next file
                        String s = readFile.nextLine();
                        switch(i)
                        {
                            //check which case and set highscore accordingly
                            case 0: 
                            {
                                //setMoney(Integer.parseInt(s));
                                highscoreList[numLines][i] = Integer.parseInt(s);
                                break;
                            }
                            case 1: 
                            {
                                //setLives(Integer.parseInt(s));
                                highscoreList[numLines][i] = Integer.parseInt(s);
                                break;
                            }
                            case 2: 
                            {
                                //setRound(Integer.parseInt(s));
                                highscoreList[numLines][i] = Integer.parseInt(s);
                                break;
                            }
                            case 3:
                            {
                                //levelType = Integer.parseInt(s);
                                highscoreList[numLines][i] = Integer.parseInt(s);
                            }
                        }
                    }
                    //increase lines
                    numLines++;
                }
                //no more lines
                catch (NoSuchElementException e){
                    //stop reading
                    moreLines = false;
                }
            }
        }
        //file not found
        catch (FileNotFoundException e) //catches if incorrect file is inputted because it does not exist
        {
            System.out.println("File not Found");
        }
        finally
        {
            if (readFile != null)
            {
                //close scanner
                readFile.close();
            }
        }
    }

    public void act()
    {
        //check if any buttons have been clicked, if they are clicked run the associated world for the level or menu
        //different level buttons
        if(!menu.isPlaying())
        {
            menu.play();
        }
        if(Greenfoot.mouseClicked(endless))
        {
            menu.stop();
            MyWorld w = new MyWorld(false,4,achievementList);
            Greenfoot.setWorld(w);
        }
        if(Greenfoot.mouseClicked(level1))
        {
            menu.stop();
            MyWorld w = new MyWorld(false,1,achievementList);
            Greenfoot.setWorld(w);
        }
        if(Greenfoot.mouseClicked(level2))
        {
            menu.stop();
            MyWorld w = new MyWorld(false,2,achievementList);
            Greenfoot.setWorld(w);
        }
        if(Greenfoot.mouseClicked(level3))
        {
            menu.stop();
            MyWorld w = new MyWorld(false,3,achievementList);
            Greenfoot.setWorld(w);
        }
        //show the play options
        if(Greenfoot.mouseClicked(play))
        {
            //display maps
            removeObject(play);
            removeObject(load);
            removeObject(highscores);
            removeObject(achievements);
            removeObject(howToPlay);
            //add the level buttons
            addObject(level1, 480,240);
            addObject(level2, 480,340);
            addObject(level3, 480,440);
            addObject(back,100,40);
            addObject(endless,480,540);
            setBackground("Background2.png");
        }
        //button for going back to main start screen
        if(Greenfoot.mouseClicked(back))
        {
            //remove all other buttons
            removeObject(info);
            removeAchievements();
            removeObject(achievementBackground);
            removeObject(right);
            removeObject(left);
            removeObject(highscore);
            removeObject(level1);
            removeObject(level2);
            removeObject(level3);
            removeObject(endless);
            removeObject(back);
            GreenfootImage bg=getBackground();
            bg.drawImage(title, 440,40);
            //add the starting screen buttons
            addObject(play, 680, 260);
            addObject(load, 680, 360);
            addObject(highscores,680,460);
            addObject(achievements,680, 560);
            addObject(howToPlay,920,600);
        }
        //if load is clicked try loading the load file
        if(Greenfoot.mouseClicked(load))
        {
            if(loadAvailable)
            {
                MyWorld w = new MyWorld(true,0,achievementList);
                Greenfoot.setWorld(w);
            }
        }
        //if highscore is clicked
        if(Greenfoot.mouseClicked(highscores))
        {
            //display maps
            removeObject(play);
            removeObject(load);
            removeObject(highscores);
            removeObject(achievements);
            removeObject(howToPlay);
            setBackground("Background2.png");
            //add objects for the highscore
            addObject(right,870,600);
            addObject(left,90,600);
            addObject(back,100,40);
            //load highscore
            loadHighscores("Highscores.txt");
            highscore = new Highscores(highscoreList,highscoreLevel);
            addObject(highscore,480,370);
        }
        //if mouse clicked the achievement buttons 
        if(Greenfoot.mouseClicked(achievements))
        {
            //add achievement background
            addObject(achievementBackground,480,320);
            loadAchievements("achievements.txt");
            //remove all the objects
            removeObject(play);
            removeObject(load);
            removeObject(highscores);
            removeObject(achievements);
            removeObject(howToPlay);
            //add objects
            addObject(back,100,40);
            addObject(right,870,40);
            addObject(left,730,40);
            //add achievements
            addAchievements(1);
        }
        //if mouse clicked how to play
        if(Greenfoot.mouseClicked(howToPlay))
        {
            //set background
            setBackground("Background2.png");
            //remove all other objects
            removeObject(play);
            removeObject(load);
            removeObject(highscores);
            removeObject(achievements);
            removeObject(howToPlay);
            //add instructions
            addObject(back,100,40);
            addObject(info,480,360);
        }
        //if clicked on the next page for the instruction
        if(Greenfoot.mouseClicked(info))
        {
            //if it is not clicked
            if(!infoClicked)
            {
                //show the instruction page 2
                info.update("howToPlay2.png","howToPlay2.png");
                infoClicked = true;
            }
            else
            {
                //show the instruction page 1
                info.update("howToPlay.png","howToPlay.png");
                infoClicked = false;
            }
        }
        //button clicked right
        if(Greenfoot.mouseClicked(right))
        {
            //if achievement next page
            if (getObjects(Achievement.class).size() > 0)
            {
                //remove page 1 achievements
                removeAchievements();
                //add page 2 achievements
                addAchievements(2);
            }
            else
            {
                //if showing highscores
                if(highscoreLevel < 4)
                {
                    //highscore increasing page
                    highscoreLevel++;
                }
                else
                {
                    //fightscore level 1
                    highscoreLevel = 1;
                }
                //update highscore
                highscore.update(highscoreLevel);
            }
        }
        //button clicked left
        else if(Greenfoot.mouseClicked(left))
        {
            //if achievement next page
            if (getObjects(Achievement.class).size() > 0)
            {
                //remove achievement
                removeAchievements();
                //show achievement 1
                addAchievements(1);
            }
            else
            {
                //if highscore level greater than 1
                if(highscoreLevel > 1)
                {
                    //decrease high score level
                    highscoreLevel--;
                }
                else
                {
                    //set highscore level to 4
                    highscoreLevel = 4;
                }
                //update highscore
                highscore.update(highscoreLevel);
            }
        }
    }

}