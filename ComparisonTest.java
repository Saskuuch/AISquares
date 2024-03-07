import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ComparisonTest{
    public static void main(String[] args){
        try{
            BufferedImage img = ImageIO.read(new File("Images/img1.png"));
            System.out.println(compareImage(img));
        }
        catch(IOException e){
            System.out.println("poo poo 1");
        }
    }

    public static double compareImage(BufferedImage img){
        BufferedImage target = null;
        try{
        target = ImageIO.read(new File("Images/img1.png"));
        }
        catch(IOException e){
            System.out.println("poo poo 2");
        }
        double compTotal = 0;
        for(int x = 0 ; x < img.getWidth(); x++){
            for(int y = 0; y< img.getHeight(); y++){
                compTotal += comparePixel(img.getRGB(x, y), target.getRGB(x, y));
            }
        }
        compTotal /= img.getWidth() *  img.getHeight();
        return compTotal;
    }

    public static double comparePixel(int img1, int img2){
        int r1, r2, g1, g2, b1, b2;
        int mask = 0xFF;
        b1 = img1 & mask;
        b2 = img2 & mask;

        g1 = (img1 & mask << 8) >> 8;
        g2 = (img2 & mask << 8) >> 8;

        r1 = (img1 & mask << 16) >> 16;
        r2 = (img2 & mask << 16) >> 16;
        double distance = Math.sqrt(Math.pow(b1 - b2, 2) + Math.pow(g1 - g2, 2) + Math.pow(r1 - r2, 2));
        distance /= Math.sqrt(Math.pow(255, 2) + Math.pow(255, 2) + Math.pow(255, 2));

        return distance;
    }
}