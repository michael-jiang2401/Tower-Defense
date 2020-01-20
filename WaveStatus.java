import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * An ability bar that displays a customizable circular cooldown timer. Update methods can either take
 * input values to change the value of the widget manually, or it can be activated or deactivated by a boolean at a  
 * modifiable cooldown speed.
 * 
 * @author Kevin Biro
 * @version October 1st 2018
 */
public class WaveStatus extends Actor
{
    //general variables
    private int cooldownTimer = 1;//time of cooldown
    private int timer;//act counter
    private char hotkey;//character display at bottom left
    private GreenfootImage loadingAnim;
    private boolean animate = false;
    private int borderThickness;

    //for drawing loading circle
    private int size;//radius
    private double x;//x,y coordinates
    private double y;
    private double a = 0;//starting angle
    private double percentPerFrame;//amount of circle completed per frame
    private int transparency;
    private int fadeSpeed;
    private int startingRotation;
    private Color borderColor;
    private int startValue;
    private boolean isOffset = false;

    //for color fader
    private Color c1,c2;
    private int r;//rgb variables for both colors
    private int g;
    private int b;
    private int r1;
    private int g1;
    private int b1;
    private int count;

    /**
     * Constructs a basic ability bar with a customizable size,
     * activation key display, border color, transparency and border thickness.
     * 
     * @param size The radius of the widget (ideal: 50-200)
     * @param hotkey The character that is drawn at the bottom left of the image to
     * show the user which key they need to press in order to activate the ability (use ' ' if you don't
     * want to display anything). This does not set the key press functionality, it only displays a character.
     * @param borderColor The border color of the widget (set this color to one that
     * is visible on your current background)
     * @param transp The transparency of the widget (ideal: 80-255)
     * @param borderThickness The thickness of the border (ideal: 2-20)
     */
    public WaveStatus(int size, char hotkey, Color borderColor, int transp, int borderThickness)
    {
        //take parameters into variables to be used later
        this.size = size;
        this.hotkey = hotkey;
        this.borderColor = borderColor;
        transparency = transp;
        this.borderThickness = borderThickness;
        loadingAnim = new GreenfootImage(size,size);
        //draw the widget
        drawWidget();
        loadingAnim.setColor(Color.WHITE);//reset color.
        this.setImage(loadingAnim);
    }

    /**
     * Sets the value of the cooldown animation with an input in degrees. You can also set the 
     * color, transparency and start rotation. The cooldown will be filled in
     * to the value of the input in degrees so you must use this method when you want to change
     * the amount filled into the circle.
     * 
     * @param c1 The color of the animation/cooldown
     * @param transparency The transparency of the widget (ideal: 80-255)
     * @param startRotation The starting rotation in degrees of the loading animation (Ex: 0,90,180,257)
     * @param degrees The amount in degrees that you want filled in. Set 0 to clear and 360 to fill
     *  (range of accepted values:0-360)
     */
    public void update(Color c1, int transparency, int startRotation, int degrees)
    {
        animate = false;
        a = 0;//reset degrees
        loadingAnim.clear();//clear the image
        drawWidget();//draw the widget
        loadingAnim.setColor(c1);//reset color.
        startingRotation = startRotation;//set the starting rotation
        loadingAnim.setTransparency(transparency);
        //fill in the specified starting value of the circle by drawing lines
        //from the center of the circle to the edge
        for(int i = 0; i < degrees*2;i++)
        {
            //to calculate the point on the circle from the given center and radius
            //size/2 = center, size/2-borderThickness = radius
            //add the starting rotation to the angle
            x = size/2 + (size/2-borderThickness) * Math.cos(a+(Math.PI/180*startingRotation));
            y = size/2 + (size/2-borderThickness) * Math.sin(a+(Math.PI/180*startingRotation));
            a+=Math.PI/360;//every cycle, add half a degree in radians to the angle
            loadingAnim.drawLine(size/2,size/2,(int)x,(int)y);//draw the line
        }
    }
    
    /**
     * Animates the cooldown depending on the variables inputted by the user
     * in the update methods or constructors.
     */
    private void animate()
    {
        percentPerFrame = 360/cooldownTimer;//calculate the amount of lines to draw per cycle
        if(animate)//if animate is true
        {
            if(a==0&&timer==0)//if the angle and timer is 0, assign color codes
            {
                assignColorCodes(c1,c2);
            }
            loadingAnim.setTransparency(transparency);//set the transparency
            timer++;//increase the timer
            //cooldown timer = frames until update
            //a = 360 degrees in radians
            if(timer >= cooldownTimer/10 && a <= Math.PI/180*360)
            {
                timer = 0;//reset the count and timer
                count = 0;
                //percentPerFrame = amount from 360 degrees to complete per frame
                //fill in the specified starting value of the circle by drawing lines
                //from the center of the circle to the edge
                for(int i = 0; i < percentPerFrame;i++)
                {
                    //to calculate the point on the circle from the given center and radius
                    //size/2 = center, size/2-borderThickness = radius
                    //add the starting rotation to the angle
                    x = size/2 + (size/2-borderThickness) * Math.cos(a+(Math.PI/180*startingRotation));
                    y = size/2 + (size/2-borderThickness) * Math.sin(a+(Math.PI/180*startingRotation));
                    a+=Math.PI/360;//every cycle, add half a degree in radians to the angle
                    loadingAnim.drawLine(size/2,size/2,(int)x,(int)y);//draw the line
                    fadeColors(fadeSpeed,cooldownTimer);//fade the colors
                }
            }
            if(a >= Math.PI/180*360)//if the animation is over
            {
                //reset circle once done loading
                a = 0;//reset drgrees & timer
                timer = 0;
                this.animate = false;//prevent the animation from starting again
                loadingAnim.clear();//clear the image
                isOffset = false;
                //redraw the widget
                drawWidget();
                loadingAnim.setColor(c1);//reset color.
                assignColorCodes(c1,c2);//assign color codes
            }
        }
    }
    
    /**
     * Assigns color codes to invididual variables in order to fade between colors
     * 
     * @param a The first color
     * @param b The second color
     */
    private void assignColorCodes(Color a, Color b)
    {
        //gets the rgb color codes from the user colors
        //assigns them to individual variables.
        r = a.getRed();
        g = a.getGreen();
        this.b = a.getBlue();
        r1 = b.getRed();
        g1 = b.getGreen();
        b1 = b.getBlue();
    }

    /**
     * Fades between colors by increasing or decreasing the rgb values until they are 
     * that of the second color. Only used by the update methods.
     * 
     * @param fadeSpeed The speed at which the numbers increase or decrease
     * @param cooldownTimer The duration of the animation
     */
    private void fadeColors(int fadeSpeed, int cooldownTimer)
    {
        //increase or decrease the rgb values towards the r1g1b1 values
        //while the count is less than the fade speed/cooldown timer
        if(count < fadeSpeed/cooldownTimer)
        {
            if(r>r1)
            {
                r--;
                count++;
            }
            else if (r==r1)
                r = r;
            else
            {
                r++;
                count++;
            }
            if(g>g1)
            {
                g--;
                count++;
            }
            else if (g==g1)
                g = g;
            else
            {
                g++;
                count++;
            }
            if(b>b1)
            {
                b--;
                count++;
            }
            else if (b==b1)
                b = b;
            else
            {
                b++;
                count++;
            }
            loadingAnim.setColor(new Color(r,g,b));//set the new color
        }
    }

    /**
     * Clears the image and draws the widget border and the hotkey in the bottom left corner.
     * 
     */
    public void drawWidget()
    {
        loadingAnim.clear();
        loadingAnim.setColor(borderColor);//set the border color
        loadingAnim.setTransparency(transparency);//set the transparency
        //draw circles each smaller than the other until the border thickness is met
        for(int i = 0; i < borderThickness; i++)
        {
            loadingAnim.drawOval(i,i,size-i*2,size-i*2);
        }
        String key = ""+hotkey;//convert the char hotkey into a string
        //create another image with the hotkey and the border color
        GreenfootImage text = new GreenfootImage(key, size/3-6, borderColor, null);
        loadingAnim.drawImage(text, 0, size-(size/3-8));//draw the hotkey image onto the widget
    }   

    /**
     * Return the angle from the start position to the current position
     * 
     * @return int Returns a rounded value of the current angle
     */
    public int getAngle()
    {
        return (int)(a*180/Math.PI);//return the current angle in degrees (rounded)
    }
    
    /**
     * Continuously checks if the animation has begun. When it has, will animate
     * the cooldown until finished or paused.
     */
    public void act()
    {
        animate();
    }

}
