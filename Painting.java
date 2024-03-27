import java.awt.image.BufferedImage;
public class Painting  implements Comparable<Painting>{
    private ColourChromosome chromosome;
    private int[][] structure;
    private double compareVal; //Fitness score

    //For creating child painting
    public Painting(ColourChromosome chromosome, int[][] structure){
        this.chromosome = chromosome;
        this.structure = structure;
        mutate((float)0.01);
        this.compareVal = compareImage();
    }

    //For initialization
    public Painting(int[][] structure, int colours){
        this.structure = structure;
        this.chromosome = new ColourChromosome(colours);
        this.compareVal = compareImage();
    }

    //From Comparable, allows sorting array by comparison score
    @Override
    public int compareTo(Painting o){
        return Double.compare(1-compareVal, 1-o.getCompareVal());
    }

    //Mutates painting
    public void mutate(float percentage){
        chromosome.mutate(percentage);
    }

    //Creates child with second painting
    public Painting crossbreed(Painting mother){
        ColourChromosome childChromosome = chromosome.breed(mother.getChromosome());
        return new Painting(childChromosome, structure);
    }

    //Compares to target image, giving fitness score
    public double compareImage(){

        //Runs comparison on new thread to speed up algorithm
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

    //Getters
    public ColourChromosome getChromosome(){
        return chromosome;
    }
    public int[][] getStructure(){
        return structure;
    }
    public double getCompareVal(){
        return compareVal;
    }



}
