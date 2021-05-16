package Map;

import Model.Entitys.Items.Potions.AbstractPotion;
import Model.Entitys.Items.Potions.HealPotion;
import Model.Entitys.Items.Potions.PotionFactory;
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

        assertTrue(previous <= next);
    }

    @Test
    public void testDropLocation() {
        Player player = ClassFactory.getNewPlayer("Testeur", ClassFactory.Class.ARCHER);
        TourManager tm = new TourManager(player);
        tm.setMap();
        Start.setTourManager(tm);
        Map map = tm.getMap();
        Etage etage = player.getEtage();

        AbstractPotion hp = PotionFactory.getNewPotion(etage, PotionFactory.PotionType.HEAL_POTION);
        etage.addItem(hp);
        hp.onContact(player);
        player.getInventory().dropItem(player, player.getInventory().getCurrentPotion());

        int differenceX = Math.abs(player.getPosition().getX() - hp.getPosition().getX());
        int differenceY = Math.abs(player.getPosition().getY() - hp.getPosition().getY());
        System.out.println("Distance X potion drop et joueur : " + differenceX);
        System.out.println("Distance Y potion drop et joueur : " + differenceY);
        assertTrue(differenceX < 2 && differenceY < 2);
    }

    @Test
    public void testDropTooMuch() {
        Player player = ClassFactory.getNewPlayer("Testeur", ClassFactory.Class.ARCHER);
        TourManager tm = new TourManager(player);
        tm.setMap();
        Start.setTourManager(tm);
        Map map = tm.getMap();
        Etage etage = player.getEtage();

        for(int i = 0; i < 10; i++) {
            AbstractPotion hp = PotionFactory.getNewPotion(etage, PotionFactory.PotionType.HEAL_POTION);
            etage.addItem(hp);
            hp.onContact(player);
            player.getInventory().dropItem(player, player.getInventory().getCurrentPotion());
        }

        System.out.println("inventory size : " + player.getInventory().getPotions().size());
        assertTrue(player.getInventory().getPotions().size() >= 2);
    }
}
