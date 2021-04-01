package Map;

import Model.Entitys.BasicPlayer;
import Model.Map.Cell;
import Model.Map.Etage;
import Model.Map.Map;
import Model.Utils.Position;
import Model.Utils.Procedure;
import Model.Utils.Tools;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;

public class TestAstar extends TestCase {

    @Test(timeout=50)
    public void testEfficiencyAstarPlayerToTrapRoom() {
        Map map = new Map();
        Etage etage = map.getCurrent();
        BasicPlayer player = map.getPlayer();

        ArrayList<Position> cheminTrapRoom = Tools.Astar(etage, player.getPosition(), etage.getTrapCellPosition(), Tools.PATH_CROSS);
        System.out.println("[EFFICIENCY] Taille chemin : " + cheminTrapRoom.size());
    }

    @Test
    public void testAstarPlayer() {
        Map map = new Map();
        Etage etage = map.getCurrent();
        BasicPlayer player = map.getPlayer();

        ArrayList<Position> cheminRandom = Tools.Astar(etage, player.getPosition(), Procedure.getAccesibleRandomPosition(false, etage), Tools.PATH_CROSS);
        ArrayList<Position> cheminTrapRoom = Tools.Astar(etage, player.getPosition(), etage.getTrapCellPosition(), Tools.PATH_CROSS);
        ArrayList<Position> reversedCheminTrapRoom = Tools.Astar(etage, etage.getTrapCellPosition(), player.getPosition(), Tools.PATH_CROSS);

        Position outsidePos = new Position(0, 0);
        while(etage.Cells.get(outsidePos.getY()).get(outsidePos.getX()).getType() != Cell.CellType.VOID) {
            if(outsidePos.getX() == 39)
            {
                outsidePos = new Position(0, outsidePos.getY() + 1);
            }
            outsidePos = new Position(outsidePos.getX() + 1, outsidePos.getY());
        }
        Position wallPos = new Position(0, 0);
        while(etage.Cells.get(wallPos.getY()).get(wallPos.getX()).getType() != Cell.CellType.BORDER) {
            if(wallPos.getX() == 39)
            {
                wallPos = new Position(0, wallPos.getY() + 1);
            }
            wallPos = new Position(wallPos.getX() + 1, wallPos.getY());
        }
        ArrayList<Position> cheminOutside = Tools.Astar(etage, player.getPosition(), outsidePos, Tools.PATH_CROSS);
        ArrayList<Position> cheminWall = Tools.Astar(etage, player.getPosition(), wallPos, Tools.PATH_CROSS);

        assertFalse(cheminRandom == null || cheminRandom.size() == 0);
        assertFalse(cheminTrapRoom == null || cheminTrapRoom.size() == 0);
        assertFalse(reversedCheminTrapRoom == null || reversedCheminTrapRoom.size() == 0);
        assertTrue(cheminOutside == null || cheminOutside.size() == 0);
        assertTrue(cheminWall == null || cheminWall.size() == 0);
    }

    @Test
    public void testMapTrapRoom() {
        Map map = new Map();
        Etage etage = map.getCurrent();
        BasicPlayer player = map.getPlayer();

        ArrayList<ArrayList<Cell>> oldCells = etage.Cells;
        map.TRAP_ROOM();
        ArrayList<ArrayList<Cell>> newCells = map.getCurrent().Cells;

        assertFalse(oldCells.equals(newCells));
    }
}
