package Map;

import Model.Entitys.Items.Potions.HealPotion;
import Model.Entitys.Player.Player;
import Model.Entitys.Player.Classes.ClassFactory;
import Model.Map.Etage;
import Model.Map.Map;
import Model.Map.Room;
import Model.Map.Room_Strategy.NormalRoomStrategy;
import Model.Utils.Position;
import Model.Utils.Procedure;
import Model.Utils.Start;
import Model.Utils.TourManager;
import junit.framework.TestCase;

public class TestGameplay extends TestCase {

    protected Map map;
    protected Player player;
    protected Etage etage;

    public TestGameplay() {
        player = ClassFactory.getNewPlayer("Testeur", ClassFactory.Class.ARCHER);
        TourManager tm = new TourManager(player);
        tm.setMap();
        Start.setTourManager(tm);
        map = tm.getMap();
        etage = map.getCurrent();

    }

    public void Testgameplay(){

    }

    public void testPoche(){
        Position pos = Procedure.getAccesibleRandomPosition(false,etage,etage.getRooms().get(0));
        Position playerPos = player.getPosition();
        HealPotion pot = new HealPotion(etage,pos ,"potion",10);
        etage.addItem(pot);
        while (!player.getInventory().isPotionsFull())player.getInventory().addPotion(new HealPotion(etage,null,"heal",10));
        assertSame(etage.get(pos).getEntity(), pot);
        player.move(pos);
        assertSame(player,etage.get(pos).getEntity());
        assertSame(player.getPoche(), pot);
        player.move(playerPos);
        assertSame(etage.get(pos).getEntity(), pot);
    }
}
