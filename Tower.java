import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
import java.util.List;
/**
 * Contains all the tower code to shoot towards the nearest enemy, upgrade stats based on exp, 
 * and all the tower dragging the path checking code to determine when the user is dragging a tower 
 * and which locations are valid.
 * 
 * @author Kevin Biro
 * @version January 16th 2019
 */
public abstract class Tower extends Actor
{
    protected boolean stunned;//for when the tower is stunned by the wizard
    protected boolean confDisplayed;
    protected int stunTimer;
    protected int x,y;
    protected int diff, recoilSpeed;
    protected boolean recoil, canDrag, isLocationValid, isRadiusShowing,dragged;
    protected int range, towerType;

	//sounds
    protected GreenfootSound confused = new GreenfootSound ("confused.wav");
    protected GreenfootSound invalid = new GreenfootSound ("Invalid.wav");

	//for enemy targetting
    protected Enemy targetEnemy;
    protected ArrayList<Enemy> enemy;
    protected ArrayList<Integer>indexOfDeadEnemy;

    protected boolean canCopy = false;
    protected RangeCircle radius;
    //prices of the tower
    protected int towerCost;
    protected int towerValue, trueTowerValue;
    protected int damage;
    protected boolean locked;

	//upgrade variables
    protected boolean upgrading,upgradeBarAdded;
    protected int upgradeTimer;
    protected HealthBar bar;
    protected int numUpgrades, upgradeCost, maxUpgrades, realNumUpgrades;
    protected int currUpgrades = 1;
    protected int xp, realXp;
    protected String towerName;//the name of the upgrade images

    protected boolean choosingRotation, takesTime;
    /**
     * Mutes the sounds in the method if they are playing. Required to be a tower.
     */
    protected abstract void muteSounds();//to ensure that all the towers can mute their sounds

    /**
     * Stuns the tower, stops its sounds and shooting.
     */
    public void stun()
    {
        if(!((MyWorld)getWorld()).getMuted())
            confused.play();
        stunned = true;
        confDisplayed = false;
    }
    
    /**
     * Returns whether or not the tower is stunned
     * 
     * @return boolean The stun condition of the tower
     */
    public boolean getStun()
    {
        return stunned;
    }
    
    /**
     * Checks if the tower is stunned, increases the stun timer until it reaches the limit. 
     * Once it reaches the limit, the tower is no longer stunned.
     */
    protected void checkStun()
    {
        if (stunned)
        {
            stunTimer ++;
            if (stunTimer >= 200)
            {
                stunned = false;
                stunTimer = 0;
            }
        }
    }
    
    /**
     * Changes the image of the tower based on its current upgrade level
     */
    protected void upgradeImage()
    {
        setImage(new GreenfootImage("" + towerName + numUpgrades + ".png"));
    }
    
    /**
     * Adds experience to the tower and increases the number of upgrades that the tower can do 
     * when a certain amount of exp has been gained.
     * 
     * @param xpGained The amount of exp increase to the tower
     */
    protected void increaseXP(int xpGained)
    {
        xp+= xpGained;
        if(xp >= currUpgrades*currUpgrades*50 && currUpgrades < maxUpgrades)
        {
            currUpgrades++;
        }
    }

    /**
     * Checks if the tower is temporary (to resell at full cost) 
     * 
     * @return boolean Whether or not the tower is temporary
     */
    protected boolean isTempTower()
    {
        if(((MyWorld)getWorld()).getTempTowers().contains(this))
        {
            return true;
        }
        return false;
    }

    /**
     * Returns the amount of damage that the tower does
     * 
     * @return int The damage of the tower
     */
    protected int getDamage()
    {
        return damage;
    }

    /**
     * Returns the number of upgrades that the tower can make
     * 
     * @return int The number of upgrades available
     */
    protected int getCurrUpgrades()
    {
        return currUpgrades;
    }

    /**
     * Returns the number of xp of the tower
     * 
     * @return int The number of exp that the tower has
     */
    protected int getXP()
    {
        return xp;
    }

    /**
     * Returns the number of xp to the next upgrade to unlock
     * 
     * @return The required exp for the next upgrade 
     */
    protected int getXpToNext()
    {
        return currUpgrades*currUpgrades*50;
    }

    /**
     * Returns the current number of xp for the current upgrade
     * 
     * @param int The exp for the current upgrade
     */
    protected int getCurrXpToNext()
    {
        return numUpgrades*numUpgrades*50;
    }

    /**
     * Increases the number of upgrades if it is less than the maximum
     */
    protected void increaseUpgradePath()
    {
        if(currUpgrades < maxUpgrades)
            currUpgrades++;
    }

    /**
     * Returns whether or not the tower is touching the towerbar
     * 
     * @return boolean Whether or not tower is touching the towerBar
     */
    protected boolean getTouchingBar()
    {
        if(isTouching(TowerBar.class))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Checks the upgrade of the tower, adds a loading bar if it is during the round. Upgrade is 
     * instant if it is at round end. If the upgrade time is over, the tower upgrades its stats and 
     * image.
     * 
     * @param upgradeTime The time of the upgrade in acts
     * 
     */
    protected void checkUpgrade(int upgradeTime)
    {
        if(!((MyWorld)getWorld()).getRoundStarted() && getWorld().getObjects(Enemy.class).size() == 0)
        {//if the round is over, upgrade takes no time
            takesTime = false;
            upgradeTime = 0;
        }
        if(!upgradeBarAdded)
        {//adds an upgrade bar if one hasn't already been added
            upgradeTimer = 0;
            bar = new HealthBar(upgradeTime,this);
            getWorld().addObject (bar, getX(), getY()-30);
            upgradeBarAdded = true;
            upgrading = true;
        }
        if(upgrading)
        {//upgrades the tower if the time allows it.
            bar.update(upgradeTimer);
            upgradeTimer++;
            if(upgradeTimer > upgradeTime)
            {
                getWorld().removeObject(bar);
                range*=1.08;
                damage*=2;
                if(recoilSpeed < 5)
                    recoilSpeed++;
                upgrading = false;
                //change image
                upgradeImage();
                upgradeStats();
                takesTime = true;
            }
        }
    }

    /**
     * Initiates the upgrade of the tower and changes upgrade costs.
     */
    protected void upgrade()
    {
        if(!upgrading && numUpgrades < currUpgrades)//if the current number of upgrades is less than the maximum
        {
            if(bar != null)
            {
                getWorld().removeObject(bar);
            }
            ((MyWorld)getWorld()).loseMoney(upgradeCost);
            upgradeBarAdded = false;
            numUpgrades++;
            towerValue += towerCost * 1/3;
            trueTowerValue += upgradeCost;
            upgradeCost += 200;
        }
    }

    /**
     * Upgrades the towers to a certain level. Only used when loading the tower into the game.
     */
    protected void upgradeOnLoad()
    {
        if(numUpgrades <= maxUpgrades)
        {
            for(int i = 0; i < numUpgrades; i++)//upgrade the tower instantly
            {
                towerValue += towerCost * 1/3;
                trueTowerValue += upgradeCost;
                upgradeCost += 200;
                range*=1.08;
                damage*=2;
                if(recoilSpeed < 5)
                    recoilSpeed++;
                upgradeImage();
                upgradeStats();
                increaseXP(0);
            }
        }
    }

    /**
     * Returns the tower value from the beginning of the round.
     * 
     * @return int The tower value ($)
     */
    protected int getTrueTowerValue()
    {
        return trueTowerValue;
    }

    /**
     * Returns the status of the tower (type, upgrade number, exp)
     * 
     * @param String A formatted output of all the tower's information
     */
    protected String getStats()
    {
        return towerType + "," + realNumUpgrades + "," + realXp;
    }

    /**
     * Returns the maximum number of upgrades that a tower can make.
     * 
     * @return int The max upgrades of the tower
     */
    protected int getMaxUpgrades()
    {
        return maxUpgrades;
    }

    /**
     * Upgrades the stats of the tower. Each tower has a different upgrade so this ensures that 
     * when calling this method, all the towers will have it.
     */
    protected void upgradeStats()
    {}

    /**
     * Returns the number of upgrades of a tower.
     * 
     * @return int The number of upgrades a tower has.
     */
    protected int getNumUpgrades()
    {
        return numUpgrades;
    }

    /**
     * Returns the upgrade cost of a tower
     * 
     * @return int The upgrade cost for a tower upgrade.
     */
    protected int getUpgradeCost()
    {
        return upgradeCost;
    }

    /**
     * Sets the tower home coordinates when it is added to the world.
     * 
     * @param w The world being added to
     */
    protected void addedToWorld(World w)
    {
        x = getX();
        y = getY();
    }

    /**
     * Sets whether or not the tower is choosing its rotation
     * 
     * @param b The boolean being set to the choosingRotation variable
     */
    protected void setChoosing(boolean b)
    {
        choosingRotation = b;
    }

    /**
     * Sets whether or not a tower is locked
     * 
     * @param b The value set to locked
     */
    protected void setLocked(boolean b)
    {
        locked = b;
    }

    /**
     * Returns whether or not a tower is locked
     * 
     * @return boolean The lock status of the tower.
     */
    protected boolean getLocked()
    {
        return locked;
    }

    /**
     * Returns the tower cost
     * 
     * @return int The cost of the tower.
     */
    protected int getTowerCost()
    {
        return towerCost;
    }

    /**
     * Returns the tower value (salvage value)
     * 
     * @return int The salvage value of the tower when sold
     */
    protected int getTowerValue()
    {
        return towerValue;
    }

    /**
     * Returns whether of not the tower can be dragged
     * 
     * @return Whether or not the tower can be dragged.
     */
    protected boolean getCanDrag()
    {
        return canDrag;
    }

    /**
     * Returns if the tower location is valid
     * 
     * @return boolean The location validity of the tower.
     */
    protected boolean getValidity()
    {
        return isLocationValid;
    }

    /**
     * Sets the validity of the tower.
     * 
     * @param b The validity of the tower being set.
     */
    protected void setValidity(boolean b)
    {
        isLocationValid = b;
    }

    /**
     * Sets Whether or not the radius is showing
     * 
     * @param b Whether or not the radius is showing
     */
    protected void setRadiusShowing(boolean b)
    {
        isRadiusShowing = b;
    }

    /**
     * Returns the type of the tower
     * 
     * @return int The type of the towers.
     */
    protected int getType()
    {
        return towerType;
    }

    
    /**
     * Sets if the tower can be copied
     * 
     * @param b Whether or not the tower can get copied.
     */
    protected void setCanCopy(boolean b)
    {
        canCopy = b;
    }

    /**
     * The drag and drop functionality of the tower. Towers snap to the grid and cannot be dragged 
     * outside of the visible area. When clicked upon, towers show their radius which is either 
     * red (invalid location) or gray. When the tower is let go in a valid location, it can no 
     * longer be dragged.
     * 
     * @param offset The offset on the grid of the game theat the tower is moved by (on x,y)
     * @param size The size of the tower (towers and walls have a different size)
     */
    protected void dragAndDrop(int offset, int size)
    {
        if(Greenfoot.mousePressed(this) && !isRadiusShowing)//shows the tower radius
        {
            radius = new RangeCircle(range,this,locked);
            getWorld().addObject(radius, x, y);
            isRadiusShowing = true;
            if(!canDrag)
                radius.setPlacedDown(true);
        }
        else if(Greenfoot.mousePressed(null))//hides the radius when not clicked anywhere
        {
            isRadiusShowing = false;
        }

        if(Greenfoot.mousePressed(this) && canDrag)//deducts money when clicked
        {
            if (((MyWorld)getWorld()).getMoney() >= towerCost)//if user has money, copy the tower
            {
                setCanCopy(true);
                ((MyWorld)getWorld()).loseMoney(towerCost);
                makeCopy();
                isLocationValid = false;
            }
            else
            {
                setCanCopy(false);
            }
        }
        //snap to grid, prevention to be out of the map, colors the map.
        if (Greenfoot.mouseDragged(this) && canDrag && canCopy)
        {
            MouseInfo mouse = Greenfoot.getMouseInfo();

            setLocation(((int)mouse.getX()/40)*40+offset, ((int)mouse.getY()/40)*40+offset);

			//boundaries of the world
			//keeps the tower in the game (prevents cases when tower is halfway out of the screen)
            if(getX()<20 && getY() < 20 && towerType != 2)
                setLocation(40,40);
            else if(getX()<20 && getY() < 20)
                setLocation(20,20);
            if(getX()>940 && getY() < 20 && towerType != 2)
                setLocation(920,40);
            else if(getX()>940 && getY() < 20)
                setLocation(940,20);
            if(getX()<20 && towerType != 2)
                setLocation(40,((int)mouse.getY()/40)*40+offset);
            else if(getX() < 20)
                setLocation(20,((int)mouse.getY()/40)*40+offset);
            if(getX()>940 && towerType != 2)
                setLocation(920,((int)mouse.getY()/40)*40+offset);
            else if(getX() > 940)
                setLocation(940,((int)mouse.getY()/40)*40+offset);
            if(getY() < 20 && towerType != 2)
                setLocation(((int)mouse.getX()/40)*40+offset,40);
            else if(getY() < 20)
                setLocation(((int)mouse.getX()/40)*40+offset,20);
            if(getY() > 620 && towerType != 2)
                setLocation(((int)mouse.getX()/40)*40+offset,600);
            else if(getY() > 620)
                setLocation(((int)mouse.getX()/40)*40+offset,620);
            radius.setLocation(getX(),getY());

			//check collisions with walls, towers, target and start to determine valid locations
            dragged = true;
            ((MyWorld)getWorld()).setDragging(dragged);
            Target ta = (Target)getOneIntersectingObject(Target.class);
            Start s = (Start)getOneIntersectingObject(Start.class);
            Tower t = (Tower)getOneIntersectingObject(Tower.class);
            Wall w = (Wall)getOneIntersectingObject(Wall.class);
            Obstacle o = (Obstacle)getOneIntersectingObject(Obstacle.class);
            if(t != null || s != null || ta != null || getY() > 550)
            {
                radius.drawImage(Color.RED);
                isLocationValid = false;
            }
            else
            {
                radius.drawImage(Color.BLACK);
                isLocationValid = true;
            }
        }
        if(Greenfoot.mouseClicked(null) && canDrag && canCopy)//if tower is placed
        {
            ((MyWorld)getWorld()).setDragging(false);//if the location placed isnt valid
            if(!isLocationValid)
            {
                ((MyWorld)getWorld()).addMoney(towerCost);//give the money back to the user
                getWorld().addObject(new X(), getX(), getY());//add an X to the invalid location
                if(!((MyWorld)getWorld()).getMuted())
                    invalid.play();
                getWorld().removeObject(this);//remove the tower
            }
            else if(isLocationValid)
            {
                x = getX();
                y = getY();
                if(dragged)
                {
                    ((MyWorld)getWorld()).setLastTower(this);//change the world map to adjust the enemy path
                    ((MyWorld)getWorld()).editMap(x,y,this,size);
                }
            }
            if(dragged)//if the one being dragged is let go, prevent it from being dragged again
            {
                canDrag = false;

            }
        }
    }

    /**
     * Makes a copy of the tower with the same parameters
     */
    protected void makeCopy()
    {
        getWorld().addObject(new TowerBase(towerType,true,true,numUpgrades,xp),getX(),getY());
    }

    /**
     * Targets the closest non dead enemy if not stunned
     * 
     * @param range The range of the tower.
     */
    protected void targetClosestEnemy (int range)
    {
        enemy = (ArrayList)getObjectsInRange(range/2, Enemy.class);
        indexOfDeadEnemy = new ArrayList<Integer>();
        if (enemy.size() > 0 && !stunned)
        {
            // set the first one as my target
            for(Enemy e : enemy)//checks if enemy is dead
            {
                if(e.getDead())
                {
                    Integer i = enemy.indexOf(e);
                    indexOfDeadEnemy.add(i);
                }
            }
            for(Integer i : indexOfDeadEnemy)//removes dead enemies from the possible targets
            {
                //System.out.println(i.intValue());
                try
                {
                    enemy.remove(i.intValue());
                }
                catch(IndexOutOfBoundsException e)
                {
                    targetEnemy = null;
                }
            }
            if(enemy.size() > 0)
                targetEnemy = enemy.get(0);
            else
                targetEnemy = null;
        }
        else
        {
            targetEnemy = null;
            checkStun();
        }
        if(!confDisplayed)//adds a confusion object if confused.
        {
            getWorld().addObject(new Confusion(),x,y);
            confDisplayed = true;
        }
    }
}
