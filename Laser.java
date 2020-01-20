import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Shoots a powerful beam in one direction. Needs to charge up before shooting.
 * 
 * @author Kevin B, Michael J
 * @version January 16th 2019
 */
public class Laser extends Tower
{
    protected int rechargeTimer,trueTime;
    private boolean canRotate, canShoot;
    private int rotateTimer, checkTimer;
    /**
     * Constructs the laser tower with 4 upgrades, a set cost, damage and shot speed. 
     * Upgrades the tower if it is loaded in with a different upgrade level.
     * 
     * @param towerType The type of the tower
     * @param canDrag Whether or not the tower can be dragged
     * @param valid Whether or not the tower location is valid.
     * @param num The number of upgrades that the tower has
     * @param xp The number of xp
     */
    public Laser(int towerType, boolean canDrag, boolean valid,int num,int xp)
    {
        //Initializes variables
        stunned = false;
        confDisplayed = true;
        towerCost = 1500;
        trueTowerValue = towerCost;
        towerValue = towerCost * 2/3;
        upgradeCost = 250;
        towerName = "laser";
        maxUpgrades = 4;
        damage=100;
        rechargeTimer = 0;
        trueTime = 150;

        this.towerType = towerType;
        isRadiusShowing = false;
        isLocationValid = valid;
        recoil = true;
        this.canDrag = canDrag;
        range = 800;
        recoilSpeed = 1;//larger = faster
        canRotate = true;
        rotateTimer = 0;
        locked = false;
        choosingRotation = false;
        upgrading = false;
        upgradeBarAdded = true;

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
        damage*=1.3;
        trueTime -= 20;
    }

    /**
     * Mutes the sounds that are playing
     */
    public void muteSounds(){
        if(confused.isPlaying())
            confused.stop();
    }

    public void act() 
    {
        //checks upgrades
        if(!((MyWorld)getWorld()).getGameOver())
        {
            checkUpgrade(numUpgrades*100 + 100);
            if(!((MyWorld)getWorld()).getRoundStarted() && getWorld().getObjects(Enemy.class).size() == 0)
            {
                realNumUpgrades = numUpgrades;//updates xp and number of upgrades at the end of the round
                realXp = xp;
            }
            if(!canDrag && isLocationValid && !choosingRotation && !upgrading)//if the tower is placed, target the closest enemy
            {
                if(!locked)
                {
                    targetClosestEnemy(range);
                    if(targetEnemy != null)
                    {
                        if(canRotate)//if can rotate, turn to the closest enemy
                            turnTowards(targetEnemy.getX(), targetEnemy.getY());
                        shoot();
                    }
                }
                else
                {
                    checkTimer++;
                    if(checkTimer > 10)
                    {
                        getWorld().addObject(new BeamHelper(getRotation(),false,this,damage,this),getX(),getY());
                        checkTimer = 0;
                    }
                    if(canShoot)
                    {
                        shoot();
                    }
                }
            }
            if(choosingRotation)
            {
                setRot();
            }
            rotateCooldown();
            dragAndDrop(0,2);
        }
    }    

    /**
     *Sets the rotation of the tower to where the user clicks on the screen. 
     *
     */
    public void setRot()
    {
        if(choosingRotation)//sets the rotation of the tower to the location of the mouse
        {
            MouseInfo mi = Greenfoot.getMouseInfo();
            if (mi!=null)
                turnTowards(mi.getX(), mi.getY());
            if(Greenfoot.mousePressed(null))
            {
                choosingRotation = false;
            }
        }
    }

    /**
     *Sets whether or not the tower can shoot.
     *
     *@param b The boolean allowing the tower to shoot.
     */
    public void setShoot(boolean b)
    {
        canShoot = b;
    }

    /**
     *Returns the recharge time of the tower
     *
     *@return int The time left in the recharge.
     */
    public int getRecharge()
    {
        return rechargeTimer;
    }

    /**
     *Shoots after recharging. Stops moving when shooting.
     *
     */
    public void shoot()
    {
        if(rechargeTimer!=0)//lowers the recharge timer.
        {
            rechargeTimer--;
        }
        else if(rechargeTimer <= 0)//if the tower is done recharging, shoot.
        {
            rechargeTimer=trueTime;
            Beam beam = new Beam(getRotation(),damage,this);
            getWorld().addObject(beam,getX(), getY());
            beam.move(30);
            canRotate = false;
            rotateTimer = 0;
            canShoot = false;
        }
    }

    /**
     *Prevents the tower from moving for a short period of time after shooting.
     *
     */
    public void rotateCooldown()
    {
        rotateTimer++;
        if(rotateTimer > 39)
        {
            canRotate = true;
        }
    }
}
