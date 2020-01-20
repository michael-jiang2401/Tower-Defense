import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Checks location of mouse when clicking and menu added to determine if the menu was clicked 
 * and if it needs to be removed. The tower sends itself to the radius so that the menus are 
 * customizable based on the tower type.
 * 
 * 
 * @author Kevin Biro
 * @version January 16th 2019
 */
public class RangeCircle extends Actor
{
    //image variables
    private GreenfootImage image;
    //size of radius
    private int range;

    private boolean onRight;
    private boolean remove, locked;
    private boolean addedMenu, placedDown;
    private UpgradeMenu menu;
    private Tower type;

    /**
     * Constructs the range circle with a set size, reference to the Tower calling it, 
     * and whether or not the locked feature is activated
     *
     * @param r The size of the radius
     * @param type The tower that created the range circle
     * @param locked Whether or not the locked feature is activated
     */
    public RangeCircle(int r, Tower type, boolean locked)
    {
        this.type = type;
        menu = new UpgradeMenu(type,this);
        placedDown = false;
        range = r;
        remove = false;
        drawImage(Color.BLACK);
        addedMenu = false;
        this.locked = locked;
    }

    /**
     * Sets the boolean placed down to what is in the parameter
     *
     * @param b Whether or not tower is placed down
     */
    public void setPlacedDown(boolean b)
    {
        placedDown = b;
    }

    /**
     * Creates the radius
     *
     */
    public void drawImage(Color c)
    {
        image = new GreenfootImage(range,range);
        image.setColor(c);
        image.fillOval(0, 0, range,range);
        image.setTransparency(100);
        this.setImage(image);
    }

    public void act()
    {
        MouseInfo mouse = Greenfoot.getMouseInfo();
        if(!addedMenu && getX() < 480 && placedDown)//if radius on left, spawn menu on left
        {
            getWorld().addObject(menu, type.getX()+100, type.getY());
            addedMenu = true;
            onRight = true;
        }
        else if(!addedMenu && getX() >= 480 && placedDown)//if radius on right, spawn menu on right
        {
            getWorld().addObject(menu, type.getX()-100, type.getY());
            addedMenu = true;
            onRight = false;
        }
        //if clicked anywhere but the radius, remove the menu and the radius (for everything but the laser)
        if(Greenfoot.mousePressed(null) && placedDown && !Greenfoot.mousePressed(menu) && ((mouse.getX() < menu.getX()-74 
                || mouse.getX() > menu.getX() +74) || (mouse.getY() < menu.getY() - 54 || mouse.getY() > menu.getY() + 54)) && type.getType() != 4)
        {
            menu.removeButtons();
            getWorld().removeObject(menu);
            getWorld().removeObject(this);
        }
        //if clicked anywhere but the radius, remove the menu and the radius (for laser)
        else if(Greenfoot.mousePressed(null) && placedDown && !Greenfoot.mousePressed(menu) && ((mouse.getX() < menu.getX()-74 
                || mouse.getX() > menu.getX() +74) || (mouse.getY() < menu.getY() - 74 || mouse.getY() > menu.getY() + 74)))
        {
            menu.removeButtons();
            getWorld().removeObject(menu);
            getWorld().removeObject(this);
        }
        if(Greenfoot.mousePressed(null) && !addedMenu)//if clicked anywhere and the menu isn't added
        {
            getWorld().removeObject(this);
        }
    }    
}
