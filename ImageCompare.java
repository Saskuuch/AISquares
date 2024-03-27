import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

//Function moved from Painting class to allow for multithreading
public class ImageCompare implements Runnable{
    private double compareVal;
    private BufferedImage img;
    private double distVal = Math.sqrt(Math.pow(255, 2) + Math.pow(255, 2) + Math.pow(255, 2));

    public ImageCompare(BufferedImage img){
        this.img = img;
    }

    public double getCompareVal(){
        return compareVal;
    }
    @Override
    public void run(){
        BufferedImage target = null;
        try{
            target = ImageIO.read(new File("Images/target.png"));
        }
        catch(IOException e){
            System.out.println("Wrong image");
        }

        //Calculates average RGB distance
        double compTotal = 0;
        for(int x = 0 ; x < img.getWidth(); x++){
            for(int y = 0; y< img.getHeight(); y++){
                compTotal += comparePixel(img.getRGB(x, y), target.getRGB(x, y));
            }
        }
        compTotal /= img.getWidth() *  img.getHeight();
        compareVal = compTotal;
    }

    //Compares two pixels, returning distance %
    public double comparePixel(int img1, int img2){
        //Gets each RGB value from pixel
        //First bits 0-7 are blue, 8-15 green, 16-23 red
        int r1, r2, g1, g2, b1, b2;
        int mask = 0xFF;
        b1 = img1 & mask;
        b2 = img2 & mask;

        g1 = (img1 & mask << 8) >> 8;
        g2 = (img2 & mask << 8) >> 8;

        r1 = (img1 & mask << 16) >> 16;
        r2 = (img2 & mask << 16) >> 16;

        //RGB Distance
        double distance = Math.sqrt(Math.pow(b1 - b2, 2) + Math.pow(g1 - g2, 2) + Math.pow(r1 - r2, 2));

        //Distance %
        distance /= distVal;

        return distance;
    }
}
