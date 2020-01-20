import java.awt.image.BufferedImage;
import greenfoot.*;
import java.awt.Graphics2D;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.awt.image.ColorModel;
import java.lang.ArrayIndexOutOfBoundsException;
/**
 * Processor is a class where it manipulates a bufferedImage, causing transformations, color changes, changing specific rgb values to create effects.
 * It also contains an undo and redo feature and processes the image.
 * It demonstrates the use of unpacking a pixel and repacking a pixel to change the rgb values of the desired picture
 * 
 * @author Jonathan Wong
 * @version 2018-11-20
 */
public class Processor  
{
    /**
     * This method will find average of the red, green, blue values and set all of them to be that value to turn the picture gray.
     * 
     * Demonstrates use of packagePixel() and unpackPixel() methods.
     * 
     * @param bi    The BufferedImage (passed by reference) to change.
     */
    public static void greyScale(BufferedImage bi)
    {
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        // Using array size as limit
        for (int x = 0; x < xSize; x++)
        {
            for (int y = 0; y < ySize; y++)
            {
                // Calls method in BufferedImage that returns R G B and alpha values
                // encoded together in an integer
                int rgb = bi.getRGB(x, y);

                // Call the unpackPixel method to retrieve the four integers for
                // R, G, B and alpha and assign them each to their own integer
                int[] rgbValues = unpackPixel (rgb);

                int alpha = rgbValues[0];
                int red = rgbValues[1];
                int green = rgbValues[2];
                int blue = rgbValues[3];

                // make the pic Gray-er
                int gray = (red + green + blue)/3;

                int newColour = packagePixel (gray, gray, gray, alpha); //sets all rgb values to be the same
                bi.setRGB (x, y, newColour);
            }
        }
    }
    
    /**
     * Takes in an rgb value - the kind that is returned from BufferedImage's
     * getRGB() method - and returns 4 integers for easy manipulation.
     * 
     * By Jordan Cohen
     * Version 0.2
     * 
     * @param rgbaValue The value of a single pixel as an integer, representing<br>
     *                  8 bits for red, green and blue and 8 bits for alpha:<br>
     *                  <pre>alpha   red     green   blue</pre>
     *                  <pre>00000000000000000000000000000000</pre>
     * @return int[4]   Array containing 4 shorter ints<br>
     *                  <pre>0       1       2       3</pre>
     *                  <pre>alpha   red     green   blue</pre>
     */
    public static int[] unpackPixel (int rgbaValue)
    {
        int[] unpackedValues = new int[4];
        // alpha
        unpackedValues[0] = (rgbaValue >> 24) & 0xFF;
        // red
        unpackedValues[1] = (rgbaValue >> 16) & 0xFF;
        // green
        unpackedValues[2] = (rgbaValue >>  8) & 0xFF;
        // blue
        unpackedValues[3] = (rgbaValue) & 0xFF;

        return unpackedValues;
    }
    
     /**
     * Takes in a red, green, blue and alpha integer and uses bit-shifting
     * to package all of the data into a single integer.
     * 
     * @param   int red value (0-255)
     * @param   int green value (0-255)
     * @param   int blue value (0-255)
     * @param   int alpha value (0-255)
     * 
     * @return int  Integer representing 32 bit integer pixel ready
     *              for BufferedImage
     */
    public static int packagePixel (int r, int g, int b, int a)
    {
        int newRGB = (a << 24) | (r << 16) | (g << 8) | b;
        return newRGB;
    }
}
