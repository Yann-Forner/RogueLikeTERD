package Map;

import Model.Entitys.Player.Classes.ClassFactory;
import Model.Map.Cell;
import Model.Map.Etage;
import Model.Map.Map;
import Model.Utils.Position;
import Model.Utils.Procedure;
import Model.Utils.Start;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.jupiter.api.Disabled;

import java.util.ArrayList;

public class TestMap extends TestCase {

    @Test
    public void testOutOfBondsCell() {
        //TODO 21/04 #JP
        Map map = new Map(ClassFactory.getNewPlayer("Testeur", ClassFactory.Class.ARCHER));
        Etage etage = map.getCurrent();

        try {
            Cell p = etage.getCells().get(45).get(45);
            fail("(45;45) est valide !");
        }catch(IndexOutOfBoundsException e) {
            assertTrue(true);
        }
    }

    @Disabled
    public void testCellAccess() {
        //TODO 21/04 #JP
        Map map = new Map(ClassFactory.getNewPlayer("Testeur", ClassFactory.Class.ARCHER));
        Etage etage = map.getCurrent();

    }

    @Test
    public void testMapRandomAccessiblePosition() {
        //TODO 21/04 #JP
        Map map = new Map(ClassFactory.getNewPlayer("Testeur", ClassFactory.Class.ARCHER));
        Etage etage = map.getCurrent();

        Position pf = Procedure.getAccesibleRandomPosition(false, etage);
        Position pt = Procedure.getAccesibleRandomPosition(true, etage);

        assertFalse((pf == null) && (pt == null));
    }

    @Test
    public void testMapTrapRoom() {
        //TODO 21/04 #JP
        Map map = new Map(ClassFactory.getNewPlayer("Testeur", ClassFactory.Class.ARCHER));
        Etage etage = map.getCurrent();

        ArrayList<ArrayList<Cell>> oldCells = etage.getCells();
        map.TRAP_ROOM();
        ArrayList<ArrayList<Cell>> newCells = map.getCurrent().getCells();

        assertFalse(oldCells.equals(newCells));
    }


}
