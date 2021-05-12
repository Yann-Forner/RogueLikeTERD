package Model.Utils;
;
import Model.Entitys.Items.AbstractItem;
import Model.Entitys.Items.Inventory;
import Model.Entitys.Items.Weapons.AbstractWeapon;
import Model.Map.Etage;
import Model.Map.Map;
import Model.Map.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Classe servant a afficher le menu sur la droite du terminal
 * @author Yann
 */
public class Menu {
    private Etage etage;
    private Map map;
    private CaseMenu[][] menuAffichage;
    private static final int LARGEUR_MENU = 83;



    private class CaseMenu{
        public String container= " ";

        @Override
        public String toString() {
            return container;
        }
    }
    /**
     * Constructeur d'un menu
     * @param etage etage
     * @param map map en entier
     * @author Yann
     */
    public Menu(Etage etage, Map map) {
        this.etage = etage;
        this.map = map;
    }

    /**
     * Fonction servant à injecter les informations dans le menu
     * @auhtor Yann
     */
    public void refresh(){
        clear();
        String[] UpperMenu = {"║","║","║"};
        Inventory inv = Start.getPlayer().getInventory();
        String lvl = " Niveau " + Start.getPlayer().getLvl() + " [" + Start.getPlayer().getCURRENT_EXP() + "/" + Start.getPlayer().getMAX_EXP() + "] ";
        String[] niveau = {
                "╔"+"═".repeat(lvl.length())+"╗ ",
                "║"+lvl+"║ ",
                "╚"+"═".repeat(lvl.length())+"╝ "
        };
        printStringOnSide(0,"  SEED : "+Procedure.getSeed(),2);
        printStringOnSide(1,"  TIMER : "+TourManager.getTimer()+"         ",2);
        printStringOnSide(2,UpperMenu,1);
        printLine(4,Affichage.GREEN);
        printStringOnSide(0,Affichage.RED+"   Joueur : "+ Start.getPlayer().getNom() + Affichage.BLUE+" ",6);
        printStringOnSide(0,"   Classe : "+ Start.getPlayer().getClasse().getNom(),7);
        printStringOnSide(0,"   Etage n°"+(map.getIndexCurrent()+1),11);
        printStringOnSide(1,niveau,6);
        printStringOnSide(0,"   Force : "+Start.getPlayer().getForce(),8);
        int range = 1 ;
        if(inv.getWeapons().size()!=0)range = inv.getWeapons().get(0).getRange();

        printStringOnSide(0,"   Portée : "+range,9);

        printLine(12,Affichage.BLUE);

        printStringOnSide(0, "  Inventaire",14);

        var potionsList = inv.getPotions();
        var armesList = inv.getWeapons();
        printStringOnSide(0, " Vous avez " + armesList.size() + " armes :",17);

        StringBuilder armesString = new StringBuilder();
        for (int index = 0; index < armesList.size(); index++) {
            AbstractWeapon abstractWeapon = armesList.get(index);
            if (index == 0){
                if (Start.getPlayer().getClasse().canUse(abstractWeapon)) {
                    armesString.append(Affichage.GREEN);
                } else {
                    armesString.append(Affichage.RED);
                }
                armesString.append("[" + abstractWeapon.toString() +Affichage.RESET+ "]  ");
            }
            else
                armesString.append(abstractWeapon.toString()+Affichage.RESET + "  ");
        }

        printStringOnSide(0,armesString.toString(),18);

       printStringOnSide(0, " Vous avez " + potionsList.size() + " potions :",20);

       StringBuilder potionsString = new StringBuilder();
       for (int i = 0; i < potionsList.size(); i++) {
           if (i == 0)
               potionsString.append("[" + potionsList.get(i).toString()+Affichage.RESET + "]  ");
           else
               potionsString.append(potionsList.get(i).toString() +Affichage.RESET + "  ");
       }
      printStringOnSide(0, potionsString.toString(),21);
    }

    /**
     * Fonction qui met réinitialise le menu
     * @auhtor Yann
     */
    public void clear(){
        menuAffichage = new CaseMenu[etage.getHeigth()][LARGEUR_MENU];
        for (int i = 0; i < menuAffichage.length ; i++) {
            for (int j = 0; j < menuAffichage[0].length; j++) {
                menuAffichage[i][j] = new CaseMenu();
            }
        }
        printAllLine(0, "╔═════════════════════════════════════════════════════════════════════════════════╗");
        for (int i = 1; i < etage.getHeigth()-1 ; i++)printAllLine(i, "║                                                                                 ║") ;
        printAllLine(etage.getHeigth()-1, "╚═════════════════════════════════════════════════════════════════════════════════╝");
    }


    public void printAllLine(int line, String s){
        for (int i = 0; i < menuAffichage[line].length; i++) {
            if(i == menuAffichage[line].length-1) menuAffichage[line][i].container = Affichage.BLUE+s.charAt(i)+"\n";
            else
            menuAffichage[line][i].container = s.charAt(i)+"";

        }
    }

    /**
     * Trace une ligne aux coordonnés y du menu
     * @param y ligne
     * @param color couleur de la ligne
     */
    public void printLine (int  y,String color){
        printStringOnLine(1,y,color+"═══════════════════════════════════════════════════════════════════════════════════════════");

    }

    /**
     * Trace le tableau de strings aux coordonnées du menu
     * @param x Coordonnée x
     * @param y Coordonnée y
     * @param myArrayString le tableau de string

     */
    public void printOnMenu(int x, int y , String[] myArrayString){
        for (int i = 0; i < myArrayString.length; i++) {
            printStringOnLine(x,y+i,myArrayString[i]);
        }
    }

    /**
     * Trace la string aux coordonnées du menu
     * @param x Coordonnée x
     * @param y Coordonnée y
     * @param s la string
     */
    public void printStringOnLine(int x, int y ,String s){
        int xInitial = x;
        for (int i = 0; i < s.length() && i+xInitial < LARGEUR_MENU-1; i++) {
            int currenti = i;
            String res = s.charAt(currenti)+"";
            Pattern p = Pattern.compile(".\\[([0-9]{1,3})m");

            // création d'un moteur de recherche
            if( i < s.length() - 5){

                Matcher m = p.matcher(s.substring(currenti, currenti+5));

                if( m.find()){
                        int size =m.end()-m.start();
                        if( m.start() == 0){
                            res = m.group(0);
                            res += s.charAt(currenti+m.end());
                            i = i+size;
                        }
                }
            }
            modifyCase(x,y,res);
            ++x;
        }
    }

    /**
     * Trace un tableau de string d'un coté précis
     * @param side 0 = left, 1 = right, 2 = center
     * @param strings le tableau de string
     * @param y Coordonnée y
     */
    public  void printStringOnSide(int side,String[] strings ,int y){
        for (int i = 0; i < strings.length; i++) {
            printStringOnSide(side,strings[i],y+i);
        }
    }

    /**
     * Trace une string d'un coté précis
     * @param side  0 = left, 1 = right, 2 = center
     * @param s la string
     * @param y Coordonnée y
     */
    public void printStringOnSide(int side ,String s, int y ){
        switch (side){
            case 0 -> {//left
                printStringOnLine(1,y,s);
            }
            case 1 -> {//right
                printStringOnLine(LARGEUR_MENU-s.length(),y,s);
            }
            default -> {//center
                int spacing = (LARGEUR_MENU-s.length())/2;
                printStringOnLine(spacing,y,s);
            }
        }
    }



 public void modifyCase(int x , int y , String s){
        menuAffichage[y][x].container = s;
 }


    /**
     * Modifier le stringbuilder de la map pour s'afficher avec un menu
     * @param line la ligne sur laquelle on se trouve
     * @param sb le stringbuilder qui affiche le jeu
     * @author Yann
     */
    public void toStringByLine(int line ,StringBuilder sb ) {
        if (line==0)refresh();
        sb.append(Affichage.BOLD).append(Affichage.BLUE).append("     ");
        sb.append(toStringLine(line));
    }


    public String toStringLine(int line){
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < menuAffichage[line].length; i++) {
            s.append(menuAffichage[line][i]);
        }
        return s.toString();
    }

    public int numberOfColorChanges(String s){
        Pattern p = Pattern.compile(".\\[([0-9]{1,3})m");
        // création d'un moteur de recherche
        Matcher m = p.matcher(s);
        // lancement de la recherche de toutes les occurrences

        long l = (m.results().count());

        return  (int) l*2;
    }



}
