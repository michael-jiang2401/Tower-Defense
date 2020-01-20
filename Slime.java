import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * An enemy that can resist all attacks other than fire based attacks
 * 
 * @author Kevin Biro
 * @version January 16th 2019
 */
public class Slime extends Enemy
{
    //animation variables
    private GreenfootImage image;
    private int frame=1;
    //location and direction variables
    private int x,y;
    private String direction;
    /**
     * creates a new Slime with a healthbar and speed
     * @param s The speed of the enemy
     * @param health The enemy health
     */
    public Slime(int s, int health)
    {
        //set death
        dead = false;
        //set enemy type
        enemyType = "Slime";
        //set kill money
        killMoney = 50;
        //set direction
        direction = "back";
        //set speed
        speed = s;
        originalSpeed = s;
        //set health
        this.health = health; 
        //set counter
        counter = 0;
        speedCount = 0;
        //set type
        type = 4;
        //give hp bar
        hpBar = new HealthBar(health, this);
    }
    /**
     * when added to the world
     * @param w the world it is being added into
     */
    public void addedToWorld(MyWorld w)
    {
        //the x and y where it is added
        x = getX();
        y = getY();
    }
    /**
     * calling this method will make this enemy take damage
     * @param damage        amount of damage
     */
    public void takeDamage(int damage)
    {
        //check if it is fire
        Fire f = (Fire)getOneIntersectingObject(Fire.class);
        FireProjectile fp = (FireProjectile)getOneIntersectingObject(FireProjectile.class);
        if(f != null || fp != null)
        {
            //if it is a fire based attack deal damage
            health-=damage;
            //if health below zero
            if(health <=0)
            {
                //play death animation and remove from world
                getWorld().addObject(new Death(false,false), getX(),getY());
                ((MyWorld)getWorld()).addMoney(killMoney);
                getWorld().addObject(new FloatingText(5,"+"+killMoney,((MyWorld)getWorld()).getLevelType()),getX(),getY());
                ((MyWorld)getWorld()).addEnemyCounter(type);
                getWorld().removeObject(this);
            }
        }
    }

    public void act() 
    {
        //if not game over
        if(!((MyWorld)getWorld()).getGameOver())
        {
            //animate
            animate();
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
    /**
     * animate the slime
     */
    public void animate()
    {
        //get the absolute value and check how far it went and which way
        if(Math.abs(getX() - x) > Math.abs(getY() - y))
        {
            //check which way it is facing based on prev x to current x and set direction
            if(getX() > x)
            {
                direction = "right";
                x = getX();
            }
            else if(getX() < x)
            {
                direction = "left";
                x = getX();
            }
        }
        else
        {
            //check which way it is facing based on prev y to current y and set direction
            if(getY() > y)
            {
                direction = "front";
                y = getY();
            }
            else if(getY() < y)
            {
                direction = "back";
                y = getY();
            }
        }
        //animated based on which direction it is currently facing
        if(direction.equals("front"))
        {
            if(frame%5==0)
            {  
                image = new GreenfootImage("front (" + frame/5 + ").png");

                setImage(image);
            }
        }
        else if(direction.equals("back"))
        {
            if(frame%5==0)
            {  
                image = new GreenfootImage("back" + frame/5 + ".png");

                setImage(image);
            }
        }
        else if(direction.equals("right"))
        {
            if(frame%5==0)
            {  
                image = new GreenfootImage("right" + frame/5 + ".png");

                setImage(image);
            }
        }
        else
        {
            if(frame%5==0)
            {  
                image = new GreenfootImage("left" + frame/5 + ".png");

                setImage(image);
            }
        }
        frame++;
        if(frame==25)
        {
            frame = 1;
            //getWorld().removeObject(this);
        }
    }
}
