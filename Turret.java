import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/**
 * Shoots one powerful bullet towards enemies.Inherits the tower class
 * 
 * @author Kevin Biro
 * @version January 16th 2019
 */
public class Turret extends Tower
{
    /**
     * Constructs the turret with 5 upgrades, a set cost, damage, and fire spread. 
     * Upgrades the tower if it is loaded in with a different upgrade level.
     * 
     * @param towerType The type of the tower
     * @param canDrag Whether or not the tower can be dragged
     * @param valid Whether or not the tower location is valid.
     * @param num The number of upgrades that the tower has
     * @param xp The number of xp
     */
    public Turret(int towerType, boolean canDrag, boolean valid, int num,int xp)
    {
        //initializes variables
        stunned = false;
        confDisplayed = true;
        towerCost = 500;
        trueTowerValue = towerCost;
        towerValue = towerCost * 2/3;
        upgradeCost = 250;
        towerName = "turret";

        this.towerType = towerType;
        isRadiusShowing = false;
        isLocationValid = valid;
        recoil = true;
        this.canDrag = canDrag;
        range = 300;
        recoilSpeed = 1;//larger = faster
        maxUpgrades=5;

        upgrading = false;
        upgradeBarAdded = true;
        damage = 1000;

        numUpgrades = num;
        realNumUpgrades = num;
        this.xp = xp;
        upgradeOnLoad();
        increaseXP(0);
    }

    /**
     *Mutes sounds that are playing
     */
    public void muteSounds(){
        if(confused.isPlaying())
            confused.stop();
    }

    /**
     *Shoots if the tower has reloaded and returned to its original position.
     */
    public void shoot()
    {
        if(diff == 0)
        {
            recoil = false;
            getWorld().addObject(new Rocket(getRotation(), damage,this), getX(), getY());
        }
    }

    /**
     *Recoils the tower when it shoots by sending it backwards.
     */
    public void recoil()
    {
        if(!recoil)
        {
            setLocation(x,y);
            diff = 20;
            move(-diff);
            recoil = true;
        }
        else if(diff != 0)
        {
            turnTowards(x,y);
            if(diff - recoilSpeed < 0)//go back to original position if recoil over
            {
                diff = 0;
                setLocation(x,y);
            }
            else//move towards the original position
            {
                move(recoilSpeed);
                diff -= recoilSpeed;
            }
        }
    }

    public void act() 
    {
        if(!((MyWorld)getWorld()).getGameOver())//if the game is still playing
        {
            checkUpgrade(numUpgrades*100 + 100);//check upgrades
            if(!((MyWorld)getWorld()).getRoundStarted() && getWorld().getObjects(Enemy.class).size() == 0)
            {
                realNumUpgrades = numUpgrades;//save upgrades and xp
                realXp = xp;
            }
            if(!canDrag && isLocationValid && !upgrading)
            {
                targetClosestEnemy(range);//target the closest enemy if the tower is placed
                if(targetEnemy != null)
                {
                    turnTowards(targetEnemy.getX(), targetEnemy.getY());//shoot at enemy
                    shoot();
                }
            }
            recoil();//recoil the tower at the set recoil speed
            dragAndDrop(0,2);
        }
    }    
}
