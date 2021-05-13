package Map;

import Model.Entitys.Player.Player;
import Model.Entitys.Monsters.AbstractMonster;
import Model.Entitys.Monsters.MonsterFactory;
import Model.Entitys.Player.Classes.ClassFactory;
import Model.Map.Etage;
import Model.Map.Map;
import Model.Utils.Start;
import Model.Utils.Position;
import Model.Utils.TourManager;
import junit.framework.TestCase;
import org.junit.Test;

public class TestDistance extends TestCase {

    @Test
    public void testDistanceBetweenTwoEntitys() {
        Player player = ClassFactory.getNewPlayer("Testeur", ClassFactory.Class.ARCHER);
        TourManager tm = new TourManager(player);
        tm.setMap();
        Start.setTourManager(tm);
        Map map = tm.getMap();
        Etage etage = map.getCurrent();


        AbstractMonster z = MonsterFactory.getNewMonster(etage, MonsterFactory.MonsterType.ZOMBIE);
        etage.addMonster(z);

        Position posZ = z.getPosition();
        Position posP = player.getPosition();

        System.out.println("Position Zombie : " + posZ.toString());
        System.out.println("Position Player : " + posP.toString());
        System.out.println("Distance entre les deux : " + posZ.Distance(posP));
    }
}
