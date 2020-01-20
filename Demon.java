import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Sub class of enemy, this enemy is extremely tanky is considered an end game 
 * monster.
 * 
 * @author Kevin Biro
 * @version January 16th 2019
 */
public class Demon extends Enemy
{
    //instance variables
    private int frame;
    private GreenfootImage image;
    //variables for animation
    private int imageStart, imageEnd,animSpeed;
    /**
     * creates a new Demon with a healthbar and speed
     * @param s The speed of the enemy
     * @param health The enemy health
     */
    public Demon(int s, int health)
    {
        //animation speed
        animSpeed = 5;
        dead = false;
        //setting animation
        imageStart = 0;
        imageEnd = 7;
        image = getImage();
        //setting type
        enemyType = "Demon";
        //amount of money when killed
        killMoney = 500;
        //setting speed
        speed = s;
        originalSpeed = s;
        //setting health
        this.health = health; 
        //counters
        counter = 0;
        speedCount = 0;
        type = 1;
        //health bar
        hpBar = new HealthBar(health, this);
    }
    /**
     * animates the demon
     */
    public void animate()
    {
        //check every 5 acts
        if(frame%5==0)
        {  
            image = new GreenfootImage("demon" + frame/animSpeed + ".png");
            setImage(image);
        }
        //check if frame/animationSpeed is greater than image end
        if(frame/animSpeed >= imageEnd)
        {
            frame = imageStart*animSpeed;
        }
        //add frame
        frame++;   
        //if dead
        if(frame/animSpeed >= imageEnd && dead)
        {
            getWorld().addObject(new Death(false,true), getX(),getY());
            ((MyWorld)getWorld()).addMoney(killMoney);
            getWorld().addObject(new FloatingText(5,"+"+killMoney,((MyWorld)getWorld()).getLevelType()),getX(),getY());
            ((MyWorld)getWorld()).addEnemyCounter(type);
            getWorld().removeObject(this);
        }
    }
    /**
     * calling this method will make this enemy take damage
     * @param damage        amount of damage
     */
    public void takeDamage(int damage)
    {
        //lose the health
        health-=damage;
        //check if dead
        if(health <=0 && !dead)
        {
            //play death animation
            imageStart = 31;
            imageEnd = 46;
            frame = imageStart*animSpeed;
            getWorld().removeObject(hpBar);
            dead = true;
        }
    }

    public void act() 
    {
        //if not game over
        if(!((MyWorld)getWorld()).getGameOver())
        {
            //update health bar
            hpBar.update(health);
            //follow the path
            followTarget();
            //check speed
            checkSpeed();
            //if not dead move
            if(!dead)
                move(speed);
                
            //check target
            targetCheck();
            //animate
            animate();
        }
    }    
}
