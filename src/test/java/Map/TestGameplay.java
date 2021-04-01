package Map;

import Model.Entitys.BasicPlayer;
import Model.Map.Etage;
import Model.Map.Map;
import junit.framework.TestCase;

public class TestGameplay /*extends TestCase*/ {

    protected Map map;
    protected BasicPlayer player;
    protected Etage etage;

    public TestGameplay() {
        map = new Map();
        player = map.getPlayer();
        etage = map.getCurrent();
    }

}
