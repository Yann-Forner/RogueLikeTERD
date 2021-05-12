package Map;

import Model.Entitys.Items.AbstractItem;
import Model.Entitys.Items.Potions.PotionFactory;
import Model.Entitys.Player.BasicPlayer;
import Model.Entitys.Player.Classes.ClassFactory;
import Model.Map.Cell;
import Model.Map.Etage;
import Model.Map.Map;
import Model.Utils.*;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;

public class TestAstar extends TestCase {


    @Test(timeout=100)
    public void testEfficiencyAstarPlayerToRandomEntity() {
        BasicPlayer player = ClassFactory.getNewPlayer("Testeur", ClassFactory.Class.ARCHER);
        TourManager tm = new TourManager(player);
        tm.setMap();
        Start.setTourManager(tm);
        Map map = tm.getMap();
        Etage etage = map.getCurrent();
        AbstractItem i1 = PotionFactory.getNewPotion(etage, PotionFactory.PotionType.HEAL_POTION);
        etage.getItems().add(i1);

        System.out.println("Item position : " + i1.getPosition());
        ArrayList<Position> cheminitem = Tools.Astar(etage, player.getPosition(), i1.getPosition(), Tools.PathType.CROSS);
    }

    @Test
    public void testAstarPlayer() {
        BasicPlayer player = ClassFactory.getNewPlayer("Testeur", ClassFactory.Class.ARCHER);
        TourManager tm = new TourManager(player);
        tm.setMap();
        Start.setTourManager(tm);
        Map map = tm.getMap();
        Etage etage = map.getCurrent();


        ArrayList<Position> cheminRandom = Tools.Astar(etage, player.getPosition(), Procedure.getAccesibleRandomPosition(false, etage), Tools.PathType.CROSS);

        Position outsidePos = new Position(0, 0);
        while(etage.getCells().get(outsidePos.getY()).get(outsidePos.getX()).getType() != Cell.Style.CellType.VOID) {
            if(outsidePos.getX() == 39)
            {
                outsidePos = new Position(0, outsidePos.getY() + 1);
            }
            outsidePos = new Position(outsidePos.getX() + 1, outsidePos.getY());
        }
        Position wallPos = new Position(0, 0);
        while(etage.getCells().get(wallPos.getY()).get(wallPos.getX()).getType() != Cell.Style.CellType.BORDER) {
            if(wallPos.getX() == 39)
            {
                wallPos = new Position(0, wallPos.getY() + 1);
            }
            wallPos = new Position(wallPos.getX() + 1, wallPos.getY());
        }
        ArrayList<Position> cheminOutside = Tools.Astar(etage, player.getPosition(), outsidePos, Tools.PathType.CROSS);
        ArrayList<Position> cheminWall = Tools.Astar(etage, player.getPosition(), wallPos, Tools.PathType.CROSS);

        assertFalse(cheminRandom == null || cheminRandom.size() == 0);
        assertTrue(cheminOutside == null || cheminOutside.size() == 0);
        assertTrue(cheminWall == null || cheminWall.size() == 0);
    }

    @Test
    public void testMapTrapRoom() {
        BasicPlayer player = ClassFactory.getNewPlayer("Testeur", ClassFactory.Class.ARCHER);
        TourManager tm = new TourManager(player);
        tm.setMap();
        Start.setTourManager(tm);
        Map map = tm.getMap();
        Etage etage = map.getCurrent();


        ArrayList<ArrayList<Cell>> oldCells = etage.getCells();
        map.TRAP_ROOM();
        ArrayList<ArrayList<Cell>> newCells = map.getCurrent().getCells();

        assertFalse(oldCells.equals(newCells));
    }
}
