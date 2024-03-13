import java.util.Random;

public class ColourChromosome {
    private int[] chromosome;
    public ColourChromosome(int[] chromosome){
        this.chromosome = chromosome;
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
                chromosome[x] = rn.nextInt(Integer.MAX_VALUE);
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
