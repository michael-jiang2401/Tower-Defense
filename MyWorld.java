import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.io.File;
import java.util.NoSuchElementException;
/**
 * Tower Defense is a game in which you prevent enemies from reaching the end of the map. 
 * The enemies calculate the shortest path from the start to end and follow it trying to 
 * survive the defending towers. You have 4 levels to choose from and many different towers and consumables.
 * There are lots of dangerous enemies, each different from the last. For example the slime 
 * monster can only be injured by fire towers and dragons ignore the enemy path. While playing this 
 * game you strive to complete the levels, unlock achievements and set new highscores. 
 * Remember that you can always save your game and come back to it later if you need to.
 * 
 * Credits: 
 * <p>
 * Tower assets: 
 * <ul>
 * <li>https://opengameart.org/content/tower-defense-300-tilessprites
 * </ul>
 * <p>
 * 
 * Land mine: 
 * <ul>
 * <li>https://www.google.com/search?q=landmine+sprite&source=lnms&tbm=isch&sa=X&ved=0ahUKEwic3Nqz6bTfAhVStIMKHcvRAswQ_AUIDigB&biw=1920&bih=889#imgdii=jqNmbE2_LqiK2M:&imgrc=lZG4l00NpsrHwM:
 * </ul>
 * <p>
 * 
 * Enemies: 
 * <ul>
 * <li>https://www.google.com/search?biw=1920&bih=938&tbm=isch&sa=1&ei=_NEfXNOWFajBjwT-vrKYDQ&q=top+down+house+sprites&oq=top+down+house+sprites&gs_l=img.3...1806.2868..2955...0.0..1.158.618.7j1......1....1..gws-wiz-img.......0j0i30j0i5i30j0i8i30.G_yaZrSmvIM#imgrc=On0OQuzck1hKuM:
 * <li>https://gameartpartners.com/?s=top+down
 * <li>https://kenney.nl/assets/topdown-shooter
 * <li>https://gameartpartners.com/downloads/the-knight-top-down-game-sprites/
 * <li>https://opengameart.org/content/top-down-green-dragon-animated
 * <li>https://opengameart.org/content/over-head-top-down-art-collection
 * <li>https://opengameart.org/content/top-down-demon-animations
 * </ul>
 * <p>
 * Background: 
 * <ul>
 * <li>http://allacrost.org/wiki/index.php/Artwork_Categories
 * <li>https://opengameart.org/content/grass-pixel-art
 * <li>https://www.google.com/search?q=top+down+simple+backgrounds&source=lnms&tbm=isch&sa=X&ved=0ahUKEwiIv5Damt_fAhVn7IMKHaybBzoQ_AUIDigB&biw=1920&bih=938#imgdii=uNbZUAji6fiBnM:&imgrc=F52eFcdUXlURiM:
 * <li>https://www.google.com/search?biw=1920&bih=938&tbm=isch&sa=1&ei=hVEqXKycOJKSjwT38Z7AAg&q=lava+top+down+background&oq=lava+top+down+background&gs_l=img.3...45728.46720..46773...0.0..0.76.635.9......1....1..gws-wiz-img.nKGfOFkuxY4#imgdii=pq1EkMz4utGVFM:&imgrc=Xwp1gHdhh1TbvM:
 * </ul>
 * <p>
 * Gameover:
 * <ul>
 * <li>http://bigrookgames.com/portfolio-items/top-shooter-2d-sprite-animation-kit/
 * <li>https://www.google.com/search?tbm=isch&q=game+over+text&chips=q:game+over+text,online_chips:pixel+art&usg=AI4_-kSYC8O0436fnsGQhuDqchCcoHwthw&sa=X&ved=0ahUKEwiE3pCF2M3fAhVK_IMKHWu5AwAQ4lYILCgF&biw=1920&bih=938&dpr=1#imgrc=fKjbGivWdqb7wM:
 * </ul>
 * <p>
 * Restart:
 * <ul>
 * <li>https://www.google.com/search?biw=1920&bih=938&tbm=isch&sa=1&ei=g_ErXPK1D4jZjwTEyJ6QCA&q=retry+button+png&oq=retry+button+png&gs_l=img.3..0.13285.16108..16158...0.0..0.89.1043.14......1....1..gws-wiz-img.......35i39j0i67j0i24j0i10i24j0i30.uXm6MngXUCc#imgrc=voM26BOhaAjywM:
 * </ul>
 * <p>
 * Menu button: 
 * <ul>
 * <li>https://www.google.com/search?q=menu+button&source=lnms&tbm=isch&sa=X&ved=0ahUKEwiQ55fH2s3fAhWQoIMKHQLSBb4Q_AUIDigB&biw=1920&bih=938#imgrc=j86zC4rcavXd7M:
 * </ul>
 * <p>
 * Play: 
 * <ul>
 * <li>https://www.google.com/search?biw=1920&bih=938&tbm=isch&sa=1&ei=-jMsXIDsHaqGjwS2iqqAAQ&q=play+button&oq=play+button&gs_l=img.3..0i67j0j0i67j0l2j0i67j0l4.5392.5698..5864...0.0..0.115.356.3j1......1....1..gws-wiz-img.......0i7i30.m1PwkCpropQ#imgdii=Q85M_6Ww_VdW1M:&imgrc=6yU2BXwo2WR5bM:
 * </ul>
 * <p>
 * Save: 
 * <ul>
 * <li>https://www.google.com/search?biw=1920&bih=938&tbm=isch&sa=1&ei=_DcsXIGEMMrejwS9_q7YDw&q=save+button+circle+png&oq=save+button+circle+png&gs_l=img.3...945020.945780..947803...0.0..0.99.547.7......1....1..gws-wiz-img.TRW6OtQOEno#imgrc=UsUjkj3aVfoYdM:
 * </ul>
 * <p>
 * Mute: 
 * <ul>
 * <li>https://www.google.com/search?q=mute+button+png&source=lnms&tbm=isch&sa=X&ved=0ahUKEwjb0KSToc7fAhUnxYMKHfifAJAQ_AUIDigB&biw=1920&bih=938#imgrc=hL_BBMWxGSyYJM:
 * </ul>
 * <p>
 * Cycle: 
 * <ul>
 * <li>https://www.google.com/search?biw=1920&bih=889&tbm=isch&sa=1&ei=LSYtXOHZJMPTjgS-lIrYDw&q=cycle+button+png&oq=cycle+button+png&gs_l=img.3...7109.7987..8131...0.0..0.77.424.6......1....1..gws-wiz-img.......0i7i30.osVPWeKe8ac#imgdii=JF2zfBlafsjxsM:&imgrc=y43QTfVi96UYBM:
 * </ul>
 * <p>
 * System: 
 * <ul>
 * <li>https://www.google.com/search?biw=1920&bih=889&tbm=isch&sa=1&ei=NiYtXNrlOLLUjgTSjrHoAQ&q=settings+button+png&oq=settings+button+png&gs_l=img.3..0.427987.428809..428921...0.0..0.83.610.8......1....1..gws-wiz-img.......0i7i30j0i7i5i30j0i8i7i30j0i8i30.nM2tJmKHojQ#imgrc=TF5QojmDGz8_QM:
 * </ul>
 * <p>
 * Play button: 
 * <ul>
 * <li>https://www.google.com/search?biw=1920&bih=889&tbm=isch&sa=1&ei=ES4tXNb2OeLEjgTn5rigCQ&q=play+button&oq=play+button&gs_l=img.3..35i39l2j0j0i67j0l2j0i67j0j0i67l2.9429.9867..10195...0.0..0.83.397.5......1....1..gws-wiz-img.......0i7i30j0i10.tE6zDuFRQYo#imgdii=oJ1Ng_k6stmn_M:&imgrc=RfbCwhEF4OZ31M:
 * </ul>
 * <p>
 * You win screen: 
 * <ul>
 * <li>https://www.google.com/search?biw=1920&bih=938&tbm=isch&sa=1&ei=rpouXOL2HOrLjgSmlZ3ADQ&q=you+win+pixel+art+text&oq=you+win+pixel+art+text&gs_l=img.3...2398.2857..2933...0.0..1.129.343.3j1......1....1..gws-wiz-img.f1OIKZd5srE#imgrc=bvunjJaXttjoOM:
 * </ul>
 * <p>
 * Elements: 
 * <ul>
 * <li>https://www.behance.net/gallery/3030623/2d-Effects-Animation
 * </ul>
 * <p>
 * Stun: 
 * <ul>
 * <li>https://www.google.com/search?safe=strict&biw=1366&bih=626&tbm=isch&sa=1&ei=6i42XMW7KZXvjwSxwYugBw&q=stunned+stars+sprites&oq=stunned+stars+sprites&gs_l=img.3...7072.7707..7955...0.0..0.82.145.2......1....1..gws-wiz-img.LLndOSp9zf4#imgrc=VOgOCBbY9BO5KM:
 * </ul>
 * <p>
 * Help button: 
 * <ul>
 * <li>https://www.google.com/search?biw=1920&bih=938&tbm=isch&sa=1&ei=Zb03XLvvLuuZjwT9v62YBg&q=question+mark+button&oq=question+mark+button&gs_l=img.3..0l2j0i24l3.7576.10283..10400...1.0..0.96.1546.20......1....1..gws-wiz-img.......0i67.nLSoacyS63U#imgrc=v-JGWOQMzd7RaM:
 * </ul>
 * <p>
 * How to play screen: 
 * <ul>
 * <li>https://www.google.com/search?q=how+to+play+screen&source=lnms&tbm=isch&sa=X&ved=0ahUKEwjfjJbgmeTfAhXn64MKHY-_BicQ_AUIDigB&biw=1920&bih=938#imgrc=TAp0EahJm6oK-M:
 * </ul>
 * <p>
 * Achievement icons: 
 * <ul>
 * <li>https://www.flaticon.com/free-icons/achievement
 * </ul>
 * <p>
 * Sounds
 * <ul>
 * <li>https://opengameart.org/content/tower-defense-theme
 * <li>http://soundbible.com/1540-Computer-Error-Alert.html
 * <li>https://soundbible.com/1770-Ray-Gun.html
 * <li>http://soundbible.com/1356-Flame-Arrow.html
 * <li>https://www.freesoundeffects.com/free-sounds/fireball-10079/
 * <li>http://soundbible.com/1902-Fire-Burning.html
 * <li>http://soundbible.com/2125-Wind-Blowing.html
 * <li>http://soundbible.com/1452-Shooting-An-MP5.html
 * <li>http://soundbible.com/2108-Shoot-Arrow.html
 * <li>http://soundbible.com/1395-Silencer.html
 * <li>https://www.soundjay.com/gun-sound-effect.html
 * <li>http://soundbible.com/1320-Short-Circuit.html
 * <li>http://soundbible.com/1097-Slime-Splash.html
 * <li>http://soundbible.com/1830-Sad-Trombone.html
 * <li>http://soundbible.com/419-Tiny-Button-Push.html
 * <li>https://www.youtube.com/watch?v=54XBrd7w1bI
 * <li>http://centracomm.cachefly.net/majornelson/2007/Achievement-mp3-sound.mp3
 * <li>https://patrickdearteaga.com/arcade-music/
 * <li>https://www.youtube.com/watch?v=J1YTQvtmsjg
 * <li>https://downloads.khinsider.com/game-soundtracks/album/dead-space
 * <li>https://www.youtube.com/watch?v=k1BstEqSpNE
 * <li>https://www.youtube.com/watch?v=pPz08Ew6xpA
 * </ul>
 * <p>
 * Code Credit for Dijkstra's Pathing Algorithm:
 * <ul>
 * <li>Located in Node Class:
 * <li>https://brilliant.org/wiki/dijkstras-short-path-finder/
 * <li>http://www.gitta.info/Accessibiliti/en/html/Dijkstra_learningObject1.html
 * <li>https://www.youtube.com/watch?v=q3yKyE19OR0 (tutorial)
 * </ul>
 * <p>
 * @author Kevin B, Kevin Y, Kevin C, Jonathan, Michael
 * @version January 16th 2019
 */
public class MyWorld extends greenfoot.World
{
    static Scanner readFile;

    //pathing variables
    private boolean pathChanged;
    private Node[] checkPathToTarget;
    private Node[] checkPathToTarget2;
    private int tokenOwner = 0;//keeps track of which enemy has the token which allows them to calculate a path
    private Target target = new Target();
    private Start start = new Start();
    private Start start2 = new Start();

    //sounds
    private GreenfootSound invalid = new GreenfootSound ("Invalid.wav");
    private GreenfootSound unlocked = new GreenfootSound ("achievement.wav");
    private GreenfootSound level1 = new GreenfootSound ("level1.wav");
    private GreenfootSound level2 = new GreenfootSound ("level2.mp3");
    private GreenfootSound level3 = new GreenfootSound ("level3.wav");
    private GreenfootSound level4 = new GreenfootSound ("level4.wav");

    private int tempTimer;
    private Enemy tempEnemy;

    //buttons
    private static Button settings;
    private static Button startB;
    private static boolean muted;

    private int achievementTimer;
    //ArrayLists
    private static ArrayList<AchievementPopUp> achievementList;//achievement list
    private static ArrayList<Tower> towers;//Tower lists
    private static ArrayList<Tower> tempTowers;
    private static Tower lastTower = null;

    //2d arrays for the map layout
    private static Tower[][] map = new Tower[24][16];
    private static Tower[][] mapCopy = new Tower[24][16];//to check tower placement
    private static Tower[][] saveCopy = new Tower[24][16];//to save tower placement
    private static Tower[][] realMap = new Tower[24][16];//updates when round is over
    private static int width = map.length;//amount of arrays (width of level)
    private static int height = map[0].length;//amount of values in each array (height of level)
    private static int money, round, lives;
    private boolean showingTowers;
    private Button switchB;

    //Towers
    private TowerBase tower1= new TowerBase(1,true,true,0,0);
    private TowerBase tower3= new TowerBase(3,true,true,0,0);
    private TowerBase tower4= new TowerBase(4,true,true,0,0);
    private TowerBase tower5= new TowerBase(5,true,true,0,0);
    private TowerBase tower6= new TowerBase(6,true,true,0,0);
    private TowerBase tower7= new TowerBase(7,true,true,0,0);

    //other buttons (consumales)
    private TowerBar towerBar= new TowerBar(true);
    private TowerBar consumableBar = new TowerBar(false);
    private Wall wall= new Wall(2,true, true);
    private AirStrikeButton button1 = new AirStrikeButton();
    private MineButton button2 = new MineButton();
    private BomberButton button3 = new BomberButton();
    private HelicopterButton button4;
    private Helicopter heli;
    private boolean addedHeli;
    private int heliRoundsAlive;
    //private Helicopter heli = new Helicopter();
    //wave display bar
    //enemy spawning variables
    private int enemyTimer, numEnemies, totalEnemiesInRound, enemySpawnInterval;
    private double enemiesLoaded;
    private boolean roundStarted, mapChanged;
    private int roundComplete; //the percent of the round complete
    private WaveStatus waveStatus = new WaveStatus(40,' ',Color.BLACK, 255,3);

    //round variables
    private int maxRounds;
    private int healthInWave,currHealth,enemyHealth;
    private int realMoney, realRound, realLives;
    private int levelType;
    private boolean xpAwarded;
    private boolean gameOver,menuOpen,draggingTower;

    //achievement boolean
    private static boolean[] achievements = new boolean[22];
    private static int airStrikeCounter;
    private static int bomberCounter;
    private static int mineCounter;
    private static int knightCounter;
    private static int dragonCounter;
    private static int demonCounter;
    private static int slimeCounter;
    private static int wizardCounter;
    /**
     * Constructor for objects of class MyWorld.
     * 
     */
    public MyWorld(boolean needsLoad, int levelType, boolean[] achievements)
    {    
        // Create a new world with 960x640 cells with a cell size of 1x1 pixels.
        super(960, 640, 1);

        this.achievements = achievements;
        achievementList = new ArrayList<AchievementPopUp>();
        achievementTimer = 600;
        gameOver = false;
        menuOpen = false;
        draggingTower = false;
        this.levelType = levelType;
        map = new Tower[24][16];
        towers = new ArrayList<Tower>();
        tempTowers = new ArrayList<Tower>();

        money = 10000;
        round = 0;
        lives = 20;

        xpAwarded = true;
        pathChanged = false;
        realMoney = money;
        realRound = round;
        realLives = lives;

        muted = false;
        addedHeli = false;
        heliRoundsAlive = 0;
        heli = new Helicopter(heliRoundsAlive);
        startB = new Button("StartButton.png","StartButton2.png");
        //
        addObject(startB,935,615);
        addObject(new Scorebar(), 60, 620);
        addObject(start, 20, 300);
        addObject(start2, 20, 180);
        addObject(target, 940,300);//target
        showingTowers=true;
        switchB=new Button("CycleButton.png","CycleButton2.png");
        settings = new Button("SettingsButton.png","SettingsButton2.png");
        addObject(settings,870,615);
        addObject(switchB,800, 595);
        showTowers();

        //addObject(heli, 150, 100);

        currHealth = 1;
        if(round < 1)//set starting health in wave if the game isnt loaded
            healthInWave = 1000;
        else
            healthInWave=(int)Math.pow(round*50,2);
        totalEnemiesInRound = 5 + round*5;
        if(60-round < 1)
            enemySpawnInterval = Greenfoot.getRandomNumber(10)+10;//set enemy spawn speed
        else
            enemySpawnInterval = Greenfoot.getRandomNumber(60-round)+10;
        roundStarted = false;
        mapChanged = false;
        roundComplete = 0;
        addObject(waveStatus, 900,580);

        setPaintOrder(AchievementPopUp.class,WaveStatus.class,TextButton.class,UpgradeMenu.class,HealthBar.class,AirStrike.class, Bomber.class,Helicopter.class, HelicopterGun.class, Explosion.class,Projectile.class,Tower.class,TowerBase.class,Enemy.class,Start.class,Target.class);
        for(int i = 2; i < 22; i++)//block off bottom of screen
        {
            Wall w = new Wall(2,false,false);
            editMap(i*40,620,w,2);
        }
        if(needsLoad)
            load("SaveInfo.txt");
        else
            loadWorld();
        copy(map,realMap);
        if(levelType == 2)
            checkPath(checkPathToTarget2,start2,2);
        checkPath(checkPathToTarget,start,1);
    }

    /**
     * Plays music based on the level type and loops it.
     */
    public void playMusic()
    {
        if(!gameOver && !muted)
        {
            if(levelType == 1)
            {
                if(!level1.isPlaying())
                {
					level1.setVolume(60);
                    level1.play();
                }
            }
            else if(levelType == 2)
            {
                if(!level2.isPlaying())
                {
					level2.setVolume(60);
                    level2.play();
                }
            }
            else if(levelType == 3)
            {
                if(!level3.isPlaying())
                {
					level3.setVolume(60);
                    level3.play();
                }
            }
            else if(levelType == 4)
            {
                if(!level4.isPlaying())
                {
					level4.setVolume(50);
                    level4.play();
                }
            }
        }
        else
        {
            if(level1.isPlaying())
            {
                level1.stop();
            }
            else if(level2.isPlaying())
            {
                level2.stop();
            }
            else if(level3.isPlaying())
            {
                level3.stop();
            }
            else if(level4.isPlaying())
            {
                level4.stop();
            }
        }

    }

    /**
     * Resets the image for the helicopter button
     */
    public void resetHeliButton()
    {
        for (HelicopterButton obj : getObjects(HelicopterButton.class))
        {
            HelicopterButton n = (HelicopterButton) obj;
            n.resetImage();
        }
    }

    /**
     * Returns the number of rounds that the helicopter has been in the game
     */
    public int getHeliRounds()
    {
        return heliRoundsAlive;
    }

    /**
     * Sets the number of rounds that the helicopter has beein in the game
     *
     *@param num The number of rounds
     */
    public void setHeliRounds(int num)
    {
        heliRoundsAlive = num;
    }

    /**
     * Returns if a helicopter has been added to the game
     *
     *@return boolean Whether or not the helicopter has been added
     */
    public boolean getAddedHeli()
    {
        return addedHeli;
    }

    /**
     * Sets whether or not a helicopter has been added to the game
     *
     *@param b Whether or not the helicopter is added to the game
     */
    public void setAddedHeli(boolean b)
    {
        addedHeli = b;
    }

    /**
     * Returns the temporaty towers of the game
     *
     * @return ArrayList<Tower> An array list of the temporary towers
     */
    public ArrayList<Tower> getTempTowers()
    {
        return tempTowers;
    }

    /**
     * Sets whether or not a tower is being dragged
     *
     * @param b The boolean that determines if a tower is being dragged
     */
    public void setDragging(boolean b)
    {
        draggingTower = b;
    }

    /**
     * Returns whether or not the game is over
     *
     * @return boolean Whether or not the game is over
     */
    public boolean getGameOver()
    {
        return gameOver;
    }

    /**
     * Returns whether or not the menu is open
     *
     * @return boolean Whether or not the menu is open
     */
    public boolean getMenuOpen()
    {
        return menuOpen;
    }

    /**
     * Sets whether or not the game is over
     *
     * @param b Whether or not the game is over
     */
    public void setGameOver(boolean b)
    {
        gameOver = b;
    }

    /**
     * Sets whether or not the menu is open
     *
     * @param b Whether or not the menu is open
     */
    public void setMenuOpen(boolean b)
    {
        menuOpen = b;
    }

    /**
     * Resets the current level by calling the world with the starting parameters
     */
    public void resetLevel()
    {
        MyWorld w = new MyWorld(false,levelType,achievements);
        Greenfoot.setWorld(w);
    }

    /**
     * Increments the AirStrike counter
     */
    public void addAirStrikeCounter()
    {
        airStrikeCounter++;
    }

    /**
     * Increments the Bomber counter
     */
    public void addBomberCounter()
    {
        bomberCounter++;
    }

    /**
     * Increments the Mine counter
     */
    public void addMineCounter()
    {
        mineCounter++;
    }

    /**
     * Increments the specified enemy counter in the parameters
     *
     * @param enemyType The type of the enemy being incremented
     */
    public void addEnemyCounter(int enemyType)
    {
        if (enemyType ==1)
        {
            demonCounter++;
        }
        if (enemyType ==2)
        {
            dragonCounter++;
        }
        if (enemyType ==3)
        {
            knightCounter++;
        }
        if (enemyType ==4)
        {
            slimeCounter++;
        }
        if (enemyType ==5)
        {
            wizardCounter++;
        }
    }

    /**
     * Sets the current world to the menu
     */
    public void goToMenu()
    {
        Menu w = new Menu();
        Greenfoot.setWorld(w);
    }

    /**
     * Loads the map depending on the levelType
     */
    public void loadWorld()
    {
        switch(levelType)
        {
            case 1: 
            {
                load("Level1.txt");
                setBackground("snow.jpg");
                removeObject(start2);
                maxRounds = 30;
                break;
            }
            case 2: 
            {
                load("Level2.txt");
                setBackground("lava.png");
                maxRounds = 50;
                break;
            }
            case 3: 
            {
                load("Level 3.txt");
                removeObject(start2);
                setBackground("stone2.png");
                maxRounds = 60;
                break;
            }
            case 4: 
            {
                setBackground("sand.jpg");
                removeObject(start2);
                maxRounds = -1;
            }
        }
    }

    /**
     * Returns the maximum number of rounds in the Level
     *
     * @return int The max number of rounds
     */
    public int getMaxRounds()
    {
        return maxRounds;
    }

    /**
     * Returns the level type
     *
     * @return int The level type
     */
    public int getLevelType()
    {
        return levelType;
    }

    /**
     * Switches from displaying towers on the tower bar to displaying consumables 
     * and vise versa
     *
     */
    public void switchTowers()
    {
        if(showingTowers)//hide towers and show consumables
        {
            removeTowers();
            showConsumables();
            showingTowers=false;
        }
        else//hide consumables and show Towers
        {
            removeConsumables();
            showTowers();
            showingTowers=true;
        }
    }

    /**
     * Removes all the towers that are touching the TowerBar
     *
     */
    public void removeTowers()
    {
        List<Tower> towers = getObjects(Tower.class);//remove Towers
        if(towers !=null && !towers.isEmpty())
        {
            for(Tower tower : towers)
            {
                if(tower.getTouchingBar())
                {
                    removeObject(tower);
                }
            }
        }
        List<TowerBase> bases = getObjects(TowerBase.class);//remove towerBases
        if(bases !=null && !bases.isEmpty())
        {
            for(TowerBase base : bases)
            {
                if(base.getTouchingBar())
                {
                    removeObject(base);
                }

            }
        }
        for (Object obj : getObjects(TowerBar.class)) //remove towerBar
        {
            TowerBar n = (TowerBar) obj;
            removeObject(n);
        }
    }

    /**
     * Shows the towers when creating the TowerBar
     *
     */
    public void showTowers()
    {
        for (Object obj : getObjects(TowerBar.class)) //'getWorld().getObjects(...)' if called from an Actor subclass
        {
            TowerBar n = (TowerBar) obj;
            removeObject(n);
        }
        TowerBase tower3= new TowerBase(3,true,true,0,0);
        TowerBase tower4= new TowerBase(4,true,true,0,0);
        TowerBase tower5= new TowerBase(5,true,true,0,0);
        TowerBase tower6= new TowerBase(6,true,true,0,0);
        TowerBase tower7= new TowerBase(7,true,true,0,0);
        TowerBar towerBar= new TowerBar(true);
        TowerBase tower1= new TowerBase(1,true,true,0,0);
        Wall wall= new Wall(2,true, true);
        addObject(towerBar, 480, 612);
        addObject(tower1,210,595);
        addObject(tower4, 570,595);//create tower 1 spawner
        addObject(tower3, 300,595);//create tower 1 spawner
        addObject(tower5, 480, 595);
        addObject(tower6, 390, 595);
        addObject(tower7, 660, 595);
        addObject(wall, 750,595);
    }

    /**
     * Shows the consumables when creating the TowerBar
     *
     */
    public void showConsumables()
    {
        button4 = new HelicopterButton(addedHeli,heliRoundsAlive);
        addObject(consumableBar, 480, 612);
        addObject(button1,345,595);
        addObject(button2,435,595);
        addObject(button3, 525, 595);
        addObject(button4,615,595);
    }

    /**
     * Removes the consumables
     *
     */
    public void removeConsumables()
    {
        removeObject(consumableBar);
        removeObject(button1);
        removeObject(button2);
        removeObject(button3);
        removeObject(button4);
    }

    /**
     * Returns whether or not the round has started
     *
     * @return boolean Whether or not the round has started
     */
    public boolean getRoundStarted()
    {
        return roundStarted;
    }

    /**
     * Returns the current round
     *
     * @return int The current round
     */
    public int getRound()
    {
        return round;
    }

    /**
     * Increments the round
     */
    public void increaseRound()
    {
        round ++;
    }

    /**
     * Starts the round by incrementing the round
     */
    public void startRound()
    {
        if(!roundStarted && getObjects(Enemy.class).size() == 0)
        {
            roundStarted = true;
            round++;
        }
    }

    /**
     * Sets whether or not the game is muted
     *
     * @param b Whether or not the game is muted
     */
    public void setMuted(boolean b)
    {
        muted = b;
    }

    /**
     * Returns whether or not the game is muted
     *
     * @return boolean Whether or not the game is muted
     */
    public boolean getMuted()
    {
        return muted;
    }

    /**
     * Returns the number of lives
     *
     * @return int The number of lives
     */
    public int getLives()
    {
        return lives;
    }   

    /**
     * Sets the number of lives
     *
     * @param l The number of lives
     */
    public void setLives(int l)
    {
        lives = l;
    }

    /**
     * Sets the round
     *
     * @param r The round
     */
    public void setRound(int r)
    {
        round = r;
    }

    /**
     * Decreases the number of lives
     *
     * @param n The number of lives to decrease by
     */
    public void lowerLives(int n)
    {
        lives -= n;
    }   

    /**
     * Returns the 2d array containing all the Towers
     *
     * @return Tower[][] The 2d array of towers
     */
    public static Tower[][] getMap()
    {
        return map;
    }

    /**
     * Returns the height of the World
     *
     * @return int The height of the world
     */
    public static int WorldSizeY()
    {
        return height;
    }

    /**
     * Returns the width of the World
     *
     * @return int The width of the world
     */
    public static int WorldSizeX()
    {
        return width;
    }

    /**
     * Sets the last tower being dragged
     *
     * @param tower The last tower being dragged
     */
    public void setLastTower(Tower tower)
    {
        lastTower = tower;
    }

    /**
     * Checks the path and updates it if the path has changed. Removes the last tower if the 
     * path is invalid.
     *
     * @param checkPathToTarget The Node array being used to check the path
     * @param start The starting point of the enemies
     * @param num The path ball number to replace
     */
    public void checkPath(Node[] checkPathToTarget, Start start,int num)
    {
        checkPathToTarget = Node.getPath(start, getEnemyTarget());//get a path

        if(checkPathToTarget != null && checkPathToTarget.length > 2)//if path is valid
        {
            for (Object obj : getObjects(PathBall.class)) //'getWorld().getObjects(...)' if called from an Actor subclass
            {
                PathBall n = (PathBall) obj;//update pathballs
                n.destroy(num);
            }
            showPath(checkPathToTarget,num);//show the path
        }
        else//if path invalid, remove the last tower
        {
            removeLastTower();
        }
    }

    /**
     * Removes the last tower placed by the user
     *
     */
    public void removeLastTower()
    {
        if(lastTower != null)//if there is a last tower
        {
            addObject(new X(), lastTower.getX(), lastTower.getY());
            copy(mapCopy,map);//set the map to the copy in order to reverse the changes
            lastTower.setValidity(false);
            addMoney(lastTower.getTowerCost());

            towers.remove(towers.size()-1);
            removeObject(lastTower);
            if(!muted)
                invalid.play();
        } 
    }

    /**
     * Edits the 2d array of towers when a tower is placed on the map.
     *
     * @param x The x position of the tower
     * @param y The y position of the Tower
     * @param type The tower being added
     * @param size The size of the tower (towers are 2x2 while walls and obstacles are 1x1)
     */
    public void editMap(int x, int y, Tower type, int size)
    {
        //make a copy of the map in case the change is invalid
        resetEnemyPaths();
        copy(map,mapCopy);
        if(size==1)//if the tower is a wall or obstacle
        {
            map[x/40][y/40] = type;//edit the map
            if(!roundStarted && getObjects(Enemy.class).size() == 0)
            {
                tempTowers.add(type);//add tower to array list
            }
        }
        else
        {
            if(!roundStarted && getObjects(Enemy.class).size() == 0)
            {
                tempTowers.add(type);//add tower to array list
            }
            towers.add(type);
            for(int i = 0; i < size; i++)//add the tower in 4 places in the map
            {
                for(int j = 0; j < size; j++)
                {
                    try
                    {
                        map[x/40-1+j][y/40-1+i] = type;
                    }
                    catch(ArrayIndexOutOfBoundsException e)
                    {
                        addMoney(10);
                    }
                }
            }
        }
        //checkPath(checkPathToTarget,start,1);
        if(levelType == 2)//update the 2 paths on level 2
        {
            checkPath(checkPathToTarget,start,1);
            checkPath(checkPathToTarget2,start2,2);
        }
        else//update the path
        {
            checkPath(checkPathToTarget,start,1);
        }
    }

    /**
     * Copies the current 2d array map
     *
     * @param map1 The map being copied
     * @param map2 The map being copied to
     */
    public void copy(Tower[][] map1, Tower[][]map2)
    {
        for(int i = 0; i < map1.length; i++)
        {
            for(int j = 0; j < map1[0].length; j++)
            {
                map2[i][j] = map1[i][j];
            }
        }
    }

    /**
     * Gives the token to the next enemy by incrementing the owner variable
     *
     */
    public void passTokenToNext()//gives the token to the next enemy
    {
        tokenOwner++;
    }

    /**
     * Checks which enemy is the token owner
     *
     */
    public boolean isTokenOwner(Enemy enemy)//checks which enemy is the token owner
    {
        //gets the list of enemies
        List<Enemy> enemies = getObjects(Enemy.class);
        //if there are no enemies
        if (enemies.size() == 0)
        {
            return false;
        }
        //if the token number is greater than the number of enemies, it resets to 0
        if (tokenOwner >= enemies.size())
        {
            tokenOwner = 0;
        }
        //returns the enemy that currently has the token
        return enemies.get(tokenOwner) == enemy;
    }

    /**
     * Returns the Coordinates of the enemy target
     *
     * @return Coordinates The coordinates of the enemy target
     */
    public Coordinates getEnemyTarget()
    {
        return new Coordinates(target.getX(), target.getY());//enemies go towards the player
    }

    /**
     * Shows the calculated enemy path 
     *
     * @param input The Node path of the enemies
     * @param num The path ball number to be placed.
     */
    public void showPath(Node[] input,int num)//shows the current path of the enemy
    {
        for(int i = 0; i < input.length; i++)
        {
            //places balls from the enemy to the target to indicate its path
            addObject(new PathBall(num), ((input[i].returnX() * 40) + 20), ((input[i].returnY() * 40) + 20));
        }
    }

    /**
     * Spawns enemies randomly based on the number of health each level can spawn. 
     * Stronger enemies such as demons and dragons spawn at set levels and after a set level. 
     * Once the round health has been filled with enemy health, enemies stop spawning.
     *
     * @param health The total amount of health in the round
     * @param spawnInterval The number of acts in between enemy spawns
     */
    public void spawnEnemies(int health, int spawnInterval)
    {
        if(health > currHealth)
        {
            if(enemyTimer > spawnInterval)
            {
                for(int i = levelType; i > 0; i--)
                {
                    //spawn demons if the round can be divided by 15 or if the round is greater than 20
                    if(round % 15 == 0 || (health-currHealth > health/2 && Greenfoot.getRandomNumber(15)==1 && round > 20))
                    {
                        enemyHealth = (Greenfoot.getRandomNumber(round*5000)+round*500)*round;
                        if(levelType == 1 || levelType == 4 || levelType == 3)
                            addObject(new Demon(2, enemyHealth), 20, 300);
                        else if(levelType == 2)
                            addObject(new Demon(2, enemyHealth), 20, 380-((i-1)*200));

                    }
                    //spawn dragons if the round can be divided by 10 or if the round is greater than 15
                    else if(round % 10 == 0 || (health-currHealth > health/2 && Greenfoot.getRandomNumber(10)==1 && round > 15))
                    {
                        enemyHealth = (Greenfoot.getRandomNumber(round*1000)+round*100)*round;
                        if(levelType == 1 || levelType == 4 || levelType == 3)
                            addObject(new Dragon(Greenfoot.getRandomNumber(1)+2, enemyHealth), 20, 300);
                        else if(levelType == 2)
                            addObject(new Dragon(Greenfoot.getRandomNumber(1)+2, enemyHealth), 20, 380-((i-1)*200));

                    }
                    //spawn Wizards if the round is greater than 7
                    else if((Greenfoot.getRandomNumber(10)==1 && round % 7 == 0) || (Greenfoot.getRandomNumber(10)==1 && round > 7))
                    {
                        enemyHealth = (Greenfoot.getRandomNumber(round*700)+round*70)*round;
                        if(levelType == 1 || levelType == 4 || levelType == 3)
                            addObject(new Wizard(Greenfoot.getRandomNumber(1)+2, enemyHealth), 20, 300);
                        else if(levelType == 2)
                            addObject(new Wizard(Greenfoot.getRandomNumber(1)+2, enemyHealth), 20, 380-((i-1)*200));

                    }
                    //spawn slimes if the round is divisible by 5 or if the round is greater than 5
                    else if((Greenfoot.getRandomNumber(4)==1 && round % 5 == 0) || (Greenfoot.getRandomNumber(10)==1 && round > 5))
                    {
                        enemyHealth = (Greenfoot.getRandomNumber(round*500)+round*50)*round;
                        if(levelType == 1 || levelType == 4 || levelType == 3)
                            addObject(new Slime(Greenfoot.getRandomNumber(1)+2, enemyHealth), 20, 300);
                        else if(levelType == 2)
                            addObject(new Slime(Greenfoot.getRandomNumber(1)+2, enemyHealth), 20, 380-((i-1)*200));

                    }
                    //spawn knights to fill in the gaps
                    else
                    {
                        enemyHealth = (Greenfoot.getRandomNumber(round*300)+round*25)*round;
                        if(levelType==1 || levelType == 4 || levelType == 3)
                            addObject(new Knight(Greenfoot.getRandomNumber(3)+2, enemyHealth), 20, 300);
                        else if (levelType == 2)
                            addObject(new Knight(Greenfoot.getRandomNumber(3)+2, enemyHealth), 20, 380-((i-1)*200));

                    }

                    xpAwarded = false;
                    enemyTimer = 0;
                }
                currHealth += enemyHealth;//add the health to the total enemy health

            }
            enemyTimer++;
        }
        else//end of spawning enemies
        {
            //determine the new health in the next round
            healthInWave=(int)Math.pow(round*50,2);
            currHealth = 1;
            roundStarted = false;
            //addMoney(200);

            if(50-round < 1)//determine the enemy spawn interval
                enemySpawnInterval = Greenfoot.getRandomNumber(10)+5;
            else
                enemySpawnInterval = Greenfoot.getRandomNumber(50-round)+5;

            /*
            copy(map,realMap);
            realMoney = money;
            realRound = round;
            realLives = lives;*/
        }
    }

    /**
     * Returns the amount of money saved at the end of the round
     *
     * @return int The amount of money
     */
    public int getRealMoney()
    {
        return realMoney;
    }

    /**
     * Returns the round number saved at the end of the round
     *
     * @return int The round number
     */
    public int getRealRound()
    {
        return realRound;
    }

    /**
     * Returns the number of lives saved at the end of the round
     *
     * @return int The number of lives
     */
    public int getRealLives()
    {
        return realLives;
    }

    /**
     * Sets the amount of money
     *
     * @param m The amount of money
     */
    public void setMoney(int m)
    {
        money = m;
    }

    /**
     * Returns the amount of money
     *
     * @return int The amount of money
     */
    public int getMoney()
    {
        return money;
    }

    /**
     * Decreases the amount of money by a certain amount
     *
     * @param amount The decrease in money
     */
    public void loseMoney(int amount)
    {
        money -= amount;
    }

    /**
     * Increases the amount of money by a certain amount
     *
     * @param amount The amount to increase the money by
     */
    public void addMoney(int amount)
    {
        money+= amount;
    }

    /**
     * Returns a modified copy of the real map in order to save it to a text file
     *
     * @return Tower[][] The copy of the real map
     */
    public Tower[][] getCopy()
    {
        saveCopy = new Tower[24][16];//make a new map
        copy(realMap,saveCopy);//copy the real map to the new map
        for(int i = 0; i < saveCopy.length-1; i++)//modify the array to make it easier to save it to a text file
        {
            for(int j = 0; j < saveCopy[0].length-1; j++)
            {
                if (saveCopy[i][j] != null && saveCopy[i][j].getType()!= 2 && saveCopy[i][j].getType()!= 0)
                {
                    int a = saveCopy[i][j].getType();
                    if (a == saveCopy[i+1][j].getType() && a == saveCopy[i][j+1].getType() && a == saveCopy[i+1][j+1].getType())//erase the duplicates of towers
                    {
                        saveCopy[i+1][j] = null;
                        saveCopy[i][j+1] = null;
                        saveCopy[i+1][j+1] = null;
                    }
                }
            }
        }
        return saveCopy;
    }

    /**
     * Loads the saved textfile of the user with all the towers, tower levels, tower xp and 
     * world values
     *
     * @param file The file to be loaded
     */
    private void load(String file)
    {

        // Use a JOptionPane to get file name from user
        String fileName = file;
        boolean loadedStats = false;
        boolean moreLines = true;
        int numLines = 0;
        int reachedBottom = 0;
        int x = 0;
        int y = 0;
        // If the file opening operation is successful, update the text in the open file button
        try 
        {
            readFile = new Scanner(new File(fileName));

            while(moreLines)
            {
                try{

                    for(int i = 0; i < 6 && !loadedStats; i++)//world values are the first 6 lines
                    {
                        String s = readFile.nextLine();//read next line
                        switch(i)
                        {
                            case 0: 
                            {
                                setMoney(Integer.parseInt(s));
                                break;
                            }
                            case 1: 
                            {
                                setLives(Integer.parseInt(s));
                                break;
                            }
                            case 2: 
                            {
                                setRound(Integer.parseInt(s));
                                break;
                            }
                            case 3:
                            {
                                levelType = Integer.parseInt(s);
                                break;
                            }
                            case 4:
                            {
                                addedHeli = Boolean.parseBoolean(s);
                                break;
                            }
                            case 5:
                            {
                                heliRoundsAlive = Integer.parseInt(s);
                                loadedStats = true;
                            }
                        }
                    }
                    if(loadedStats)//towers
                    {
                        String g = readFile.nextLine();//read next line, add tower based on the first number, second number = level, third = xp.
                        if (!g.equals("null"))
                        {
                            if (g.substring(0, 1).equals("2"))
                            {
                                Tower t = new Wall(2, false,true);
                                addObject(t, x*40+20, y*40+20);
                                editMap(x*40+20,y*40+20,t,1);
                            }
                            if (g.substring(0, 1).equals("0"))
                            {
                                Tower t = new Obstacle();
                                addObject(t, x*40+20, y*40+20);
                                editMap(x*40+20,y*40+20,t,1);
                            }
                            if(g.substring(0, 1).equals("1"))
                            {
                                TowerBase bt = new TowerBase(1, false ,true,Integer.parseInt(g.substring(2,3)),Integer.parseInt(g.substring(4)));
                                addObject(bt, x*40+40, y*40+40);
                                editMap(x*40+40,y*40+40,bt.getTower(),2);
                            }
                            if(g.substring(0, 1).equals("3"))
                            {
                                TowerBase bt = new TowerBase(3, false ,true,Integer.parseInt(g.substring(2,3)),Integer.parseInt(g.substring(4)));
                                addObject(bt, x*40+40, y*40+40);
                                editMap(x*40+40,y*40+40,bt.getTower(),2);
                            }
                        }
                        if(g.substring(0, 1).equals("4"))
                        {
                            TowerBase bt = new TowerBase(4, false ,true,Integer.parseInt(g.substring(2,3)),Integer.parseInt(g.substring(4)));
                            addObject(bt, x*40+40, y*40+40);
                            editMap(x*40+40,y*40+40,bt.getTower(),2);
                        }
                        if(g.substring(0, 1).equals("5"))
                        {                       
                            TowerBase bt = new TowerBase(5, false ,true,Integer.parseInt(g.substring(2,3)),Integer.parseInt(g.substring(4)));
                            addObject(bt, x*40+40, y*40+40);
                            editMap(x*40+40,y*40+40,bt.getTower(),2);
                        }
                        if(g.substring(0, 1).equals("6"))
                        {
                            TowerBase bt = new TowerBase(6, false ,true,Integer.parseInt(g.substring(2,3)),Integer.parseInt(g.substring(4)));
                            addObject(bt, x*40+40, y*40+40);
                            editMap(x*40+40,y*40+40,bt.getTower(),2);
                        }
                        if(g.substring(0, 1).equals("7"))
                        {
                            TowerBase bt = new TowerBase(7, false ,true,Integer.parseInt(g.substring(2,3)),Integer.parseInt(g.substring(4)));
                            addObject(bt, x*40+40, y*40+40);
                            editMap(x*40+40,y*40+40,bt.getTower(),2);
                        }
                        y++;
                        if (y == 14)
                        {
                            y = 0;
                            x++;
                        }
                        numLines++;
                    }
                }
                catch (NoSuchElementException e){
                    moreLines = false;
                }
            }
        }
        catch (FileNotFoundException e) //catches if incorrect file is inputted because it does not exist
        {
            System.out.println("File not Found");
        }
        finally
        {
            if (readFile != null)
            {
                readFile.close();
            }
        }
        //Load background and set max rounds based on level type
        if(levelType == 1)
        {
            setBackground("snow.jpg");
            removeObject(start2);
            maxRounds = 30;
        }
        else if(levelType == 2)
        {
            setBackground("lava.png");
            start.setLocation(start.getX(),start.getY()+80);
            maxRounds = 50;
        }
        else if(levelType == 3)
        {
            setBackground("stone2.png");
            removeObject(start2);
            maxRounds = 60;
        }
        else if(levelType == 4)
        {
            maxRounds = -1;
            removeObject(start2);
            setBackground("sand.jpg");
        }
        if(round < 1)//set total enemy health in wave based on round
            healthInWave = 1000;
        else
            healthInWave=(int)Math.pow(round*50,2);
        totalEnemiesInRound = 5 + round*5;
        if(50-round < 1)//set enemy spawn speed
            enemySpawnInterval = Greenfoot.getRandomNumber(10)+5;
        else
            enemySpawnInterval = Greenfoot.getRandomNumber(50-round)+5;
        if(addedHeli)//add the helicopter if it was previously there
        {
            heli.setRoundsAlive(heliRoundsAlive);
            addObject(heli, 150, 100);
        }
        tempTowers.clear();//clear the tower array list
    }

    public void act()
    {
        setPaintOrder(AchievementPopUp.class,Confusion.class,X.class,Button.class,GameMenu.class,TextButton.class,Scorebar.class,FloatingText.class,WaveStatus.class,UpgradeMenu.class,HealthBar.class,Dragon.class,AirStrike.class, Bomber.class,Helicopter.class,HelicopterGun.class, HeliDisplay.class,Explosion.class,Tower.class,TowerBase.class,Enemy.class,Projectile.class,Start.class,Target.class,PathBall.class);
        if(!gameOver)//while the game is not over
        {
            if(roundStarted)//if the round has started
            {
                if (levelType == 4)//check endless achievements
                    checkEndlessLevel(round);
                if(tempTowers != null)//clear the temporary tower list
                    tempTowers.clear();
                enemiesLoaded = (double)currHealth/healthInWave;//update the wave status with the number of enemies that are spawned
                if(healthInWave > currHealth)
                    waveStatus.update(Color.RED,255,270,(int)(enemiesLoaded*360));
                else
                    waveStatus.update(Color.RED,255,270,360);
                roundComplete++;
                spawnEnemies(healthInWave,enemySpawnInterval);
            }
            else if(!roundStarted && getObjects(Enemy.class).size() == 0)//if round is over
            {
                if(!xpAwarded)//give xp to all the towers placed
                {
                    tempTowers.clear();
                    for(Tower tower : towers)
                    {
                        if(tower!=null)
                            tower.increaseXP(50);
                        //addObject(new FloatingText(5,"+50XP"),tower.getX(),tower.getY());
                    }
                    addMoney(200);//add 200 for completing the round
                    xpAwarded = true;
                    for (UpgradeMenu obj : getObjects(UpgradeMenu.class)) //'getWorld().getObjects(...)' if called from an Actor subclass
                    {
                        UpgradeMenu n = (UpgradeMenu) obj;//update tower xp display if it is open
                        n.setUpdateXp(true);
                    }
                    heliRoundsAlive++;//increment the heli rounds alive
                    for (Helicopter obj : getObjects(Helicopter.class))
                    {
                        Helicopter n = (Helicopter) obj;
                        n.setRoundsAlive(heliRoundsAlive);
                    }
                }
                //save the real values of the game
                realMoney = money;
                realRound = round;
                realLives = lives;
                copy(map,realMap);

                if(round >= maxRounds && maxRounds != -1)//if level is beat
                {
                    setMenuOpen(true);
                    setGameOver(true);//stop loop
                    muteSounds();
                    checkLevelBeat(maxRounds,lives);
                    addObject(new GameMenu(false,muted,round,maxRounds,getObjects(Enemy.class).size()),480,320);//add popup
                    try
                    {
                        Save.saveHighscore(this);//save highscore
                    }
                    catch(IOException e)
                    {
                        System.out.println("Error saving highscores");
                    }
                    try
                    {
                        Save.deleteSave();//delete the save
                    }
                    catch(IOException e)
                    {
                        System.out.println("Error deleting save");
                    }
                }

            }
            //showAchievements();
            if(lives < 1)//you lose
            {
                muteSounds();
                addObject(new GameMenu(true,muted,round,maxRounds,getObjects(Enemy.class).size()),480,320);
                gameOver = true;
                try
                {
                    Save.saveHighscore(this);
                }
                catch(IOException e)
                {
                    System.out.println("Error saving highscore");
                }
                try
                {
                    Save.deleteSave();
                }
                catch(IOException e)
                {
                    System.out.println("Error deleting save");
                }
                //delete the save file to avoid cheating
            }
            if(!draggingTower)//if not dragging a tower, check button presses
            {
                if(Greenfoot.mouseClicked(switchB))
                {
                    switchTowers();
                }
                if(Greenfoot.mouseClicked(startB))
                {
                    startRound();
                }
                if(Greenfoot.mouseClicked(settings) && !getMenuOpen())
                {
                    setMenuOpen(true);
                    setGameOver(true);
                    muteSounds();
                    addObject(new GameMenu(false,muted,round,maxRounds,getObjects(Enemy.class).size()),480,320);
                }
            }

        }
        playMusic();
        showAchievements();//show achievements one by one
        if(lives < 0)//prevent lives from going under 0
            lives = 0;
        otherAchievements();//check other achievements
        if(Greenfoot.isKeyDown("1")) // cheat for money
        {
            if(!achievements[21])
            {
                AchievementPopUp a = new AchievementPopUp("Nice Cheat!");
                achievementList.add(a);
                //addObject(new AchievementPopUp("Nice Cheat!"), getWidth()/2, 0); 
            }
            achievements[21] = true;
            money += 1000;
            try
            {
                Save.saveAchievements(achievements);//save unlocked achievements
            }
            catch(IOException e)
            {
                System.out.println("Error saving achievements");
            }
        }
    }

    /**
     * Check if the current level is beat, and check if lives were lost. 
     * If achievement is unlocked, add it to the queue to be displayed
     *
     * @param rounds The number of rounds survived
     * @param health The health of the user
     */
    public void checkLevelBeat(int rounds, int health)
    {
        if (rounds == 30)//level 1
        {
            if (health == 30)
            {
                if(!achievements[9])
                {
                    AchievementPopUp a = new AchievementPopUp("Beat Level 1 Deathless");
                    achievementList.add(a);
                    //addObject(new AchievementPopUp("Beat Level 1 Deathless"), getWidth()/2, 0); 
                }
                achievements[9] = true;   
            }
            if(!achievements[0])
            {
                AchievementPopUp a = new AchievementPopUp("Beat Level 1");
                achievementList.add(a);
                //addObject(new AchievementPopUp("Beat Level 1"), getWidth()/2, 0); 
            }
            achievements[0] = true;
        }
        else if (rounds == 50)//level 2
        {
            if (health == 30)
            {
                if(!achievements[10])
                {
                    AchievementPopUp a = new AchievementPopUp("Beat Level 2 Deathless");
                    achievementList.add(a);
                    //addObject(new AchievementPopUp("Beat Level 2 Deathless"), getWidth()/2, 0); 
                }
                achievements[10] = true;
            }
            if(!achievements[1])
            {
                AchievementPopUp a = new AchievementPopUp("Beat Level 2");
                achievementList.add(a);
                //addObject(new AchievementPopUp("Beat Level 2"), getWidth()/2, 0); 
            }
            achievements[1] = true;
        }
        else if (rounds == 60)//level 3
        {
            if (health == 30)
            {
                if(!achievements[11])
                {
                    AchievementPopUp a = new AchievementPopUp("Beat Level 3 Deathless");
                    achievementList.add(a);
                    //addObject(new AchievementPopUp("Beat Level 3 Deathless"), getWidth()/2, 0); 
                }
                achievements[11] = true;
            }
            if(!achievements[2])
            {
                AchievementPopUp a = new AchievementPopUp("Beat Level 3");
                achievementList.add(a);
                //addObject(new AchievementPopUp("Beat Level 3"), getWidth()/2, 0); 
            }
            achievements[2] = true;
        }
        if (achievements[2] & achievements[1] &&achievements[0])//if all were unlocked
        {
            if(!achievements[20])//you beat the game!
            {
                AchievementPopUp a = new AchievementPopUp("Finish The Game!");
                achievementList.add(a);
                //addObject(new AchievementPopUp("Finish The Game!"), getWidth()/2, 0); 
            }
            achievements[20] = true;
        }
        try
        {
            Save.saveAchievements(achievements);//save achievements
        }
        catch(IOException e)
        {
            System.out.println("Error saving achievements");
        }
    }

    /**
     * Checks for endless achievements.
     *
     * @param rounds The current number of rounds.
     */
    public void checkEndlessLevel(int rounds)
    {
        if (rounds >= 100)
        {
            if(!achievements[6])
            {
                AchievementPopUp a = new AchievementPopUp("Reach endless level 100");
                achievementList.add(a);
                //addObject(new AchievementPopUp("Reach endless level 100"), getWidth()/2, 0); 
            }
            achievements[6] = true;
        }
        else if (rounds >= 75)
        {
            if(!achievements[5])
            {
                AchievementPopUp a = new AchievementPopUp("Reach endless level 75");
                achievementList.add(a);
                //addObject(new AchievementPopUp("Reach endless level 75"), getWidth()/2, 0); 
            }
            achievements[5] = true;
        }
        else if (rounds >= 50)
        {
            if(!achievements[4])
            {
                AchievementPopUp a = new AchievementPopUp("Reach endless level 50");
                achievementList.add(a);
                //addObject(new AchievementPopUp("Reach endless level 50"), getWidth()/2, 0); 
            }
            achievements[4] = true;
        }
        else if (rounds >= 25)
        {
            if(!achievements[3])
            {
                AchievementPopUp a = new AchievementPopUp("Reach endless level 25");
                achievementList.add(a);
                //addObject(new AchievementPopUp("Reach endless level 25"), getWidth()/2, 0); 
            }
            achievements[3] = true;
        }
        try
        {
            Save.saveAchievements(achievements);//save achievements
        }
        catch(IOException e)
        {
            System.out.println("Error saving achievements");
        }
    }

    /**
     * Checks for other achievements such as number of mines placed, number of enemies killed etc.
     *
     */
    public void otherAchievements()
    {         
        if (money >= 100000)//money achievement
        {
            if(!achievements[7])
            {
                AchievementPopUp a = new AchievementPopUp("Accumulate $100,000");
                achievementList.add(a);
                //addObject(a, getWidth()/2, 0); 
            }
            achievements[7] = true;
        }
        for (Tower t: towers)//check all towers to see if any has more than 9999 xp
        {
            if (t != null)
            {
                if (t.getXP() >= 9999)
                {
                    if(!achievements[8])
                    {
                        AchievementPopUp a = new AchievementPopUp("Earn 9999 XP on a Tower");
                        achievementList.add(a);
                        //addObject(new AchievementPopUp("Earn 9999 XP on a Tower"), getWidth()/2, 0); 

                    }
                    achievements[8] = true;

                }
            }
        }
        if (airStrikeCounter >=10)//air strikes
        {
            if(!achievements[12])
            {
                AchievementPopUp a = new AchievementPopUp("Use AirStrike 10 Times");
                achievementList.add(a);
                //addObject(new AchievementPopUp("Use AirStrike 10 Times"), getWidth()/2, 0); 
            }
            achievements[12] = true;
        }
        if (bomberCounter >=10)//bombers
        {
            if(!achievements[13])
            {
                AchievementPopUp a = new AchievementPopUp("Use Bomber 10 Times");
                achievementList.add(a);
                //addObject(new AchievementPopUp("Use Bomber 10 Times"), getWidth()/2, 0); 
            }
            achievements[13] = true;
        }
        if (mineCounter >=10)//mines
        {
            if(!achievements[14])
            {
                AchievementPopUp a = new AchievementPopUp("Place Mine 10 Times");
                achievementList.add(a);
                //addObject(new AchievementPopUp("Place Mine 10 Times"), getWidth()/2, 0); 
            }
            achievements[14] = true;
        }
        if (demonCounter >=20)//demons killed
        {
            if(!achievements[17])
            {
                AchievementPopUp a = new AchievementPopUp("Kill 20 Demons");
                achievementList.add(a);
                //addObject(new AchievementPopUp("Kill 20 Demons"), getWidth()/2, 0); 
            }
            achievements[17] = true;
        }
        if (dragonCounter >=100)//dragons killed
        {
            if(!achievements[16])
            {
                AchievementPopUp a = new AchievementPopUp("Kill 100 Dragons");
                achievementList.add(a);
                //addObject(new AchievementPopUp("Kill 100 Dragons"), getWidth()/2, 0); 
            }
            achievements[16] = true;
        }
        if (knightCounter >=1000)//knights killed
        {
            if(!achievements[15])
            {
                AchievementPopUp a = new AchievementPopUp("Kill 1000 Knights");
                achievementList.add(a);
                //addObject(new AchievementPopUp("Kill 1000 Knights"), getWidth()/2, 0); 
            }
            achievements[15] = true;
        }
        if (slimeCounter >=100)//slimes killed
        {
            if(!achievements[18])
            {
                AchievementPopUp a = new AchievementPopUp("Kill 100 Slimes");
                achievementList.add(a);
                //addObject(new AchievementPopUp("Kill 100 Slimes"), getWidth()/2, 0); 
            }
            achievements[18] = true;
        }
        if (wizardCounter >=100)//wizards killed
        {
            if(!achievements[19])
            {
                AchievementPopUp a = new AchievementPopUp("Kill 100 Wizards");
                achievementList.add(a);
                //addObject(new AchievementPopUp("Kill 100 Wizards"), getWidth()/2, 0); 
            }
            achievements[19] = true;
        }
        try
        {
            Save.saveAchievements(achievements);//save achievements
        }
        catch(IOException e)
        {
            System.out.println("Error saving achievements");
        }
    }

    /**
     * Shows the achievements one by one
     *
     */
    public void showAchievements()
    {
        achievementTimer++;
        if(achievementList.size() > 0 && achievementTimer > 600)//if timer done, show next achievement
        {
            if(!muted)
                unlocked.play();
            addObject(achievementList.get(achievementList.size()-1),getWidth()/2,0);
            achievementList.remove(achievementList.size()-1);
            achievementTimer = 0;
        }
    }

    /**
     * Mute all the tower sounds in the game
     */
    public void muteSounds()
    {
        for(Tower tower : towers)
        {
            if(tower != null)
                tower.muteSounds();
        }
    }

    /**
     * Called when something in the world changed and we need to recalculate all enemy paths
     *
     */
    public void resetEnemyPaths()
    {
        for (Object obj : getObjects(Enemy.class)) //'getWorld().getObjects(...)' if called from an Actor subclass
        {
            Enemy n = (Enemy) obj;
            n.resetPath();
        }

    }
}
