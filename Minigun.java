import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Rapidly fires projectiles towards enemies.
 * 
 * @author Kevin B, Michael J
 * @version January 16th 2019
 */
public class Minigun extends Tower
{
    private int reload, relTimer;
    /**
     * Constructs the minigun tower with 3 upgrades, a set cost, damage and shot speed. 
     * Upgrades the tower if it is loaded in with a different upgrade level.
     * 
     * @param towerType The type of the tower
     * @param canDrag Whether or not the tower can be dragged
     * @param valid Whether or not the tower location is valid.
     * @param num The number of upgrades that the tower has
     * @param xp The number of xp
     */
    public Minigun(int towerType, boolean canDrag, boolean valid, int num,int xp)
    {
        //initializes all the important variables
        stunned = false;
        confDisplayed = true;
        towerCost = 100;
        trueTowerValue = towerCost;
        towerValue = towerCost * 2/3;
        upgradeCost = 250;
        towerName = "minigun";

        maxUpgrades=3;
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
        damage=50;

        numUpgrades = num;
        realNumUpgrades = num;
        this.xp = xp;
        upgradeOnLoad();
        increaseXP(0);
    }

    /**
     *Upgrades the stats of the tower
     *
     */
    public void upgradeStats()
    {
        reload-=1;
        damage*=2;
        range+=50;
    }

    public void act() 
    {
        //checks upgrades
        if(!((MyWorld)getWorld()).getGameOver())
        {
            checkUpgrade(numUpgrades*100 + 100);
            if(!((MyWorld)getWorld()).getRoundStarted() && getWorld().getObjects(Enemy.class).size() == 0)
            {
                realNumUpgrades = numUpgrades;
                realXp = xp;
            }
            if(!canDrag && isLocationValid && !upgrading)//targets enemies.
            {
                targetClosestEnemy(range);
                if(targetEnemy != null)
                {
                    turnTowards(targetEnemy.getX(), targetEnemy.getY());
                    shoot();
                }
            }
            targetClosestEnemy(range);
            dragAndDrop(0,2);//drag and drop functionality
        }
    }

    /**
     *Shoots projectiles in random directions very quickly.
     *
     */
    public void shoot()
    {

        if(reload<=0)//if the tower has reloaded
        {
            int randomizer=Greenfoot.getRandomNumber(20)-10; 
            MinigunBullet bullet = new MinigunBullet(this.getRotation()+randomizer, damage,this);
            getWorld().addObject(bullet, getX(), getY());
            bullet.move(30);
            reload=3;
        }
        else//reload
        {
            reload--;
        }

    }

    /**
     *Mutes all the sounds that are playing
     *
     */
    protected  void muteSounds()
    {
        if(confused.isPlaying())
            confused.stop();
    }
}
