import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import javax.imageio.ImageIO;
public class Algorithm {
    //Current configuration
    //Structure: 353x523
    //Population: 100
    //Mutation rate: 0.01
    //Crossover: Top 30
    public static void main(String[] args){
        //Structure initialization
        int[][] structure = new int[353][523]; //Painting structure, needs to be same dimensions as target image
        int colourCount = createStructure(structure);

        //Population initialization
        Painting[] population = createPopulation(100, structure, colourCount);
        Arrays.sort(population);
        System.out.println(population[99].getCompareVal());
        System.out.println(population[0].getCompareVal());

        //Generations, split up to see progress
        for(int x = 0; x<1000; x++){
            generation(population);
            addToCSV(population);
        }
        printSumary(population, 1000);
       for(int x = 0; x<3000; x++){
           generation(population);
           addToCSV(population);
       }
       printSumary(population, 4000);
       for(int x = 0; x<6000; x++){
           generation(population);
           addToCSV(population);
       }
       printSumary(population, 10000);
    }

    //Creates painting population based on structure
    private static Painting[] createPopulation(int size, int[][] structure, int colourCount){
        Painting[] population = new Painting[size];
        for(int x = 0; x<size; x++){
            population[x] = new Painting(structure, colourCount);
        }
        return population;
    }

    //Crossover top 30 with random painting from pop then mutates child, replaces bottom 30 with children
    public static void generation(Painting[] population){
        Random rn = new Random();
        for(int x = 0; x < 30; x++){
            population[x] = population[population.length - x -1].crossbreed(population[rn.nextInt(population.length)]);
            population[x].mutate((float)0.01); //Colour chromosome will mutate 1% of the time
        }
        Arrays.sort(population); //Resorts array
    }

    //Creates structure of rectangles
    private static int createStructure(int[][] structure){
        int squareID = 1; //Counts current rectangle

        for(int x = 0; x<structure.length; x++){
            for(int y = 0; y<structure[x].length; y++){
                //Iterates until it can fit a rectangle into an empty space
                while(structure[x][y] == 0){
                    Rectangle r1 = new Rectangle(squareID);
                    if(insertAble(r1, x, y, structure)){
                        insertSquare(r1, x, y, structure);
                        squareID++;
                    }
                }
            }
        }
        return squareID;
    }

    //Checks if rectangle can be inserted into structure
    public static boolean insertAble(Rectangle r1, int x, int y, int[][] structure){
        boolean insertable = true;
        if(r1.getWidth() + x > structure.length || r1.getHeight() + y >structure[0].length) insertable = false;
        int curX = x;
        int curY = y;
        while(insertable && curX < x + r1.getWidth()){
            while(insertable && curY < y + r1.getHeight()){
                if(structure[x][y] != 0) insertable = false;
                curY++;
            }
            curX++;
        }
        return insertable;
    }

    //Inserts square into structure
    public static void insertSquare(Rectangle r1, int x, int y, int[][] structure){
        for(int currX = x; currX< r1.getWidth() + x; currX++){
            for(int currY = y; currY <r1.getHeight() + y; currY++){
                structure[currX][currY] = r1.getId();
            }
        }
    }

    //Prints summary of current population and generates image of best
    private static void printSumary(Painting[] population, int iterationNumber){
        System.out.println("Iteration: " + iterationNumber);
        System.out.println("Best: " + population[99].getCompareVal());
        System.out.println("Worst: " + population[0].getCompareVal());

        try{
            File file = new File("Images/Golden" + iterationNumber + ".png");
            ImageIO.write(population[99].generateImage(), "png", file);
        }
        catch(IOException e){
            System.out.println("Image output error");
        }
    }

    //Adds best and worst fitness to csv file, to measure performance
    private static void addToCSV(Painting[] population){
        try{
            FileWriter writer = new FileWriter("Images/stats.csv", true);
            writer.append(population[0].getCompareVal() + "," + population[99].getCompareVal() + "\n");
            writer.close();
        }
        catch(IOException e){
            System.out.println("Statistics file error");
        }
    }

}
