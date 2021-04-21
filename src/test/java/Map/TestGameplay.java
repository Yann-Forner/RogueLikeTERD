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
        player = Start.getPlayer();
        map = new Map(player);
        etage = map.getCurrent();
    }

}
