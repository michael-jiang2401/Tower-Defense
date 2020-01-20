import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;
import java.util.ArrayList;
/**
 * A class containing methods that save the game, achievements, highscore. Can also clear a save file.
 * 
 * @author Kevin B, Kevin Y, Jonathan
 * @version January 16th 2019
 */
public class Save
{
    /**
     * Saves the game to a text file to be loaded in later. Prints important values to the file 
     * such as money, lives, round, towers etc.
     * 
     * @param save The 2d array that contains all the towers
     * @param w The world being saved
     */
    public static void saveFile(Tower[][] save, MyWorld w)throws IOException
    {
        FileWriter write = new FileWriter("SaveInfo.txt", false); // creates a path to the filewriter and erases if there is any text beforehand
        PrintWriter printer = new PrintWriter(write); // links the printwriter to the text file

        //save money, lives, round

        printer.println(Integer.toString(w.getRealMoney()));
        printer.println(Integer.toString(w.getRealLives()));
        printer.println(Integer.toString(w.getRealRound()));
        printer.println(Integer.toString(w.getLevelType()));
        printer.println(Boolean.toString(w.getAddedHeli()));
        printer.println(Integer.toString(w.getHeliRounds()));

        for (int i = 0; i < save.length; i++)
        {
            for (int j = 0; j < save[0].length-2; j++)
            {
                if (save[i][j] != null) {
                    //String info = ""+Integer.toString(save[i][j].getType())+","+Integer.toString(save[i][j].getNumUpgrades());
                    String info = save[i][j].getStats();
                    printer.println(info);
                }
                else
                {
                    printer.println("null");
                }

            }
        }
        printer.close(); //closes printer to avoid excessive CPU
    }

    /**
     * Deletes the text file save by writing over the previous text
     */
    public static void deleteSave()throws IOException
    {
        FileWriter write = new FileWriter("SaveInfo.txt", false); // creates a path to the filewriter
        PrintWriter printer = new PrintWriter(write); // links the printwriter to the text file
        printer.close(); //closes printer to avoid excessive CPU 
    }

    /**
     * Saves the achievements from a given list of booleans which indicate if the achievement is 
     * unlocked
     * 
     * @param bool The list of booleans being saved
     */
    public static void saveAchievements(boolean[] bool)throws IOException
    {
        FileWriter write = new FileWriter("achievements.txt", false); // creates a path to the filewriter and erases if there is any text beforehand
        PrintWriter printer = new PrintWriter(write); // links the printwriter to the text file

        for (int i = 0; i < bool.length; i++)
        {
            String info = String.valueOf(bool[i]);
            printer.println(info);
        }

        printer.close(); //closes printer to avoid excessive CPU 
    }

    /**
     * Saves the game statistics when player loses or wins
     * 
     * @param w The main world where all the game variables are saved
     */
    public static void saveHighscore(MyWorld w) throws IOException
    {
        FileWriter write = new FileWriter("Highscores.txt", true); // creates a path to the filewriter
        PrintWriter printer = new PrintWriter(write); // links the printwriter to the text file

        //save money, lives, round, level type
        printer.println(Integer.toString(w.getLevelType()));
        printer.println(Integer.toString(w.getRealRound()));
        printer.println(Integer.toString(w.getRealLives()));
        printer.println(Integer.toString(w.getRealMoney()));

        printer.close(); //closes printer to avoid excessive CPU 
    }
}

