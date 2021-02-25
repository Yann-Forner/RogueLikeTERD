import java.util.Random;

public class Procedure {

    public Procedure(){
        Random rand = new Random();
        int SIZEX = rand.nextInt(Room.MaxSize - Room.MinSize + 1) + Room.MinSize;
        int SIZEY = rand.nextInt(Room.MaxSize - Room.MinSize + 1) + Room.MinSize;
    }
}
