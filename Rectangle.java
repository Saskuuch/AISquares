import java.util.Random;

public class Rectangle {
    private int width, height, id;
    public Rectangle(int id){
        Random rn = new Random();
        width = 32 / (1 << rn.nextInt(2, 5));
        height = 32 / (1 << rn.nextInt(2, 5));
        this.id = id;
    }

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
