import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
import java.util.List;
/**
 * Unique enemy that can stun towers which makes the towers do nothing for a short while
 * 
 * @author Kevin Biro
 * @version January 16th 2019
 */
public class Wizard extends Enemy
{
    //sound effects and images
    private GreenfootSound curse = new GreenfootSound ("curse4.wav");
    private GreenfootImage image;
    //timer
    private int timer;
    //target turrets
    private Tower targetEnemy;
    private List<Tower> towerList;
    //target
    private boolean target = false;
    //range of stun
    private int range;
    //animation variables
    private int CDtimer,stopTime,frame,animSpeed,imageEnd,imageStart;
    /**
     * creates a new wizard with a healthbar and speed
     * @param s The speed of the enemy
     * @param health The enemy health
     */
    public Wizard(int s, int health)
    {
        //animation variable setting
        animSpeed = 3;
        imageStart = 1;
        imageEnd = 8;
        frame = imageStart*animSpeed;
        image = getImage();
        stopTime = 0;
        //set death
        dead = false;
        //set enemy wizard
        enemyType = "Wizard";
        //set kill money
        killMoney = 20;
        //set speed
        speed = s;
        originalSpeed = s;
        //set health
        this.health = health;
        //set counters
        counter = 0;
        speedCount = 0;
        //set range
        range = 400;
        //set hpbar
        hpBar = new HealthBar(health, this);
        //set timers for attack
        CDtimer = -50;
        stopTime = 101;
        type = 5;
    }
    /**
     * target closest turret
     * @param range the radius the wizard should be able to target
     */
    private void targetClosestEnemy (int range)
    {
        //get all towers in range
        towerList = getObjectsInRange(range/2,Tower.class);
        //if there are towers in range
        if (towerList.size() > 0)
        {
            for(Tower t : towerList)
            {
                //check if it is actually a tower
                if (t.getType() != 0 && t.getType() != 2)
                {
                    //check if it can hit the tower
                    if(!t.getStun() && !t.getCanDrag() && towerList.size() > 0)
                    {
                        //set target
                        targetEnemy = t;
                    }
                    else
                    {
                        //target is null
                        targetEnemy = null;
                    }
                }
                else
                {
                    //target is null
                    targetEnemy = null;
                }
            }
        }
        else
        {
            //target is null
            targetEnemy = null;
        }
    }

    public void act() 
    {
        //if not game over
        if(!((MyWorld)getWorld()).getGameOver())
        {
            //target closest tower
            targetClosestEnemy (range);
            //check the timer for stopping
            stopTime++;
            //stun tower
            stunTower();
            //update hp bar
            hpBar.update(health);
            //follow target
            followTarget();
            //check speed
            checkSpeed();
            //if stop time greater than 50
            if(stopTime > 50)
            {
                //set frame
                frame = imageStart*animSpeed;
                //animate
                animate(enemyType,animSpeed,imageStart,imageEnd);
                //move
                move(speed);
            }
            else
            {
                //set frame
                frame = imageStart*8;
                //animate the casting 
                animate("Casting",8,imageStart,5);
            }
            //check target
            targetCheck();
        }
    }
    /**
     * stun tower
     */
    public void stunTower()
    {
        //cd timer increae
        CDtimer++;
        //check if target enemy exist and timer is above 0
        if (targetEnemy!= null && CDtimer > 0)
        {
            //if not muted play sound
            if(!((MyWorld)getWorld()).getMuted())
                curse.play();
            //turn towards the turret
            turnTowards(targetEnemy.getX(),targetEnemy.getY());
            //set stop time
            stopTime = 0;
            //stun target
            targetEnemy.stun();
            //set timer to -150
            CDtimer = -150;
        }
    }
}