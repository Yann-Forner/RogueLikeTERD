package Map;

import Model.Entitys.BasicPlayer;
import Model.Map.Cell;
import Model.Map.Etage;
import Model.Map.Map;
import Model.Utils.Position;
import Model.Utils.Procedure;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.jupiter.api.Disabled;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class TestMap extends TestCase {

    @Test
    public void testOutOfBondsCell() {
        Map map = new Map();
        Etage etage = map.getCurrent();

        try {
            Cell p = etage.Cells.get(45).get(45);
            fail("(45;45) est valide !");
        }catch(IndexOutOfBoundsException e) {
            assertTrue(true);
        }
    }

    @Disabled
    public void testCellAccess() {
        Map map = new Map();
        Etage etage = map.getCurrent();

    }

    @Test
    public void testMapRandomAccessiblePosition() {
        Map map = new Map();
        Etage etage = map.getCurrent();

        Position pf = Procedure.getAccesibleRandomPosition(false, etage);
        Position pt = Procedure.getAccesibleRandomPosition(true, etage);

        assertFalse((pf == null) && (pt == null));
    }

    @Test
    public void testMapTrapRoom() {
        Map map = new Map();
        Etage etage = map.getCurrent();

        ArrayList<ArrayList<Cell>> oldCells = etage.Cells;
        map.TRAP_ROOM();
        ArrayList<ArrayList<Cell>> newCells = map.getCurrent().Cells;

        assertFalse(oldCells.equals(newCells));
    }


}
