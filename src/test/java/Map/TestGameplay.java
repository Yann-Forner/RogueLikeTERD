package Map;

import Model.Entitys.Player.BasicPlayer;
import Model.Entitys.Player.Classes.ClassFactory;
import Model.Map.Etage;
import Model.Map.Map;
import Model.Utils.Start;
import Model.Utils.TourManager;

public class TestGameplay /*extends TestCase*/ {

    protected Map map;
    protected BasicPlayer player;
    protected Etage etage;

    public TestGameplay() {
        BasicPlayer player = ClassFactory.getNewPlayer("Testeur", ClassFactory.Class.ARCHER);
        TourManager tm = new TourManager(player);
        tm.setMap();
        Start.setTourManager(tm);
        Map map = tm.getMap();
        Etage etage = map.getCurrent();

    }

}
