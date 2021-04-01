package Model.Entitys;

import Model.Map.Etage;
import Model.Utils.Affichage;
import Model.Utils.Position;

public class BasicPlayer extends Entity {

    public BasicPlayer(Etage etage, Position position) {
        super(etage,position);
    }

    public void updateEtage(Etage etage, Position position){
        getEtage().get(getPosition()).setEntity(null);
        setEtage(etage);
        setPosition(position);
        etage.get(getPosition()).setEntity(this);
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
