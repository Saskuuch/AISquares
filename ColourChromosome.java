import java.util.Random;

public class ColourChromosome {
    private int[] chromosome;
    public ColourChromosome(int[] chromosome){
        this.chromosome = chromosome;
    }

    //Creates a new ColourChromosome, for initialization
    public ColourChromosome(int colours){
        chromosome = new int[colours];
        Random rn = new Random();
        for(int x = 0; x< chromosome.length; x++){
            chromosome[x] = rn.nextInt(Integer.MAX_VALUE);
        }
    }

    //Mutates chromosome, current config at 0.01
    public void mutate(float percentage){
        Random rn = new Random();
        for(int x = 0; x < chromosome.length; x++){
            if(rn.nextFloat(0,1) < percentage){
                int rgb[] = new int[3];
                int mask = 0xFF;

                //Gets each RGB value from element
                //First bits 0-7 are blue, 8-15 green, 16-23 red
                rgb[0] = chromosome[x] & mask;
                rgb[1] = (chromosome[x] & mask << 8) >> 8;
                rgb[2] = (chromosome[x] & mask << 16) >> 16;

                //Changes each RGB value by range -25 - 25
                for(int z = 0; z < rgb.length; z++){
                    rgb[z] += rn.nextInt(-25, 25);
                    if(rgb[z] > 255) rgb[z] = 255;
                    else if (rgb[z] < 0) rgb[z] = 0;
                }

                //Reconstructs element
                int newColour = 0;
                newColour = newColour | rgb[0];
                newColour = newColour | rgb[1] << 8;
                newColour = newColour | rgb[2] << 16;
                chromosome[x] = newColour;
            }
        }
    }

    //Crossover with second ColourChromosome
    public ColourChromosome breed(ColourChromosome chromosome2){
        ColourChromosome child = null;
        int[] childArray = new int[chromosome2.length()];
        Random rn = new Random();

        //Makes sure both chromosomes are same length
        if(chromosome2.length() != chromosome.length){
            return chromosome2;
        }
        else{
            //Creates new chromosome by randomly selecting value from each chromosome
            for(int x = 0; x < chromosome2.length(); x++){
                if(rn.nextBoolean()){
                    childArray[x] = chromosome2.getElement(x);
                }
                else{
                    childArray[x] = chromosome[x];
                }
            }
            child = new ColourChromosome(childArray);
        }
        return child;
    }

    //Getters
    public int[] getChromosome(){
        return chromosome;
    }

    public int getElement(int index){
        return chromosome[index];
    }

    public int length(){
        return chromosome.length;
    }
}
