import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
import java.io.FileNotFoundException;
/**
 * Enemy is a class that moves along a path in order to get to the player’s 
 * base. The goal of the enemy is to kill the player’s base if it manages to 
 * reach the player’s base. Enemy is able to recalculate its path location 
 * when changes are made to the terrain. Enemies must stay on path. 
 * They lose health off path.
 * 
 * @author Kevin Biro
 * @version January 16th 2019
 */
public abstract class Enemy extends Actor
{
    protected Node[] pathToTarget;
    protected int indexInPath; // where in the cached path we are going towards
    //previous location
    protected int prevTargetX;
    protected int prevTargetY;
    protected int prevMyX;
    protected int prevMyY;
    //variables for their abilities
    protected int health;
    protected int speed, sDuration, speedCount;
    protected int originalSpeed, counter;
    //target and hpBar
    protected Target target;
    protected MyWorld myWorld;
    protected HealthBar hpBar;
    //bath finding stuff
    protected ArrayList<PathBall> path;
    protected int towerCollisionTimer;
    //amount of money
    protected int killMoney;
    //enemy type
    protected String enemyType;
    protected int type;
    protected boolean dead;
    //image stuff
    protected GreenfootImage image;
    protected int frame;
    /**
     * Adds the enemy to the world
     * 
     * @param w     The world that the enemy will be placed in
     */
    protected void addedToWorld(World w)
    {
        //not dead
        dead = false;
        //add hp bar
        w.addObject (hpBar, getX(), getY()-30);
        //update health bar
        hpBar.update(health);
    }
    /**
     * check if enemy is dead
     * 
     * @return boolean true if enemy is dead
     */
    protected boolean getDead()
    {
        return dead;
    }
    /**
     * check enemy health
     * 
     * @return int return amount of health
     */
    protected int getHealth()
    {
        return health;
    }
    /**
     * animates the enemy
     * 
     * @param fileName name of the file
     * @param animSpeed the speed at which they move through the frames
     * @param imageStart starting image
     * @param imageEnd ending image
     */
    public void animate(String fileName,int animSpeed,int imageStart,int imageEnd)
    {
        //increase fram
        frame++;
        if(frame%animSpeed==0 && frame/animSpeed < imageEnd)
        {  
            //change image
            image = new GreenfootImage(""+fileName + frame/animSpeed + ".png");
            //image.scale((int)(image.getWidth()*1.5) , (int)(image.getHeight()*1.5));
            setImage(image);
        }
        
        if(frame/animSpeed >= imageEnd && !dead)
        {
            //set frame
            frame = imageStart*animSpeed;
        }
        //if enemy is dead
        if(frame/animSpeed >= imageEnd && dead)
        {
            //show death animation and add money
            getWorld().addObject(new Death(true,false), getX(),getY());
            ((MyWorld)getWorld()).addMoney(killMoney);
            getWorld().addObject(new FloatingText(5,"+"+killMoney,((MyWorld)getWorld()).getLevelType()),getX(),getY());
            //remove object
            getWorld().removeObject(this);
        }
    }
     /**
     * decreases health of the enemy. If the enemy reaches 0 or less health, the enemy is
     * removed
     * 
     * @param damage    The amount of health the enemy loses
     */
    protected void takeDamage(int damage)
    {
        //take the damage
        health-=damage;
        //if health is less than 0
        if(health <=0)
        {
            //remove the object and add money
            getWorld().addObject(new Death(true,false), getX(),getY());
            ((MyWorld)getWorld()).addMoney(killMoney);
            getWorld().addObject(new FloatingText(5,"+"+killMoney,((MyWorld)getWorld()).getLevelType()),getX(),getY());
            ((MyWorld)getWorld()).addEnemyCounter(type);
            getWorld().removeObject(this);
        }
    }
    /**
     * changes the speed of the enemy. The slow does not apply until after a certain amount
     * of time
     * 
     * @param slow              the amount of speed the enemy loses
     * @param duration  the duration of the change of speed
     */
    protected void changeSpeed(int slow, int duration)
    {
        //set slow duration 
        sDuration = duration;
        //increase when soldier should be slowed
        speedCount++;
        if(speedCount == 70)//how much time needs to pass to change the speed, more upgrades = less time
        {
            speedCount = 0;
            speed -= slow;
        }
        //speed cannot go under 1
        if (speed < 1)
        {
            speed = 1;
        }
    }

    /**
     * sets the speed back to the original speed after the slow duration has worn off
     */

    protected void checkSpeed()
    {
        //increase counter
        counter++;
        //check if duration is over
        if (counter > sDuration)
        {
            //set speed back to normal
            speed = originalSpeed;
            counter = 0;
        }
    }
    /**
     * method to reset path
     */
    protected void resetPath()
    {
        pathToTarget = null;
    }
    /**
     * follows the player and calculates if the enemy is off the correct path in order to fix it
     */
    protected void followTarget()//follows the player
    {
        //converts player coordinates to node coordinates based on the 2d array (levels)
        //1000X800 screen, every 50 pixels is a node (20x16)
        //int curTargetX = getTarget().getX()/40;
        //int curTargetY = getTarget().getY()/40;
        //gets the x,y of the enemy and converts them to node coordinates
        int curMyX = getX() / 40;
        int curMyY = getY() / 40;

        //boolean to determine if the enemy owns a token
        //enemies can only recalculate their if they own a token
        //token is passed through the enemies
        boolean owningToken = getMyWorld().isTokenOwner(this);
        boolean timerOK = true;

        if (pathToTarget == null)
        {
            pathToTarget = Node.getPath(this, getMyWorld().getEnemyTarget());//gets the path from the enemy to the player

            indexInPath = -1;
        }

        //decides if the enemy must recalculate their path
        //optimized to recalculate only if there is a change in the x y coordinates
        boolean mustRecalculate = 
            curMyX != prevMyX ||
            curMyY != prevMyY ||            
            indexInPath == -1;

        if(mustRecalculate && pathToTarget != null)//updates path if x,y changes
        {
            if (indexInPath == -1)
            {
                // if this is the first time seed the index so that after incrementing we get to 2
                indexInPath = 1;            
            }

            indexInPath ++;
            prevMyX = curMyX;
            prevMyY = curMyY;

            if (pathToTarget != null && pathToTarget.length > indexInPath)
            {
                // we found a valid path to the target
                // the last point in the path is the enemy 
                // so move towards it.
                // first re-center to the first node so you do not bump into walls
                // then move towards the next point.

                Node nextNode = pathToTarget[pathToTarget.length - indexInPath];

                // if we are close enough to the center of the starting point then
                // move towards the next point
                // adjust this threshold by experimenting with speeds to avoid walls

                //getMyWorld().showPath(pathToTarget);

                // move towards the center of the next square
                turnTowards(nextNode.realWorldXCenter(), nextNode.realWorldYCenter());

            }
            else
            {
                // there is no valid path to the target
                //turn towards the target
                //if the temporary enemy checked the path and it is invalid
                //remove the most recent tower
                turnTowards(getMyWorld().getEnemyTarget().getX(), getMyWorld().getEnemyTarget().getY()); 
            }
        }
        else
        {
            getMyWorld().passTokenToNext();//pass the token to the next enemy
        }
    }
     /**
      *  gets the world that the enemy is currently in
      *  @return MyWorld    The world that the enemy is in
      */
    protected MyWorld getMyWorld()//gets the world
    {
        if (myWorld == null)
        {
            myWorld = (MyWorld)getWorld();
        }
        return myWorld;
    }
    /**
     * gets the Target that the enemy is heading towards
     * @return Target	The target that the enemy is heading towards
     */
    protected Target getTarget()//gets the target class
    {
        if (target == null)
        {
            target = (Target) getMyWorld().getObjects(Target.class).get(0);
        }

        return target;
    }
    /**
     * checks to see if the enemy is at the target and if it is,
     * the player loses 1 life and removes the enemy
     */
    protected void targetCheck()
    {
        Target t = (Target)getOneIntersectingObject(Target.class);
        //check which enemy it is that reached the end
        if(t != null)
        {
            if(enemyType.equals("Knight"))
                ((MyWorld)getWorld()).lowerLives(1);
            if(enemyType.equals("Slime"))
                ((MyWorld)getWorld()).lowerLives(5);
            if(enemyType.equals("Demon"))
                ((MyWorld)getWorld()).lowerLives(10);
            getWorld().removeObject(this);
        }
    }
}
