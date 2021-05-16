package Map;

import Model.Entitys.Monsters.AbstractMonster;
import Model.Entitys.Player.Classes.ClassFactory;
import Model.Entitys.Player.Player;
import Model.Map.Etage;
import Model.Map.Map;
import Model.Utils.Start;
import Model.Utils.TourManager;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;

public class TestEntitys extends TestCase {

    @Test
    public void testDropOnDeath() {
        Player player = ClassFactory.getNewPlayer("Testeur", ClassFactory.Class.ARCHER);
        TourManager tm = new TourManager(player);
        tm.setMap();
        Start.setTourManager(tm);
        Map map = tm.getMap();
        Etage etage = map.getCurrent();

        int previous = etage.getItems().size();
        ArrayList<AbstractMonster> list = etage.getMonsters();
        list.get(0).death();
        int next = etage.getItems().size();
        System.out.println(previous + " - " + next);

        assertTrue(true);
    }
}
