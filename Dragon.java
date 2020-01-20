import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * This enemy run through all the path and goes towards the flag without 
 * stopping
 * 
 * @author Kevin Biro
 * @version January 16th 2019
 */
public class Dragon extends Enemy
{
    //instance variables
    private int frame;
    private GreenfootImage image;
    //variables for animation
    private int imageStart, imageEnd,animSpeed;
    /**
     * creates a new Dragon with a healthbar and speed
     * 
     * @param s The speed of the enemy
     * @param health The enemy health
     */
    public Dragon(int s, int health)
    {
        //animation speed
        animSpeed = 5;
        dead = false;
        //setting animation
        imageStart = 0;
        imageEnd = 7;
        image = getImage();
        //setting type
        enemyType = "Slime";
        //amount of money when killed
        killMoney = 100;
        //setting speed
        speed = s;
        originalSpeed = s;
        //setting health
        this.health = health; 
        //counters
        counter = 0;
        speedCount = 0;
        type = 2;
        //health bar
        hpBar = new HealthBar(health, this);
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
    /**
     * animates the dragon
     */
    public void animate()
    {
        if(frame%5==0)
        {  
            image = new GreenfootImage("dragon" + frame/animSpeed + ".png");
            //image.scale((int)(image.getWidth()*1.5) , (int)(image.getHeight()*1.5));
            setImage(image);
        }
        if(frame/animSpeed >= imageEnd)
        {
            frame = imageStart*animSpeed;
        }
        frame++;    
        if(frame/animSpeed >= imageEnd && dead)
        {
            getWorld().addObject(new Death(false,false), getX(),getY());
            ((MyWorld)getWorld()).addMoney(killMoney);
            getWorld().addObject(new FloatingText(5,"+"+killMoney,((MyWorld)getWorld()).getLevelType()),getX(),getY());
            ((MyWorld)getWorld()).addEnemyCounter(type);
            getWorld().removeObject(this);
        }
    }
    /**
     * method to follow target
     */
    public void followTarget()
    {
        //turn towards end and go directly there
        turnTowards(getMyWorld().getEnemyTarget().getX(), getMyWorld().getEnemyTarget().getY());
    }
    
    public void act() 
    {
        //if not game over
        if(!((MyWorld)getWorld()).getGameOver())
        {
             //update health bar
            hpBar.update(health);
            //folow target
            followTarget();
            //check speed
            checkSpeed();
            //check if dead
            if(!dead)
                move(speed);
            //check target
            targetCheck();
            //animate
            animate();
        }
    }    
}
