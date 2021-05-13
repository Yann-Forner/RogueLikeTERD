package Map;

import Model.Entitys.Player.Player;
import Model.Entitys.Player.Classes.ClassFactory;
import Model.Map.Etage;
import Model.Map.Map;
import Model.Utils.Start;
import Model.Utils.TourManager;

public class TestGameplay /*extends TestCase*/ {

    protected Map map;
    protected Player player;
    protected Etage etage;

    public TestGameplay() {
        Player player = ClassFactory.getNewPlayer("Testeur", ClassFactory.Class.ARCHER);
        TourManager tm = new TourManager(player);
        tm.setMap();
        Start.setTourManager(tm);
        Map map = tm.getMap();
        Etage etage = map.getCurrent();

    }

}
