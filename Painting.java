import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;

import javax.imageio.ImageIO;
public class Painting  implements Comparable<Painting>{
    private ColourChromosome chromosome;
    private int[][] structure;
    private double compareVal;

    @Override
    public int compareTo(Painting o){
        return Double.compare(1-compareVal, 1-o.getCompareVal());
    }

    public Painting(ColourChromosome chromosome, int[][] structure){
        this.chromosome = chromosome;
        this.structure = structure;
        this.compareVal = compareImage();
    }

    //For creating a new painting
    public Painting(int[][] structure, int colours){
        this.structure = structure;
        this.chromosome = new ColourChromosome(colours);
        this.compareVal = compareImage();
    }

    public ColourChromosome getChromosome(){
        return chromosome;
    }
    public int[][] getStructure(){
        return structure;
    }
    public double getCompareVal(){
        return compareVal;
    }

    //Paints the picture which can be turned into png
    public BufferedImage generateImage(){
        BufferedImage img = new BufferedImage(structure.length, structure[0].length, BufferedImage.TYPE_INT_RGB);
        for(int x = 0; x < structure.length; x++){
            for(int y = 0; y < structure[0].length; y++){
                img.setRGB(x, y, chromosome.getElement(structure[x][y]));
            }
        }
        return img;
    }

    public void mutate(float percentage){
        chromosome.mutate(percentage);
        this.compareVal = compareImage();
    }

    public Painting crossbreed(Painting mother){
        ColourChromosome childChromosome = chromosome.breed(mother.getChromosome());
        return new Painting(childChromosome, structure);
    }

    public double compareImage(){

//        BufferedImage img = generateImage();
//        BufferedImage target = null;
//        try{
//        target = ImageIO.read(new File("Images/target.png"));
//        }
//        catch(IOException e){
//            System.out.println("Wrong image");
//        }
//        double compTotal = 0;
//        for(int x = 0 ; x < img.getWidth(); x++){
//            for(int y = 0; y< img.getHeight(); y++){
//                compTotal += comparePixel(img.getRGB(x, y), target.getRGB(x, y));
//            }
//        }
//        compTotal /= img.getWidth() *  img.getHeight();

        ImageCompare imageCompare = new ImageCompare(generateImage());
        Thread thread = new Thread(imageCompare);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return imageCompare.getCompareVal();
    }

    public double comparePixel(int img1, int img2){
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
