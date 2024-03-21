import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

import javax.imageio.ImageIO;
public class Algorithm {
    public static void main(String[] args){
        int[][] structure = new int[213][299];
        int colourCount = createStructure(structure);
        Painting[] population = createPopulation(100, structure, colourCount);
        Arrays.sort(population);
        System.out.println(population[99].getCompareVal());
        System.out.println(population[0].getCompareVal());

        for(int x = 0; x<100; x++){
            generation(population);
        }
        printSumary(population, 100);
        for(int x = 0; x<400; x++){
            generation(population);
        }
        printSumary(population, 500);
        for(int x = 0; x<500; x++){
            generation(population);
        }
        printSumary(population, 1000);
    }

    private static void printSumary(Painting[] population, int iterationNumber){
        System.out.println("Iteration: " + iterationNumber);
        System.out.println("Best: " + population[99].getCompareVal());
        System.out.println("Worst: " + population[0].getCompareVal());

        try{
            File file = new File("Images/AIDog" + iterationNumber + ".png");
            ImageIO.write(population[99].generateImage(), "png", file);
        }
        catch(IOException e){
            System.out.println("Poo Poo");
        }
    }

    private static Painting[] createPopulation(int size, int[][] structure, int colourCount){
        Painting[] population = new Painting[size];
        for(int x = 0; x<size; x++){
            population[x] = new Painting(structure, colourCount);
        }
        return population;
    }

    private static int createStructure(int[][] structure){
        int squareID = 1;

        for(int x = 0; x<structure.length; x++){
            for(int y = 0; y<structure[x].length; y++){
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

    public static void generation(Painting[] population){
        Random rn = new Random();
        for(int x = 0; x < 30; x++){
            population[x] = population[population.length - x -1].crossbreed(population[rn.nextInt(population.length)]);
            population[x].mutate((float)0.01);
        }
        Arrays.sort(population);
    }

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

    public static void insertSquare(Rectangle r1, int x, int y, int[][] structure){
        for(int currX = x; currX< r1.getWidth() + x; currX++){
            for(int currY = y; currY <r1.getHeight() + y; currY++){
                structure[currX][currY] = r1.getId();
            }
        }
    }
}
