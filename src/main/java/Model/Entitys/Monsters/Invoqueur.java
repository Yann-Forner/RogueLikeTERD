package Model.Entitys.Monsters;

import Model.Map.Cell;
import Model.Map.Etage;
import Model.Utils.Affichage;
import Model.Utils.Position;
import Model.Utils.Start;
import Model.Utils.TourManager;

import java.util.ArrayList;

public class Invoqueur extends AbstractMonster {
    public Invoqueur(Etage m, Position pos, String nom, int pv, int force, double vision_radius , int agro, int update_rate, int pathCross, int lvl) {
        super(m, pos, nom, pv, force, vision_radius, agro, update_rate, pathCross, lvl);
    }

    @Override
    public void updateMonster() {
        super.updateMonster();
        TourManager.addMessage("OK");
        ArrayList<ArrayList<Position>> waves = new ArrayList<>();
        for (int i = 0; i < getVision_radius()*2+20; i++) {
            waves.add(new ArrayList<>());
        }

        int posX = getPosition().getX();
        int posY = getPosition().getY();
        for(int x = posX - (int)getVision_radius(); x < posX + getVision_radius()*2 -1; x++) {
            for (int y = posY - (int)getVision_radius(); y < posY + getVision_radius()*2 -1; y++) {
                Position pos = new Position(x,y);
                int distance = (int)pos.Distance(getPosition());
                TourManager.addMessage(String.valueOf(distance));
                waves.get(distance).add(pos);
            }
        }

        TourManager.addMessage("WAVES");
        for (ArrayList<Position> a : waves){
            for (Position p : a){
                getEtage().get(p).updateCell(true,new Cell.Style(Cell.Style.CellType.NORMAL,Affichage.BLUE));
            }
            for (int i = 0; i < a.size(); i++) {
                switch (i){
                    case 0 -> {
                        getEtage().get(a.get(i)).updateCell(true, new Cell.Style(Cell.Style.CellType.NORMAL, Affichage.BLUE));
                        break;
                    }
                    case 1 -> {
                        getEtage().get(a.get(i)).updateCell(true, new Cell.Style(Cell.Style.CellType.NORMAL, Affichage.RED));
                        break;
                    }
                    case 2 -> {
                        getEtage().get(a.get(i)).updateCell(true, new Cell.Style(Cell.Style.CellType.NORMAL, Affichage.GREEN));
                        break;
                    }
                }
            }
        }
        Start.end();
    }

    @Override
    public String toString() {
        if(System.getProperty("os.name").equals("Linux")){
            return "\uD83D\uDDFD";
        }
        else{
            return Affichage.RED+Affichage.BOLD+"I";
        }
    }
}
