import java.util.Random;
public class Rectangle {
    private int width, height, id;

    //Creates new rectangle in range 1-32 x 1-32
    public Rectangle(int id){
        Random rn = new Random();
        width = 64 / (1 << rn.nextInt(2, 7));
        height = 64 / (1 << rn.nextInt(2, 7));
        this.id = id;
    }

    //Getters
    public int getHeight() {
        return height;
    }
    public int getWidth() {
        return width;
    }
    public int getId() {
        return id;
    }
    public int area(){
        return width * height;
    }
    public String toString(){
        return("(" + width + ", " + height + ")");
    }
}
