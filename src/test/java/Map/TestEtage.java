package Map;

import Exceptions.CollisionRoom;
import Model.Map.Etage;
import Model.Map.Etage_Strategy.EtageStrategy;
import Model.Map.Room;
import Model.Map.Room_Strategy.NormalRoomStrategy;
import Model.Utils.Position;
import junit.framework.TestCase;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestEtage extends TestCase {

    private class TestStrategyEtage extends EtageStrategy{

        /**
         * Genere les composants de l'étage
         *
         * @param etage etage courant
         * @author Quentin, Yann
         */
        @Override
        public void composeEtage(Etage etage) {

        }

        /**
         * Renvoit le nombre maximal de rooms dans un etage.
         *
         * @return int
         * @author Quentin
         */
        @Override
        public int getNbrMaxRoom() {
            return 10;
        }
    }

    private final Etage ETAGE   = new Etage(50,50,new TestStrategyEtage());

    public void  testIfCollide(){
        Room room = new Room(5,5,new NormalRoomStrategy());
        Room room2 = new Room(5,5,new NormalRoomStrategy());
        room.setAbsolutePos(new Position(5,5));
        room2.setAbsolutePos(new Position(5,5));
        ETAGE.addRoom(room);
        Exception exception = assertThrows(CollisionRoom.class, () -> {
            ETAGE.addRoom(room2);
        });
        String excpectedMessage ="La room ajoutée entre en collision avec une autre room";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(excpectedMessage));

        room2.setAbsolutePos(new Position(12,12));
        try {
            ETAGE.addRoom(room2);
        }catch (CollisionRoom e){
            fail();
        }

    }

    public void testAjoutEntity(){

    }

}
