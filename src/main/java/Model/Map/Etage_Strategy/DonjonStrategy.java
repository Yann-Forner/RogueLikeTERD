package Model.Map.Etage_Strategy;

import Model.Entitys.Items.Foods.FoodFactory;
import Model.Entitys.Items.Potions.PotionFactory;
import Model.Entitys.Monsters.MonsterFactory;
import Model.Map.Cell;
import Model.Map.Etage;
import Model.Map.RoomFactory;
import Model.Utils.Affichage;
import Model.Utils.Procedure;

import java.util.ArrayList;
import java.util.Objects;

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
    public void composeEtage(Etage etage, boolean etageDepart) {
        ArrayList<RoomFactory.roomType> roomsOfEtage = new ArrayList<>();
        int CircleRooms = 0, NormalRooms = 0, MarchandRooms = 0,nbrCurrentRooms = 0;
        while (nbrCurrentRooms< getNbrMaxRoom()){
            RoomFactory.roomType roomType = myRooms.get(Procedure.getRandomInt(myRooms.size()-1,0));
            if(roomType != RoomFactory.roomType.MARCHAND || MarchandRooms == 0){
                if(MarchandRooms==0){
                    MarchandRooms++;
                }
                nbrCurrentRooms++;
                roomsOfEtage.add(roomType);
                switch (roomType){
                    case NORMAL -> NormalRooms++;
                    case CIRCLENORMAL -> CircleRooms++;
                }
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
        setSpecialCell(etage, etageDepart);
        setMonsters(etage);
        setItems(etage);
    }

    @Override
    public void setItems(Etage e) {
        for (int i = 0; i < Procedure.getRandomInt(3,0); i++) {
            e.addItem(PotionFactory.getNewPotion(e, PotionFactory.PotionType.HEAL_POTION));
            e.addItem(PotionFactory.getNewPotion(e, PotionFactory.PotionType.STRENGTH_POTION));
        }
        for (int i = 0; i < Procedure.getRandomInt(4,0); i++) {
            int rand = Procedure.getRandomInt(1, 0);
            if(rand == 0)
                e.addItem(FoodFactory.getNewFood(e, FoodFactory.FoodType.APPLE));
            else
                e.addItem(FoodFactory.getNewFood(e, FoodFactory.FoodType.CARROT));
        }

        super.setItems(e);
    }

    @Override
    public int getNbrMaxRoom() {
        return 8;
    }
}
