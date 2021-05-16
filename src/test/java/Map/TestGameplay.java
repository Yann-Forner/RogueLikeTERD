package Map;

import Model.Entitys.Items.AbstractItem;
import Model.Entitys.Items.Potions.PotionFactory;
import Model.Entitys.Player.Classes.ClassFactory;
import Model.Entitys.Player.Player;
import Model.Map.Etage;
import Model.Map.Map;
import Model.Utils.Position;
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

    public void testPoche(){
        System.out.println("----------------ETAGE DE BASE----------------");
        System.out.println(etage);
        AbstractItem item = PotionFactory.getNewPotion(etage, PotionFactory.PotionType.HEAL_POTION);
        Position up = item.getPosition().somme(Position.Direction.HAUT.getVecteur());
        player.move(up);
        assertSame(player.getPosition(),up);
        Position playerPos = player.getPosition();
        etage.addItem(item);
        while (!player.getInventory().isPotionsFull()){
            player.getInventory().addPotion(PotionFactory.getNewPotion(etage, PotionFactory.PotionType.HEAL_POTION));
        }
        System.out.println("----------------AVEC ITEM----------------");
        System.out.println(etage);
        player.moveDirection(Position.Direction.BAS);
        System.out.println("----------------DEPLACEMENT SUR ITEM----------------");
        System.out.println(etage);
        assertSame(player,etage.get(item.getPosition()).getEntity());
        assertSame(player.getPoche(), item);
        assertNotSame(player.getPosition(),playerPos);
        try {
            Thread.sleep(player.getClasse().getSpeed());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        player.moveDirection(Position.Direction.BAS);
        System.out.println("----------------DEPLACEMENT HORS ITEM----------------");
        System.out.println(player.getPoche());
        System.out.println(etage);
    }
}
