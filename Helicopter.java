import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * This is a helicopter that flies around the map and can be controlled by the user to 
 * shoot at the location of the mouse
 * 
 * @author Michael 
 * @version 18/01/2019
 */
public class Helicopter extends Actor
{
    private int timer=0;//timer between shots
    private int frame = 1;
    private int movement=0;
    private boolean rotating= false;//whether or not the helicopter is currently rotating
    private boolean horizontal=true;//whether or not the helicopter is flying straight
    private int rotation=0;//inital rotation
    private HelicopterGun gun = new HelicopterGun(this);
    private boolean addedGun = false;
    private int roundsAlive;
    private int flyInCounter=0;//counter for when helicopter is flying in
    
    /**
     * Creates a helicopter which flies around and can shoot
     * 
     * @param roundsAlive The amount of rounds the helicopter has been alive so that it 
     * can be properly loaded from a save file
     */
    public Helicopter(int roundsAlive)
    {
        this.roundsAlive = roundsAlive;
    }
    /**
     * Act - do whatever the Helicopter wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        //adds gun which sticks onto the helicopter
        if(!addedGun)
        {
            getWorld().addObject(gun, getX(), getY());
            gun.addedToWorld();
        }
        animate();//animates the helicopter
        if(roundsAlive > 4)//makes helicopter fly away after 5 rounds
        {
            flyAway();
        }
        else if(flyInCounter<=150)//makes the helicopter fly into the edge of the map before
        //performing regular movement cylce
        {
            move(1);
            flyInCounter++;
        }
        else
        {
            moveCycle();//performs movement cylce
        }
    }    
    /**
     * Makes the helicopter fly away after 5 rounds 
     */
    public void flyAway()
    {
        turnTowards(480,0);
        move(2);
        if(isAtEdge())
        {
            ((MyWorld)getWorld()).setAddedHeli(false);
            ((MyWorld)getWorld()).setHeliRounds(0);
            ((MyWorld)getWorld()).resetHeliButton();
            gun.removeHB();
            getWorld().removeObject(gun);
            getWorld().removeObject(this);
        }
    }
    /**
     * Sets the rounds alive value
     * 
     * @param num The number of rounds that the helicopter has been alive
     */
    public void setRoundsAlive(int num)
    {
        roundsAlive=num;
    }
    /**
     * sets the rotation of the helicopter 
     */
    public void rotate()
    {
        setRotation(getRotation()+1);
        move(1);
    }
    
    /**
     * makes the helicopter move in a rectangular pattern
     */
    public void moveCycle()
    {
        if(horizontal)//checks if the helicopter is flying horizontally
        {
            if(rotating)//checks if the helicopter is rotating
            {
                if(rotation>=90)//causes the helicopter to turn until it has turned 90 degrees
                {
                    rotation=0;
                    rotating=false;
                    horizontal = false;
                }
                else
                {
                    rotate();//roates helicopter
                    rotation++;
                }

            }
            else
            {
                if(movement==600)//causes the helicopter to turn once it has travelled a certain distance horizontally
                {
                    movement=0;
                    rotating=true;
                }
                else
                {
                    move(1); //moves until it has reached horizontal movement limit
                    movement++;
                }
            }
        }
        else//makes helicopter move vertically 
        {
            if(rotating)//makes helicopter slowly turn 90 degrees
            {
                if(rotation>=90)
                {
                    rotation=0;
                    rotating=false;
                    horizontal=true;
                }
                else
                {
                    rotate();
                    rotation++;
                }

            }
            else
            {
                if(movement==200)//causes helicopter to turn after travelling specified distance
                {
                    movement=0;
                    rotating=true;
                }
                else
                {
                    move(1);
                    movement++;
                }
            }
        }
    }
    /**
     * animates the helicopter 
     */
    public void animate()
    {
        if(timer>3)
        {
            timer=0;
            setImage("heli2-" + frame + ".png");//sets the image to the next frame every 3 acts
            if(frame==4)//on the fourth frame reset and start from frame 1
            {
                frame=1;
            }
            else
            {
                frame++;
            }

        }
        else
        {
            timer++;
        }
    }
}
