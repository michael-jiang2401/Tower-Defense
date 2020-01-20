import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/**
 * Displays an upgrade menu for the towers what they are clicked upon. In this menu, you can 
 * upgrade, sell and (for the laser tower only) lock the rotation of the towers. It also 
 * displays the current EXP of the tower and the amount of EXP and money you need to upgrade it.
 * 
 * @author Kevin Biro
 * @version January 16th 2019
 */
public class UpgradeMenu extends Actor
{
    private GreenfootImage menu;
    private TextButton sell, upgrade, lock;
    private boolean added, locked;
    private TextButton[] buttons = new TextButton[3];
    private Tower tower;
    private RangeCircle range;
    private int timer;
    private boolean updateXp;
    /**
     * Creates an upgrade menu with reference to the tower and range circle of the tower. 
     * Initializes the buttons and their display.
     */
    public UpgradeMenu(Tower tower, RangeCircle range)
    {
        updateXp = false;
        this.tower = tower;
        this.range = range;
        //sell = new TextButton("SELL: $" + tower.getTowerValue(), 10);
        if(tower.isTempTower())
        {
            sell = new TextButton("SELL: $" + tower.getTrueTowerValue(), 10);
        }
        else
        {
            sell = new TextButton("SELL: $" + tower.getTowerValue(), 10);
        }
        upgrade = new TextButton("UPGRADE " + tower.getNumUpgrades() + "/" + tower.getMaxUpgrades()
            + "\n" + "Cost: " + tower.getUpgradeCost() + "\nXP: " + tower.getXP() + "/"+tower.getCurrXpToNext(), 17);

        lock = new TextButton("LOCK ROTATION",30);

        buttons[0] = sell;
        buttons[1] = upgrade;
        buttons[2] = lock;
        menu = new GreenfootImage(150,80);
        buildMenu();
        added = false;
        locked = tower.getLocked();
        if(tower.getNumUpgrades() >= tower.getMaxUpgrades())
            upgrade.update("UPGRADE " + tower.getNumUpgrades() + "/" + tower.getMaxUpgrades()+ "\n" + "Tower MAXED");
        if(locked)
            lock.update("ROTATION LOCKED");
    }

    /**
     * Sets the boolean updateXp to what is in the parameter. UpdateXp refreshes the display 
     * of the tower's XP.
     * 
     * @param b The boolean you want updateXp to be.
     */
    public void setUpdateXp(boolean b)
    {
        updateXp = b;
    }

    /**
     * Builds a menu with an upgrade button, sell button and (for laser) lock button. 
     * Draws text on the menu to display the current EXP of the tower and the number of 
     * upgrades unlocked.
     */
    public void buildMenu()
    {
        menu.clear();
        if(tower.getType() == 4)//different size to fit number of buttons (laser has lock button)
            menu = new GreenfootImage(150,150);
        else
            menu = new GreenfootImage(150,110);
        menu.setColor(Color.GRAY);
        menu.fill();
        menu.setTransparency(150);
        GreenfootImage xp = new GreenfootImage(100,20);
        GreenfootImage xpToNext = new GreenfootImage(100,20);//grey rectangle

        xp.setColor(Color.CYAN);//draws xp and upgrades unlocked at top of menu
        xpToNext.setColor(Color.RED);
        if(tower.getXP() < 9999)
            xp.drawString("XP: "+tower.getXP(),0,10);
        else
            xp.drawString("XP: 9999",0,10);
        if(tower.getCurrUpgrades() == tower.getMaxUpgrades())
        {
            xpToNext.drawString("Next: N/A",0,10);
        }
        else
            xpToNext.drawString("Next: "+tower.getXpToNext(),0,10);

        if(updateXp)//updates the xp if true
        {
            if(tower.getNumUpgrades() < tower.getMaxUpgrades()){
                upgrade.update("UPGRADE " + tower.getNumUpgrades() + "/" + tower.getMaxUpgrades() + "\n" + "Cost: " + tower.getUpgradeCost()
                    + "\nXP: " + tower.getXP() + "/"+tower.getCurrXpToNext());
            }
            else
            {
                upgrade.update("UPGRADE " + tower.getNumUpgrades() + "/" + tower.getMaxUpgrades()+ "\n" + "Tower MAXED");
            }
            updateXp = false;
        }

        menu.drawImage(xp,0,0);
        menu.drawImage(xpToNext,55,0);
        menu.setColor(Color.CYAN);
        menu.drawString(tower.getCurrUpgrades() + "/" + tower.getMaxUpgrades(),130,10);
        setImage(menu);
    }

    /**
     * Removes all the buttons in the array
     */
    public void removeButtons()
    {
        for(TextButton button : buttons)
        {
            getWorld().removeObject(button);
        }
    }

    /**
     * Checks for mouse clicks on the buttons and does a specific action depending on the type 
     * of the button. Updates the menu display every 9 acts to reduce lag
     */
    public void act() 
    {
        timer++;
        if(timer % 9 == 0)
        {
            timer = 0;
            buildMenu();
        }
        if(!added)
        {
            if(tower.getType() != 4)
            {
                getWorld().addObject(sell,getX(),getY()-25);
                getWorld().addObject(upgrade,getX(),getY()+20);
            }
            else
            {
                getWorld().addObject(sell,getX(),getY()-40);
                getWorld().addObject(upgrade,getX(),getY()+5);
                getWorld().addObject(lock,getX(),getY()+50);
            }

            added = true;
        }
        if(Greenfoot.mouseClicked(sell))
        {
            if(tower.getType() == 2)//wall
                ((MyWorld)getWorld()).editMap(tower.getX(), tower.getY(),null,1);
            else//other towers
                ((MyWorld)getWorld()).editMap(tower.getX(), tower.getY(),null,2);
            tower.setValidity(false);
            if(((MyWorld)getWorld()).getTempTowers().contains(tower))
            {
                ((MyWorld)getWorld()).addMoney(tower.getTrueTowerValue());
            }
            else
            {
                ((MyWorld)getWorld()).addMoney(tower.getTowerValue());
            }
            tower.muteSounds();
            getWorld().removeObject(tower);
            getWorld().removeObject(range);
            removeButtons();
            getWorld().removeObject(this);
        }
        if(Greenfoot.mouseClicked(upgrade) && ((MyWorld)getWorld()).getMoney() >= tower.getUpgradeCost())
        {
            if(tower.getType() != 2)// if not a wall
            {
                tower.upgrade();
                if(tower.getNumUpgrades() < tower.getMaxUpgrades()){
                    upgrade.update("UPGRADE " + tower.getNumUpgrades() + "/" + tower.getMaxUpgrades() + "\n" + "Cost: " + tower.getUpgradeCost()
                        + "\nXP: " + tower.getXP() + "/"+tower.getCurrXpToNext());
                    if(tower.isTempTower())
                    {
                        sell.update("SELL: $" + tower.getTrueTowerValue());
                    }
                    else
                    {
                        sell.update("SELL: $" + tower.getTowerValue());
                    }
                    
                }
                else
                {
                    upgrade.update("UPGRADE " + tower.getNumUpgrades() + "/" + tower.getMaxUpgrades()+ "\n" + "Tower MAXED");
                }
            }
        }
        if(Greenfoot.mouseClicked(lock))
        {
            if(!locked)
            {
                locked = true;
                range.drawImage(Color.RED);
                tower.setChoosing(true);
                lock.update("ROTATION LOCKED");
                tower.setLocked(locked);
            }
            else
            {
                lock.update("LOCK ROTATION");
                locked = false;
                tower.setLocked(locked);
            }
        }
    }    
}
