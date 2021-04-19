import Model.Utils.Start;

import java.io.Console;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            Start.startGame();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
