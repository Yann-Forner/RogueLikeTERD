package Model.Utils;

import Model.Entitys.AbstractItem;
import Model.Entitys.Inventaires.Inventory;
import Model.Map.Etage;
import Model.Map.Map;

import java.util.ArrayList;

public class Menu {

    private Etage etage;


    private Map map;


    public Menu(Etage etage, Map map) {
        this.etage = etage;
        this.map = map;
    }

    public StringBuilder toStringByLine(int line ,StringBuilder sb ) {
        Inventory inv = Start.getPlayer().getInventory();
        var itemsList = inv.getItems();

        //Menu
        sb.append(Affichage.BOLD).append(Affichage.BLUE).append("     ");
        if(line==0){
            sb.append("╔═════════════════════════════════════════════════════════════════════════════════╗");
        }
        else if(line==etage.getHeigth()-1){
            sb.append("╚═════════════════════════════════════════════════════════════════════════════════╝");
        }
        else{
            if(line==1){
                sb.append(print_txt_in_menu_leftorRight(true,"  Etage n°"+(map.getIndexCurrent()+1)+ " | Joueur : "+ Start.getPlayer().getNom()));
            } else if (line==7 && Start.getPlayer()!= null){
                String equipement = "Equipement : ";
                if (Start.getPlayer().getInventory().getCurrentWeapon()!=null)equipement+=Start.getPlayer().getInventory().getCurrentWeapon().getNom()+"/";
                else equipement+="X/";
                if (Start.getPlayer().getInventory().getCurrentArmure()!=null)equipement+=Start.getPlayer().getInventory().getCurrentArmure().getNom();
                else equipement+="X";
                sb.append(print_txt_in_menu_center(equipement));
            }
            else if(line == etage.getHeigth() - 4) {
                sb.append(print_txt_in_menu_leftorRight(true, "Vous avez " +  itemsList.size() + " potions :"));
            }
            else if(line == etage.getHeigth() - 3) {
                StringBuilder potionsString = new StringBuilder();
                for(int i = 0; i < itemsList.size(); i++) {
                    if(i == 0)
                        potionsString.append("[" + itemsList.get(i).toString() + "] ");
                    else
                        potionsString.append(itemsList.get(i).toString() + " ");
                }
                sb.append(print_txt_in_menu_leftorRight(true, potionsString.toString()));
            }
            else {
                sb.append("║                                                                                 ║");
            }
        }
        sb.append("\n");


        return sb;
    }
    public static String print_txt_in_menu_leftorRight(boolean left, String message){
        StringBuilder sb = new StringBuilder();
        if(message.length()<81){
            sb.append("║");
            int spacing = (81 - message.length());
            if (!left)sb.append(" ".repeat(spacing));
            sb.append(message);
            if(left)sb.append(" ".repeat(spacing));
            sb.append("║");
        }else{
            sb.append("║                                                                                 ║");
        }
        return sb.toString();
    }
    public static String print_txt_in_menu_center(String message){//81
        StringBuilder sb = new StringBuilder();
        if(message.length()<81){
            sb.append("║");
            int spacing = (81 - message.length())/2;
            sb.append(" ".repeat(spacing));
            sb.append(message);
            if((message.length()%2==0))++spacing;
            sb.append(" ".repeat(spacing));
            sb.append("║");
        }else if (message.length()>81){
            sb.append("║                                                                                 ║");
        }
        else {
            sb.append("║").append(message).append("║");
        }

        return sb.toString();
    }

    public StringBuilder menuToStringBuilder(){
        StringBuilder sb= new StringBuilder();
        for (int i = 0; i < etage.getHeigth(); i++) {
            this.toStringByLine(i ,sb);
        }
        return sb;
    }
}
