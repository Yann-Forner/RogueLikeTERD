package Model.Map.Etage_Strategy;

import Model.Entitys.Items.Misc.Chest;
import Model.Entitys.Monsters.MonsterFactory;
import Model.Map.Cell;
import Model.Map.Etage;
import Model.Map.Room;
import Model.Map.RoomFactory;
import Model.Utils.Affichage;
import Model.Utils.Position;
import Model.Utils.Procedure;
import Model.Utils.Tools;

import java.util.ArrayList;
import java.util.Objects;

/**
 * L'etage piege est un étage ressemblant à un labyrinthe.
 * @author Quentin
 */
public class TrapEtageStrategy extends EtageStrategy{
    @Override
    public void composeEtage(Etage etage, boolean etageDepart) {
        Procedure.setRandomRooms(etage,this, RoomFactory.roomType.TRAP);
        EtageFusion(etage,new Cell.Style(Cell.Style.CellType.NORMAL, Affichage.BRIGTH_PURPLE));
        setSpecialCell(etage,etageDepart);
        setItems(etage);
        setMonsters(etage);
    }

    @Override
    public void setSpecialCell(Etage etage, boolean etageDepart) {
        Procedure.setRandomUP(etage);
    }

    @Override
    public void EtageFusion(Etage etage, Cell.Style style_fusion) {
        ArrayList<Position> doors = new ArrayList<>();
        for(Room r : etage.getRooms()) {
            for (int i = 0; i < Procedure.getRandomInt(3,1); i++) {
                Position pos = Procedure.getRelativeRandomPosition(r);
                r.get(pos).updateCell(true, style_fusion);
                doors.add(pos.somme(r.getAbsolutePos()));
            }
        }
        for (int i = 0; i < doors.size()-1; i++) {
            for(Position p : Tools.Astar(etage,doors.get(i),doors.get(i+1), Tools.PathType.LABY)){
                etage.get(p).updateCell(true, style_fusion);
            }
        }
        cleanFusion(etage,style_fusion);
    }

    @Override
    public void setMonsters(Etage etage) {
        int rand;
        rand = Procedure.getRandomInt(7, 1);
        for(int i = 0; i < rand; i++) {
            etage.addMonster(Objects.requireNonNull(MonsterFactory.getNewMonster(etage, MonsterFactory.MonsterType.RAT)));
        }
        rand = Procedure.getRandomInt(2, 1);
        for(int i = 0; i < rand; i++) {
            etage.addMonster(Objects.requireNonNull(MonsterFactory.getNewMonster(etage, MonsterFactory.MonsterType.ALIEN)));
        }

        rand = Procedure.getRandomInt(1, 0);
        if(rand == 0) {
            for (int i = 0; i < Procedure.getRandomInt(5, 0); i++) {
                etage.addMonster(Objects.requireNonNull(MonsterFactory.getNewMonster(etage, MonsterFactory.MonsterType.GHOST)));
            }
        }
        super.setMonsters(etage);
    }

    @Override
    public void setItems(Etage e) {
        e.addItem(new Chest(e, Procedure.getAccesibleRandomPosition(true, e)));
        super.setItems(e);
    }

    @Override
    public int getNbrMaxRoom() {
        return 20;
    }
}
