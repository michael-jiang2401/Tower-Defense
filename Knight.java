import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The most basic enemy known as the knight
 * 
 * @author Kevin Biro
 * @version January 16th 2019
 */
public class Knight extends Enemy
{
    /**
     * creates a new Knight with a healthbar and speed
     * @param s The speed of the enemy
     * @param health The enemy health
     */
    public Knight(int s, int health)
    {
        //set dead as false
        dead = false;
        //set enemy type
        enemyType = "Knight";
        //set kill money
        killMoney = 10;
        //set speed
        speed = s;
        originalSpeed = s;
        //set hp
        this.health = health; 
        //st counters
        counter = 0;
        speedCount = 0;
        //set type
        type = 3;
        //add hp bar
        hpBar = new HealthBar(health, this);
    }

    public void act() 
    {
        //if game not over
        if(!((MyWorld)getWorld()).getGameOver())
        {
            //update hp bar
            hpBar.update(health);
            //follow target
            followTarget();
            //check speed
            checkSpeed();
            //move
            move(speed);
            //check target
            targetCheck();
        }
    }
}
