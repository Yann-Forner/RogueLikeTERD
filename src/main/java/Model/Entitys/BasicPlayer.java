package Model.Entitys;

import Model.Map.Etage;
import Model.Utils.Affichage;
import Model.Utils.Position;

public class BasicPlayer extends Entity {

    public BasicPlayer(Etage etage, Position position, int vision_radius, String nom) {
        super(etage,position,vision_radius, nom,100,10);
    }

    public void updateEtage(Etage etage, Position position){
        getEtage().get(getPosition()).setEntity(null);
        setEtage(etage);
        setPosition(position);
        etage.get(getPosition()).setEntity(this);
    }

    public void moveLeft() {
        move(getPosition().somme(-1,0));
    }

    public void moveRight() {
        move(getPosition().somme(1,0));
    }

    public void moveUp() {
        move(getPosition().somme(0,-1));
    }

    public void moveDown() {
        move(getPosition().somme(0,1));
    }

    @Override
    public String toString() {
        if(System.getProperty("os.name").equals("Linux")){
            return "\uD83E\uDD13";
        }
        else{
            return Affichage.GREEN+Affichage.BOLD+"@";
        }
    }

}
