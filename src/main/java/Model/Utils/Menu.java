package Model.Utils;

import Model.Entitys.Inventaires.Inventory;
import Model.Map.Etage;
import Model.Map.Map;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe servant a afficher le menu sur la droite du terminal
 * @author Yann
 */
public class Menu {
    private Etage etage;
    private Map map;
    private String[] menuAffichage;
    private String[] colors;
    private static final int LARGEUR_MENU = 81;
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
        printStringOnSide(0,"  SEED : "+Procedure.getSeed(),2,Affichage.GREEN);
        printStringOnSide(1,"  TIMER : "+TourManager.getTimer()+"         ",2,Affichage.GREEN);
        printStringOnSide(2,UpperMenu,1, Affichage.GREEN);
        printLine(4,Affichage.GREEN);
        printStringOnSide(0,"   Joueur : "+ Start.getPlayer().getNom(),6,Affichage.RED);
        printStringOnSide(0,"   Classe : "+ Start.getPlayer().getClasse().getNom(),7,Affichage.RED);
        printStringOnSide(0,"   Etage n°"+(map.getIndexCurrent()+1),11,Affichage.YELLOW);
        printStringOnSide(1,niveau,6,Affichage.RED);
        printStringOnSide(0,"   Force : "+Start.getPlayer().getForce(),8,Affichage.RED);
        int range = 1 ;
        if(inv.getWeapons().size()!=0)range = inv.getWeapons().get(0).getRange();

        printStringOnSide(0,"   Portée : "+range,9,Affichage.RED);

        printLine(12,Affichage.BLUE);

        printStringOnSide(0, "  Inventaire",14,Affichage.PURPLE);



        var potionsList = inv.getPotions();
        var armesList = inv.getWeapons();
        printStringOnSide(0, " Vous avez " + armesList.size() + " armes :",17 , Affichage.RED);

        StringBuilder armesString = new StringBuilder();
        for (int index = 0; index < armesList.size(); index++) {
            if (index == 0)
                armesString.append("[" + armesList.get(index).toString() + "] ");
            else
                armesString.append(armesList.get(index).toString() + " ");
        }

        printStringOnSide(0,armesString.toString(),18,Affichage.RED);

       printStringOnSide(0, " Vous avez " + potionsList.size() + " potions :",20, Affichage.RED);

       StringBuilder potionsString = new StringBuilder();
       for (int i = 0; i < potionsList.size(); i++) {
           if (i == 0)
               potionsString.append("[" + potionsList.get(i).toStringWithoutColor() + "] ");
           else
               potionsString.append(potionsList.get(i).toStringWithoutColor() + " ");
       }
      printStringOnSide(0, potionsString.toString(),21, Affichage.RED);
    }

    /**
     * Fonction qui met réinitialise le menu
     * @auhtor Yann
     */
    public void clear(){
        menuAffichage = new String[etage.getHeigth()];
        menuAffichage[0] = "╔═════════════════════════════════════════════════════════════════════════════════╗\n";
        for (int i = 1; i < etage.getHeigth()-1 ; i++)menuAffichage[i]= "║                                                                                 ║\n" ;
        menuAffichage[etage.getHeigth()-1] = "╚═════════════════════════════════════════════════════════════════════════════════╝\n";
        colors = new String[etage.getHeigth()];
    }

    /**
     * Trace une ligne aux coordonnés y du menu
     * @param y ligne
     * @param color couleur de la ligne
     */
    public void printLine (int  y,String color){
        printStringOnLine(1,y,"═════════════════════════════════════════════════════════════════════════════════",color);

    }

    /**
     * Trace le tableau de strings aux coordonnées du menu
     * @param x Coordonnée x
     * @param y Coordonnée y
     * @param myArrayString le tableau de string
     * @param color couleur des lignes du tableau
     */
    public void printOnMenu(int x, int y , String[] myArrayString, String color){
        for (int i = 0; i < myArrayString.length; i++) {
            printStringOnLine(x,y+i,myArrayString[i],color);
        }
    }

    /**
     * Trace la string aux coordonnées du menu
     * @param x Coordonnée x
     * @param y Coordonnée y
     * @param s la string
     * @param color couleur de la ligne du string
     */
    public void printStringOnLine(int x, int y ,String s, String color){
        if (s.length() > LARGEUR_MENU) s = s.substring(0, LARGEUR_MENU);
        putColor(y,color);
        for (int i = 0; i < s.length() ; i++) {
            menuAffichage[y] = setCharAt(menuAffichage[y],x+i,s.charAt(i));
        }

    }

    /**
     * Trace un tableau de string d'un coté précis
     * @param side 0 = left, 1 = right, 2 = center
     * @param strings le tableau de string
     * @param y Coordonnée y
     * @param color  couleur des lignes du tableau
     */
    public  void printStringOnSide(int side,String[] strings ,int y, String color){
        for (int i = 0; i < strings.length; i++) {
            printStringOnSide(side,strings[i],y+i,color);
        }
    }

    /**
     * Trace une string d'un coté précis
     * @param side  0 = left, 1 = right, 2 = center
     * @param s la string
     * @param y Coordonnée y
     * @param color couleur de la ligne du string
     */
    public void printStringOnSide(int side ,String s, int y , String color){
        switch (side){
            case 0 -> {//left
                printStringOnLine(1,y,s,color);
            }
            case 1 -> {//right
                printStringOnLine(LARGEUR_MENU-s.length(),y,s,color);
            }
            default -> {//center
                int spacing = (LARGEUR_MENU-s.length())/2;
                printStringOnLine(spacing,y,s,color);
            }
        }
    }

    /**
     * Mémorise la couleur à affecter au tableau
     * @param y Coordonnée y
     * @param color la couleur
     */
    public void putColor(int y, String color){
        colors[y] = color;
    }

    /**
     * Ajoute la couleur au menu
     * @param s la string
     * @param y  Coordonnée y
     */
    public void setColorAt(String s,int y){
        menuAffichage[y] = "║"+s+menuAffichage[y].substring(1,LARGEUR_MENU+1)+Affichage.BLUE+"║\n";
    }

    /**
     * modifies un character à la string s au i
     * @param s la string
     * @param i l'index du char
     * @param c le char
     * @return la nouvelle string
     */
    public String setCharAt( String s , int i , char c){
        return s.substring(0,i)+c+s.substring(i+1);
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
        if (colors[line]!=null)setColorAt(colors[line],line);
        sb.append(menuAffichage[line]);
    }


}
