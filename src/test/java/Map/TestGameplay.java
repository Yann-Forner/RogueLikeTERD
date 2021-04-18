package Map;

import Model.Entitys.Player.BasicPlayer;
import Model.Map.Etage;
import Model.Map.Map;
import Model.Utils.Start;

public class TestGameplay /*extends TestCase*/ {

    protected Map map;
    protected BasicPlayer player;
    protected Etage etage;

    public TestGameplay() {
        map = new Map();
        player = Start.getPlayer();
        etage = map.getCurrent();
    }

}
