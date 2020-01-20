import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Rapidly shoots out fire towards enemies. Can damage Slime monsters. Inherits the tower class.
 * 
 * @author Kevin B, Michael J
 * @version January 16th 2019
 */
public class Flame extends Tower
{
    private GreenfootSound burning = new GreenfootSound ("burning.wav");
    private int reload, relTimer, spread, damage;
    /**
     * Constructs the flame tower with 5 upgrades, a set cost, damage, and fire spread. 
     * Upgrades the tower if it is loaded in with a different upgrade level.
     * 
     * @param towerType The type of the tower
     * @param canDrag Whether or not the tower can be dragged
     * @param valid Whether or not the tower location is valid.
     * @param num The number of upgrades that the tower has
     * @param xp The number of xp
     */
    public Flame(int towerType, boolean canDrag, boolean valid,int num,int xp)
    {
        //sets variables
        stunned = false;
        confDisplayed = true;
        towerCost = 100;
        trueTowerValue = towerCost;
        towerValue = towerCost * 2/3;
        upgradeCost = 250;
        towerName = "flame";
        maxUpgrades = 5;
        damage=15;
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
        damage*=2;
    }

    /**
     * Mutes the sounds that are playing
     */
    public void muteSounds()
    {
        if(burning.isPlaying())
            burning.stop();
        if(confused.isPlaying())
            confused.stop();
    }

    public void act() 
    {
        if(!((MyWorld)getWorld()).getGameOver())//if the game is still playing
        {
            checkUpgrade(numUpgrades*100 + 100);//check for upgrades
            if(!((MyWorld)getWorld()).getRoundStarted() && getWorld().getObjects(Enemy.class).size() == 0)
            {
                realNumUpgrades = numUpgrades;//save upgrades and xp at the end of the round
                realXp = xp;
            }
            if(!canDrag && isLocationValid && !upgrading)
            {
                targetClosestEnemy(range);//target closest enemy and shoot towards it
                if(targetEnemy != null)
                {
                    turnTowards(targetEnemy.getX(), targetEnemy.getY());
                    shoot();
                    burning.setVolume(60);
                    if(!burning.isPlaying() && !((MyWorld)getWorld()).getMuted())
                        burning.play();
                    else if(((MyWorld)getWorld()).getMuted())
                    {
                        muteSounds();
                    }
                }
                else
                {
                    burning.stop();
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
     * Shoots fire at a set speed.
     */
    private void shoot()
    {
        if(relTimer > reload)//if the tower is done reloading
        {
            int randomizer=Greenfoot.getRandomNumber(spread)-spread/2;//shoot fire at random directions
            Fire fire = new Fire(getRotation(),damage,this);
            getWorld().addObject(fire,getX(), getY());
            fire.move(55);
            fire.setRotation(fire.getRotation()+randomizer);
            relTimer = 0;
        }
        relTimer++;
    }
}
