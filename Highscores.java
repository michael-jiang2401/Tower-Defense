import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Displays the highscores that have been accumulated in the games played.
 * Sorts the data from a save based on level > round > lives > money and then displays on the screen
 * 
 * @author Kevin Biro
 * @version January 16th 2019
 */
public class Highscores extends Actor
{
    private GreenfootImage image;
    private int[] tempArray;
    private int yIncrement,level;
    private int[][] highscores;
    /**
     * Constructs the highscores display by getting the save file and the level being displayed.
     *
     * @param save The save containing all the Highscores
     * @param level The level currently being displayed
     */
    public Highscores(int[][] save,int level)
    {
        highscores = save;
        this.level = level;
        yIncrement = 0;
        tempArray = new int[4];
        image = new GreenfootImage(600,500);

        drawHighscores(sortHighscores(save),level);
    }

    /**
     * Updates the image based on the level
     *
     * @param level The current level
     */
    public void update(int level)
    {
        yIncrement = 0;
        image = new GreenfootImage(600,500);

        drawHighscores(sortHighscores(highscores),level);//redraws highscores

    }

    /**
     * Sorts the highscores based on level > round > lives > money
     *
     * @param save The save with all the unsorted highscores.
     */
    public int[][] sortHighscores(int[][] save)//order: level, round, lives, money
    {
        for(int x = 3; x > 0; x--)//do 4 times because there are 4 levels
        {
            for(int i = 0; i < save.length; i++)//while there are items in the array
            {
                for(int j = 0; j < save.length; j++)//swap the item if it is larger than the one being compared to
                {
                    if(save[i][x] > save[j][x])
                    {
                        //swap the saves if the round is higher
                        tempArray = save[i];
                        save[i] = save[j];
                        save[j] = tempArray;
                    }
                }
            }
        }
        return save;//return the sorted save
    }

    /**
     * Draws the highscores on the image.
     *
     * @param savedFile The sorted highscore save
     * @param round The level being displayed
     */
    public void drawHighscores(int[][] savedFile, int round)
    {
        image.setFont(image.getFont().deriveFont(27f));
        image.setColor(Color.GRAY);
        image.fill();
        image.setColor(Color.BLACK);
        image.drawString("HIGHSCORES",220,30);
        if(round != 4)//level 1,2,3
            image.drawString("Level "+round,260,60);
        else
            image.drawString("Endless",260,60);

        for(int i = 0; i < savedFile.length && yIncrement < 10; i++)//check all the items
        {
            image.setFont(image.getFont().deriveFont(15f));
            if(savedFile[i][0] == round)//if the item is for the current level
            {
                for(int j = 1; j < savedFile[0].length; j++)//display the item on the screen.
                {
                    switch(j)
                    {
                        case 1:
                        {
                            image.drawString("     Round: "+savedFile[i][j],120,100 + yIncrement*40);//round
                            break;
                        }
                        case 2:
                        {
                            image.drawString("\u2665"+savedFile[i][j],120 + 150,100 + yIncrement*40);//lives
                            break;
                        }
                        case 3:
                        {
                            image.drawString("$"+savedFile[i][j],120 + 250,100 + yIncrement*40);//money
                        }
                    }
                }
                yIncrement++;
            }
            if(i < 10)
                image.drawString(""+(i+1)+".     ",120,100 + i*40);
        }

        setImage(image);
    }  
}
