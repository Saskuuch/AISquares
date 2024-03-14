import java.awt.image.BufferedImage;
public class Painting {
    private ColourChromosome chromosome;
    private int[][] structure;

    public Painting(ColourChromosome chromosome, int[][] structure){
        this.chromosome = chromosome;
        this.structure = structure;
    }

    //For creating a new painting
    public Painting(int[][] structure, int colours){
        this.structure = structure;
        this.chromosome = new ColourChromosome(colours);
    }

    public ColourChromosome getChromosome(){
        return chromosome;
    }
    public int[][] getStructure(){
        return structure;
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
    }

    public Painting crossbreed(Painting mother){
        ColourChromosome childChromosome = chromosome.breed(mother.getChromosome());
        return new Painting(childChromosome, structure);
    }
}
