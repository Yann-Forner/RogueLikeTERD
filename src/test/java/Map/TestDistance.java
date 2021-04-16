package Map;

import Model.Entitys.BasicPlayer;
import Model.Entitys.Monsters.AbstractMonster;
import Model.Entitys.Monsters.MonsterFactory;
import Model.Map.Etage;
import Model.Map.Map;
import Model.Utils.Start;
import Model.Utils.Position;
import junit.framework.TestCase;
import org.junit.Test;

public class TestDistance extends TestCase {

    @Test
    public void testDistanceBetweenTwoEntitys() {
        Map map = new Map();
        Etage etage = map.getCurrent();
        BasicPlayer player = Start.getPlayer();

        AbstractMonster z = MonsterFactory.getNewMonster(etage, MonsterFactory.MonsterType.ZOMBIE);
        etage.addMonster(z);

        Position posZ = z.getPosition();
        Position posP = player.getPosition();

        System.out.println("Position Zombie : " + posZ.toString());
        System.out.println("Position Player : " + posP.toString());
        System.out.println("Distance entre les deux : " + posZ.Distance(posP));
    }
}
