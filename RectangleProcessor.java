import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

//Deprecated, used to test adding rectangles to structure
public class RectangleProcessor {
    public static int[][] pic = new int[128][128];
    public static int[] colours;
    public static void main(String[] args){

        int squareID = 1;

        for(int x = 0; x<pic.length; x++){
            for(int y = 0; y<pic[x].length; y++){
                while(pic[x][y] == 0){
                    Rectangle r1 = new Rectangle(squareID);
                    if(insertAble(r1, x, y)){
                        insertSquare(r1, x, y);
                        squareID++;
                    }
                }
            }
        }
        colours = new int[squareID];
        Random rn = new Random();
        for(int x = 0; x< colours.length; x++){
            colours[x] = rn.nextInt(Integer.MAX_VALUE);
        }
        BufferedImage img = createImage();
        try{
            File file = new File("Images/img2.png");
            ImageIO.write(img, "png", file);
        }
        catch(IOException e){
            System.out.println("Error creating image");
        }
    }

    public static boolean insertAble(Rectangle r1, int x, int y){
        boolean insertable = true;
        if(r1.getWidth() + x > pic.length || r1.getHeight() + y >pic[0].length) insertable = false;
        int curX = x;
        int curY = y;
        while(insertable && curX < x + r1.getWidth()){
            while(insertable && curY < y + r1.getHeight()){
                if(pic[x][y] != 0) insertable = false;
                curY++;
            }
            curX++;
        }
        return insertable;
    }

    public static void insertSquare(Rectangle r1, int x, int y){
        for(int currX = x; currX< r1.getWidth() + x; currX++){
            for(int currY = y; currY <r1.getHeight() + y; currY++){
                pic[currX][currY] = r1.getId();
            }
        }
    }

    public static BufferedImage createImage(){
        BufferedImage img = new BufferedImage(pic.length, pic[0].length, BufferedImage.TYPE_INT_RGB);
        for(int x = 0; x < pic.length; x++){
            for(int y = 0; y < pic[0].length; y++){
                img.setRGB(x, y, colours[pic[x][y]]);
            }
        }
        return img;
    }
}
