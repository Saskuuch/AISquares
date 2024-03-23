import java.util.Random;

public class ColourChromosome {
    private int[] chromosome;
    public ColourChromosome(int[] chromosome){
        this.chromosome = chromosome;
    }
    public ColourChromosome(int colours){
        chromosome = new int[colours];
        Random rn = new Random();
        for(int x = 0; x< chromosome.length; x++){
            chromosome[x] = rn.nextInt(Integer.MAX_VALUE);
        }
    }
    public int[] getChromosome(){
        return chromosome;
    }

    public int getElement(int index){
        return chromosome[index];
    }

    public int length(){
        return chromosome.length;
    }

    //Must give value between 0 and 1
    public void mutate(float percentage){
        Random rn = new Random();
        for(int x = 0; x < chromosome.length; x++){
            if(rn.nextFloat(0,1) < percentage){
                int rgb[] = new int[3];
                int mask = 0xFF;
                rgb[0] = chromosome[x] & mask;
                rgb[1] = (chromosome[x] & mask << 8) >> 8;
                rgb[2] = (chromosome[x] & mask << 16) >> 16;
                for(int z = 0; z < rgb.length; z++){
                    rgb[z] += rn.nextInt(-25, 25);
                    if(rgb[z] > 255) rgb[z] = 255;
                    else if (rgb[z] < 0) rgb[z] = 0;
                }
                int newColour = 0;
                newColour = newColour | rgb[0];
                newColour = newColour | rgb[1] << 8;
                newColour = newColour | rgb[2] << 16;
                chromosome[x] = newColour;
            }
        }
    }

    public ColourChromosome breed(ColourChromosome chromosome2){
        ColourChromosome child = null;
        int[] childArray = new int[chromosome2.length()];
        Random rn = new Random();

        if(chromosome2.length() != chromosome.length){
            return chromosome2;
        }
        else{
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
}
