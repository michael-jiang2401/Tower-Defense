import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Tower that can shoot rings of bullets around itself. Inherits the tower class
 * 
 * @author Kevin Yang
 * @version Dec 13
 */
public class RingTurret extends Tower
{
    private GreenfootSound flame = new GreenfootSound ("fire.wav");
    /**
     * Constructs the ring turret with 5 upgrades, a set cost, damage, shot speed. 
     * Upgrades the tower if it is loaded in with a different upgrade level.
     * 
     * @param towerType The type of the tower
     * @param canDrag Whether or not the tower can be dragged
     * @param valid Whether or not the tower location is valid.
     * @param num The number of upgrades that the tower has
     * @param xp The number of xp
     */
    public RingTurret(int towerType, boolean canDrag, boolean valid, int num,int xp)
    {
        //initializes variables
        stunned = false;
        confDisplayed = true;
        towerCost = 100;
        trueTowerValue = towerCost;
        towerValue = towerCost * 2/3;
        upgradeCost = 250;
        towerName = "ringTurret";
        damage = 20;
        flame.setVolume(90);

        this.towerType = towerType;
        isRadiusShowing = false;
        isLocationValid = valid;
        recoil = true;
        this.canDrag = canDrag;
        range = 200;
        recoilSpeed = 2;//larger = faster

        upgrading = false;
        upgradeBarAdded = true;
        maxUpgrades = 5;
        numUpgrades = num;
        realNumUpgrades = num;
        this.xp = xp;
        upgradeOnLoad();
        increaseXP(0);
    }

    /**
     *Mutes the sounds that are playing
     *
     */
    public void muteSounds(){
        if(confused.isPlaying())
            confused.stop();
    }

    /**
     *Shoots fire projectiles around the tower at the same time.
     *
     */
    public void shoot()
    {
        if(diff <= 0)
        {
            diff = 50;
            if(!((MyWorld)getWorld()).getMuted())
                flame.play();
            //spawns fire projectiles around the tower
            getWorld().addObject(new FireProjectile(0,10,this), getX(), getY());
            getWorld().addObject(new FireProjectile(22,10,this), getX(), getY());
            getWorld().addObject(new FireProjectile(45,10,this), getX(), getY());
            getWorld().addObject(new FireProjectile(67,10,this), getX(), getY());
            getWorld().addObject(new FireProjectile(90,10,this), getX(), getY());
            getWorld().addObject(new FireProjectile(112,10,this), getX(), getY());
            getWorld().addObject(new FireProjectile(135,10,this), getX(), getY());
            getWorld().addObject(new FireProjectile(157,10,this), getX(), getY());
            getWorld().addObject(new FireProjectile(180,10,this), getX(), getY());
            getWorld().addObject(new FireProjectile(202,10,this), getX(), getY());
            getWorld().addObject(new FireProjectile(225,10,this), getX(), getY());
            getWorld().addObject(new FireProjectile(247,10,this), getX(), getY());
            getWorld().addObject(new FireProjectile(270,10,this), getX(), getY());
            getWorld().addObject(new FireProjectile(292,10,this), getX(), getY());
            getWorld().addObject(new FireProjectile(315,10,this), getX(), getY());
            getWorld().addObject(new FireProjectile(337,10,this), getX(), getY());
            getWorld().addObject(new FireProjectile(360,10,this), getX(), getY());
        }
    }

    /**
     *Upgrades the stats of the tower
     *
     */
    public void upgradeStats()
    {
        recoilSpeed += 1;
        damage*=1.5;
    }

    
    public void act() 
    {
        if(!((MyWorld)getWorld()).getGameOver())//if the game isnt over
        {
            checkUpgrade(numUpgrades*100 + 100);//check for upgrades
            if(!((MyWorld)getWorld()).getRoundStarted() && getWorld().getObjects(Enemy.class).size() == 0)
            {
                //save upgrades and xp of the tower at the end of the round
                realNumUpgrades = numUpgrades;
                realXp = xp;
            }
            //shoot if the location is valid if there is an enemy in range
            if(!canDrag && isLocationValid && !upgrading)
            {
                diff -= recoilSpeed;
                targetClosestEnemy(range);
                try
                {
                    if (targetEnemy != null)
                    {
                        shoot();
                    }
                }
                catch(IllegalStateException e){}
                catch(NullPointerException e){}            
            }
            dragAndDrop(0,2);
        }
    }     
}
