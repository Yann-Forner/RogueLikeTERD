import Model.Utils.Affichage;
import Model.Utils.Start;

public class Main {
    public static void main(String[] args) {
        try{
            Start.StartGame();
        }
        catch (Exception e){
            Start.setConsoleMode(false);
            System.out.println(Affichage.RED);
            e.printStackTrace();
            System.exit(1);
        }
    }
}
