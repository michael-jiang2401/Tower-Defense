import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Rapidly shoots out ice towards enemies. Slows down enemies. Inherits the tower class.
 * 
 * @author Kevin B, Kevin Y
 * @version January 16th 2019
 */
public class Frost extends Tower
{
    private GreenfootSound wind = new GreenfootSound ("wind.wav");
    private int reload, relTimer, spread, freeze;
    /**
     * Constructs the frost tower with 5 upgrades, a set cost, damage, and fire spread. 
     * Upgrades the tower if it is loaded in with a different upgrade level.
     * 
     * @param towerType The type of the tower
     * @param canDrag Whether or not the tower can be dragged
     * @param valid Whether or not the tower location is valid.
     * @param num The number of upgrades that the tower has
     * @param xp The number of xp
     */
    public Frost(int towerType, boolean canDrag, boolean valid,int num,int xp)
    {
        //initializes variables
        stunned = false;
        confDisplayed = true;
        towerCost = 500;
        trueTowerValue = towerCost;
        towerValue = towerCost * 2/3;
        upgradeCost = 250;
        towerName = "freeze";
        maxUpgrades = 5;
        freeze=1;
        reload = 5;
        this.towerType = towerType;
        isRadiusShowing = false;
        isLocationValid = valid;
        recoil = true;
        this.canDrag = canDrag;
        range = 300;
        recoilSpeed = 1;//larger = faster
        upgrading = false;
        upgradeBarAdded = true;
        spread=40;

        numUpgrades = num;
        realNumUpgrades = num;
        this.xp = xp;
        upgradeOnLoad();
        increaseXP(0);
    }

    /**
     * Upgrades the stats of the tower
     */
    public void upgradeStats()
    {
        reload-=1;
        spread+=10;
        freeze*=2;
    }

    /**
     * Mutes all the sounds that are playing
     */
    public void muteSounds(){
        if(wind.isPlaying())
            wind.stop();
        if(confused.isPlaying())
            confused.stop();
    }

    public void act() 
    {
        if(!((MyWorld)getWorld()).getGameOver())//if the game isn't over, check for upgrades
        {
            checkUpgrade(numUpgrades*100 + 100);
            if(!((MyWorld)getWorld()).getRoundStarted() && getWorld().getObjects(Enemy.class).size() == 0)
            {
                realNumUpgrades = numUpgrades;//save upgrades and xp at the end of the round
                realXp = xp;
            }

            if(!canDrag && isLocationValid && !upgrading)//if the location is valid and tower is not upgrading
            {//target closest enemy and shoot toward it.
                targetClosestEnemy(range);
                if(targetEnemy != null)
                {
                    turnTowards(targetEnemy.getX(), targetEnemy.getY());
                    shoot();
                    wind.setVolume(50);
                    if(!wind.isPlaying() && !((MyWorld)getWorld()).getMuted())
                        wind.play();
                    else if(((MyWorld)getWorld()).getMuted())
                    {
                        muteSounds();
                    }
                }
                else
                {
                    wind.stop();
                }
            }
            else
            {
                muteSounds();
            }

            targetClosestEnemy(300);
            dragAndDrop(0,2);
        }
    }    

    /**
     * Shotes ice at a set speed
     */
    private void shoot()
    {
        if(relTimer > reload)//if the tower is done reloading, shoot at random rotations
        {
            int randomizer=Greenfoot.getRandomNumber(spread)-spread/2;
            FrostBeam frostBeam = new FrostBeam(getRotation(),freeze,this);
            getWorld().addObject(frostBeam,getX(), getY());
            frostBeam.move(45);
            frostBeam.setRotation(frostBeam.getRotation()+randomizer);
            relTimer = 0;
        }
        relTimer++;
    }
}