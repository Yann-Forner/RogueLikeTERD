package Model.Map.Etage_Strategy;

import Model.Entitys.Monsters.MonsterFactory;
import Model.Map.Cell;
import Model.Map.Etage;
import Model.Map.RoomFactory;
import Model.Utils.Affichage;
import Model.Utils.Procedure;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

/**
 * Classe de la strategie principale du jeu, cet etage peut etre rempli de plusieurs rooms qui seront différentes les unes des autres.
 * @author Yann,Quentin
 */
public class DonjonStrategy extends EtageStrategy {

    private final ArrayList<RoomFactory.roomType> myRooms = new ArrayList<>() {{
        add(RoomFactory.roomType.MINIBOSS);
        add(RoomFactory.roomType.NORMAL);
        add(RoomFactory.roomType.NORMAL);
        add(RoomFactory.roomType.MARCHAND);
        add(RoomFactory.roomType.TRESOR);
        add(RoomFactory.roomType.CIRCLENORMAL);
        add(RoomFactory.roomType.TRIANGLENORMAL);
    }};

    @Override
    public void composeEtage(Etage etage) {
        ArrayList<RoomFactory.roomType> roomsOfEtage = new ArrayList<>();
        int CircleRooms, NormalRooms;
        CircleRooms = NormalRooms = 0;
        for (int i = 0; i < getNbrMaxRoom() ; i++) {
            RoomFactory.roomType roomType = myRooms.get(new Random().nextInt((myRooms.size())));
            roomsOfEtage.add(roomType);
            switch (roomType){
                case NORMAL -> NormalRooms++;
                case CIRCLENORMAL -> CircleRooms++;
            }
        }
        Cell.Style fusion_style = new Cell.Style(Cell.Style.CellType.NORMAL, Affichage.GREY,".");
        if(NormalRooms==getNbrMaxRoom()){
            fusion_style = new Cell.Style(Cell.Style.CellType.NORMAL);
        }
        else if(CircleRooms==getNbrMaxRoom()){
            fusion_style = new Cell.Style(Cell.Style.CellType.NORMAL, Affichage.BLUE);
        }

        Procedure.setRandomRooms(etage,this,roomsOfEtage);
        EtageFusion(etage,fusion_style);
        setSpecialCell(etage);
        setMonsters(etage);
        setItems(etage);
    }

    @Override
    public void setMonsters(Etage etage) {
        super.setMonsters(etage);
        for(int i = 0; i < Procedure.getRandomInt(3,0); i++){
            etage.addMonster(Objects.requireNonNull(MonsterFactory.getNewMonster(etage, MonsterFactory.MonsterType.RAT)));
        }
        for(int i = 0; i < Procedure.getRandomInt(2,0); i++){
            etage.addMonster(Objects.requireNonNull(MonsterFactory.getNewMonster(etage, MonsterFactory.MonsterType.SNAIL)));
        }
    }

    @Override
    public int getNbrMaxRoom() {
        return 8;
    }
}
