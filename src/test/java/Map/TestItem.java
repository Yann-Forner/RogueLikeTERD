package Map;

import Model.Entitys.Items.AbstractItem;
import Model.Entitys.Items.Foods.FoodFactory;
import Model.Entitys.Items.Potions.PotionFactory;
import Model.Entitys.Player.Player;
import Model.Entitys.Player.Classes.ClassFactory;
import Model.Map.Etage;
import Model.Map.Map;
import Model.Utils.Position;
import Model.Utils.Start;
import Model.Utils.TourManager;
import junit.framework.TestCase;
import org.junit.Test;

public class TestItem extends TestCase {

    @Test
    public void testSpawnItemSameLocation() {
        Player player = ClassFactory.getNewPlayer("Testeur", ClassFactory.Class.ARCHER);
        TourManager tm = new TourManager(player);
        tm.setMap();
        Start.setTourManager(tm);
        Map map = tm.getMap();
        Etage etage = map.getCurrent();

        AbstractItem i1 = PotionFactory.getNewPotion(etage, PotionFactory.PotionType.HEAL_POTION);
        AbstractItem i2 = FoodFactory.getNewFood(etage, FoodFactory.FoodType.APPLE);
        i2.setPosition(new Position(50, 50));
        i1.setPosition(new Position(50, 50));

        etage.getItems().add(i1);
        etage.getItems().add(i2);
    }

    @Test
    public void testItemSpawnLocation() {
        Player player = ClassFactory.getNewPlayer("Testeur", ClassFactory.Class.ARCHER);
        TourManager tm = new TourManager(player);
        tm.setMap();
        Start.setTourManager(tm);
        Map map = tm.getMap();
        Etage etage = map.getCurrent();


        AbstractItem i1 = PotionFactory.getNewPotion(etage, PotionFactory.PotionType.HEAL_POTION);
        etage.getItems().add(i1);

        assertTrue(etage.get(i1.getPosition()).isAccesible());
    }

    @Test
    public void testCellUpdatedState() {
        Player player = ClassFactory.getNewPlayer("Testeur", ClassFactory.Class.ARCHER);
        TourManager tm = new TourManager(player);
        tm.setMap();
        Start.setTourManager(tm);
        Map map = tm.getMap();
        Etage etage = map.getCurrent();


        AbstractItem i1 = PotionFactory.getNewPotion(etage, PotionFactory.PotionType.HEAL_POTION);
        etage.getItems().add(i1);

        assertTrue(etage.get(i1.getPosition()).getEntity() != null);
    }

    @Test
    public void testPotionHeal() {
        Player player = ClassFactory.getNewPlayer("Testeur", ClassFactory.Class.ARCHER);
        TourManager tm = new TourManager(player);
        tm.setMap();
        Start.setTourManager(tm);
        Map map = tm.getMap();
        Etage etage = map.getCurrent();


        AbstractItem i1 = PotionFactory.getNewPotion(etage, PotionFactory.PotionType.HEAL_POTION);
        etage.getItems().add(i1);

        player.updatePV(-10, true);
        i1.useItem(player);
        assertTrue(player.getPv() <= player.getMAX_PV());
    }
}
