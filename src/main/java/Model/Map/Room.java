package Model.Map;

import Model.Entitys.Items.AbstractItem;
import Model.Entitys.Monsters.AbstractMonster;
import Model.Utils.Position;
import Model.Map.Room_Strategy.RoomStrategy;

/**
 * Les Rooms composents un etage.
 * @author Yann, Quentin, JP
 */
public class Room extends Etage implements Comparable<Room> {
    private Position AbsolutePos=null;
    public final RoomStrategy strategy;

    /**
     * Creation de la room.
     * @param width Largeur
     * @param height Hauteur
     * @param strategy Strategy de la room
     * @author Yann
     */
    public Room(int width, int height,RoomStrategy strategy){
        super(width,height);
        this.strategy=strategy;
        strategy.composeRoom(this);
    }

    /**
     * Ajoute les monstres de la room a l'etage.
     * @param etage Etage
     * @author Quentin
     */
    public void setMonsters(Etage etage){
        strategy.setMonsters(this);
        for(AbstractMonster a : Monsters){
            a.setEtage(etage);
            a.setPosition(a.getPosition().somme(getAbsolutePos()));
            etage.addMonster(a);
        }
        Monsters.clear();
    }

    /**
     * Ajoute les items de la room a l'etage.
     * @param etage Etage
     * @author JP
     */
    public void setItems(Etage etage) {
        strategy.setItems(this);
        for(AbstractItem a : Items) {
            a.setEtage(etage);
            a.setPosition(a.getPosition().somme(getAbsolutePos()));
            etage.addItem(a);
        }
    }

    /**
     * Verifie sa la room peut etre ajouté a l'etage.
     * @param etage Etage
     * @return boolean
     * @author Yann
     */
    public boolean noCollision(Etage etage){
        return strategy.noCollision(etage,this);
    }

    /**
     * Defini la position absolue de la room par rapport a l'etage.
     * @param pos Correspond a sa coin en haut a gauche (0,0).
     * @author Quentin
     */
    public void setAbsolutePos(Position pos){
        AbsolutePos=pos.copyOf();
    }

    /**
     * Renvoit la position absolue de la room par rapport a l'etage.
     * @return La position absolue
     * @author Quentin
     */
    public Position getAbsolutePos() {
        return AbsolutePos.copyOf();
    }

    @Override
    public int compareTo(Room o) {
        return (int) (getAbsolutePos().Distance(new Position(0,0)) - o.getAbsolutePos().Distance(new Position(0,0)));
    }
}
