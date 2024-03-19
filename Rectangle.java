import java.util.Random;

public class Rectangle {
    private int width, height, id;
    public Rectangle(int id){
        Random rn = new Random();
        width = 64 / (1 << rn.nextInt(2, 6));
        height = 64 / (1 << rn.nextInt(2, 6));
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
