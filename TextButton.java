import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A Generic Button to display text that is clickable. Owned by a World, which controls click
 * capturing. Buttons are modular, you can modify the button text, backcolour, size, and text colour.
 * 
 * @author Jordan Cohen, Kevin Biro
 * @version v0.1.5
 */
public class TextButton extends Actor
{
    // Declare private variables
    private GreenfootImage myImage;
    private String buttonText;
    private int textSize;

    private Color color = Color.RED;
    private Color textColor;
    private int type;
    private boolean menuOpen = false;
    
    private boolean mouseOver;
    /**
     * Construct a TextButton with a given String at the default size
     * 
     * @param text  String value to display
     * 
     */
    public TextButton (String text, int type)
    {
        this(text, 17,Color.WHITE,Color.BLACK,type);
        this.type = type;
        color = Color.GRAY;
        textColor = Color.BLACK;
        textSize = 17;
        mouseOver = false;
    }

    /**
     * Construct a TextButton with a given String and a specified size
     * 
     * @param text  String value to display
     * @param textSize  size of text, as an integer
     * 
     */
    public TextButton (String text, int textSize, Color color, Color textColor, int type)
    {
        this.color = color;
        this.textSize = textSize;
        this.type = type;
        drawImage(text,textSize,Color.WHITE,Color.BLACK);
        this.textColor = textColor;
    }

    /**
     * Change the text displayed on this Button
     * 
     * @param   text    String to display
     * 
     */
    public void update (String text)
    {
        drawImage(text,textSize,Color.WHITE,textColor);
    }

    public void update (String text, Color color)
    {
        this.color = color;
        drawImage(text,textSize,Color.WHITE,textColor);
    }
    
    /**
     * Draws the image, adds a modifyable string and back color
     * 
     * @param text The text you want to display on the button
     * @param textSize The size of the text
     * @param backColor The back color of the button
     */
    public void drawImage(String text, int textSize, Color backColor, Color textColor)
    {
        buttonText = text;
        GreenfootImage tempTextImage = new GreenfootImage (text, textSize, textColor, backColor);
        myImage = new GreenfootImage (tempTextImage.getWidth() + 8, tempTextImage.getHeight() + 8);
        myImage.setColor (backColor);
        myImage.fill();
        myImage.drawImage (tempTextImage, 4, 4);
        myImage.setColor (Color.BLACK);
        myImage.drawRect (0,0,tempTextImage.getWidth() + 7, tempTextImage.getHeight() + 7);
        setImage(myImage);
    }

    /**
     * Updates the image when the mouse is over it by lighting up red.
     */
    public void update()
    {
        MouseInfo mouse = Greenfoot.getMouseInfo();
        if (!mouseOver && Greenfoot.mouseMoved(this))
        {
            drawImage(buttonText, textSize+3, color, textColor);
            mouseOver = true;
        }
        if (mouseOver && Greenfoot.mouseMoved(null) && ! Greenfoot.mouseMoved(this))
        {
            drawImage(buttonText, textSize, Color.WHITE, Color.BLACK);
            mouseOver = false;
        }
    }

    /**
     * Checks mouse movement over image and updates when necessary
     */
    public void act()
    {
        update();
    }
}
