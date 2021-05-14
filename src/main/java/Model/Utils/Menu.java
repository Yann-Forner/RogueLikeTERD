package Model.Utils;
;
import Model.Entitys.Items.AbstractItem;
import Model.Entitys.Items.Inventory;
import Model.Entitys.Items.Potions.HealPotion;
import Model.Entitys.Monsters.AbstractMonster;
import Model.Map.Cell;
import Model.Map.Etage;
import Model.Map.Map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;
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
        if(Start.getPlayer() != null){



            String[] UpperMenu = {"║","║","║"};
            Inventory inv = Start.getPlayer().getInventory();
            String lvl = " Niveau " + Start.getPlayer().getLvl() + " [" + Start.getPlayer().getCURRENT_EXP() + "/" + Start.getPlayer().getMAX_EXP() + "] ";
            String[] niveau = {
                    Affichage.RED+"╔"+"═".repeat(lvl.length())+"╗   ",
                    Affichage.RED+"║"+lvl+"║   ",
                    Affichage.RED+"╚"+"═".repeat(lvl.length())+"╝   "
            };
            printStringOnSide(0,Affichage.PURPLE+"   SEED : "+Procedure.getSeed()+Affichage.BLUE +" " ,2);
            printStringOnSide(1,Affichage.PURPLE+"  TIMER : "+TourManager.getTimer()+Affichage.BLUE+"      ",2);
            printStringOnSide(2,UpperMenu,1);
            printLine(4,Affichage.BLUE);

            printStringOnSide(0,Affichage.RED+"   Joueur  :  "+ Start.getPlayer().getNom() + Affichage.BLUE+" ",5);
            printStringOnSide(0,Affichage.RED+"   Classe  :  "+ Start.getPlayer().getClasse().getNom(),6);
            printStringOnSide(1,Affichage.RED+" Etage n°"+(map.getIndexCurrent()+1)+"        ",9);
            printStringOnSide(1,niveau,5);
            printStringOnSide(0,Affichage.RED+"   Force  :  "+Start.getPlayer().getForce(),7);


            int range = 1 ;
            if(inv.getWeapons().size()!=0)range = inv.getWeapons().get(0).getRange();

            printStringOnSide(0,Affichage.RED+"   Portée  :  "+range,8);

            printLine(10,Affichage.BLUE);

            printStringOnSide(2, Affichage.GREEN+"Inventaire",12);

            ArrayList<? extends AbstractItem> potionsList = inv.getPotions();
            ArrayList<? extends AbstractItem> armesList = Start.getPlayer().getInventory().getWeapons();
            boolean canUse = true;
            if( Start.getPlayer().getInventory().getWeapons().size()!= 0){
                canUse = Start.getPlayer().getClasse().canUse(Start.getPlayer().getInventory().getWeapons().get(0));
            }
            printStringOnSide(0, Affichage.GREEN+" Vous avez " + armesList.size() + " armes :",14);
            printArrayOnTheWholeLine(15,armesList,canUse);

            printStringOnSide(0, Affichage.GREEN+" Vous avez " + potionsList.size() + " potions :",16);
            printArrayOnTheWholeLine(17,potionsList,true);

            printLine(18,Affichage.BLUE);
            printStringOnSide(2,Affichage.YELLOW+" Monstres ",20);

            HashMap<AbstractMonster,Integer > monsterTypes = new HashMap<>();

            for ( AbstractMonster m : Start.getPlayer().getEtage().getMonsters()
                 ) {
                AtomicBoolean added = new AtomicBoolean(false);
                monsterTypes.forEach((k, v) -> {

                    if(k.getClass().getName().equals(m.getClass().getName())){
                        monsterTypes.put(k,v+1);
                        added.set(true);
                    }
                });
                if(!added.get()) monsterTypes.put(m,1);
            }
            ArrayList<String> monsters = new ArrayList<>();
            monsterTypes.forEach((k, v) -> {
                String name = k.getClass().getName().split("\\.")[3];
               monsters.add( " "+k+" = "  +name+ " x"+v+"  ");
            });
            printAndBackToTheLine(22,monsters);

            printLine(25,Affichage.BLUE);

            printStringOnSide(2,Affichage.BRIGTH_CYAN+" Items ",27);


            HashMap<AbstractItem,Integer > itemTypes = new HashMap<>();

            for ( AbstractItem p : Start.getPlayer().getEtage().getItems()
            ) {
                AtomicBoolean added = new AtomicBoolean(false);
                itemTypes.forEach((k, v) -> {
                    if(k.getClass().getName().equals(p.getClass().getName())){
                        itemTypes.put(k,v+1);
                        added.set(true);
                    }
                });
                if(!added.get()) itemTypes.put(p,1);
            }

            ArrayList<String> items = new ArrayList<>();
            itemTypes.forEach((k, v) -> {
                String name = k.getClass().getName().split("\\.")[3];
                items.add( " "+k+" = "  +name+ " x"+v+"  ");
            });
            printAndBackToTheLine(29,items);

            printLine(32,Affichage.BLUE);

            printStringOnSide(2,Affichage.BRIGTH_GREEN+" Cellules ",34);


            HashMap<Cell,Integer > CellsTypes = new HashMap<>();

            for ( ArrayList<Cell> arrayListCell : Start.getPlayer().getEtage().getCells()
            ) {
                for ( Cell c : arrayListCell
                ) {
                    AtomicBoolean added = new AtomicBoolean(false);
                    CellsTypes.forEach((k, v) -> {
                        if (c.getType() == k.getType()) {
                            CellsTypes.put(k, v + 1);
                            added.set(true);
                        }
                    });
                    if (!added.get()) CellsTypes.put(c, 1);
                }
            }

            ArrayList<String> cells = new ArrayList<>();
            CellsTypes.forEach((k, v) -> {
                String name = k.getType().name();
                cells.add( " "+k+" = "  +name+ " x"+v+"  ");
            });
            printAndBackToTheLine(36,cells);

        }
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

    public void printArrayOnTheWholeLine(int line, ArrayList<? extends AbstractItem> itemList  , boolean canUse){
        StringBuilder itemString = new StringBuilder("  ");
        for (int index = 0; index < itemList.size(); index++) {
            AbstractItem abstractItem = itemList.get(index);
            if (index == 0){
                String color = Affichage.RED;
                if(canUse)color =Affichage.GREEN;
                itemString.append(color);
                itemString.append(" [" + abstractItem.toString() +Affichage.RESET+Affichage.BOLD + color +"]  ");
            }
            else
                itemString.append(abstractItem.toString()+Affichage.RESET + "  ");
        }

        printStringOnSide(0,itemString.toString(),line);
    }

    /**
     * Trace une ligne aux coordonnés y du menu
     * @param y ligne
     * @param color couleur de la ligne
     */
    public void printLine (int  y,String color){
        modifyCase(0,y,"║"+color);
        printStringOnLine(1,y,"═══════════════════════════════════════════════════════════════════════════════════════════");

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


    public void printAndBackToTheLine(int y,ArrayList<String> myArrayString){
        int x = 1;
        for (int i = 0; i < myArrayString.size(); i++) {
            if(x+getTrueLength(myArrayString.get(i)) > menuAffichage[y].length){
                x=1;
                ++y;
                if(y >= etage.getHeigth()-1)break;
            }
            printStringOnLine(x ,y,myArrayString.get(i));
            x=x+getTrueLength(myArrayString.get(i));
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
            if( i < s.length() - 5){
                Matcher m = p.matcher(s.substring(currenti, currenti+5));
                if( m.find()){
                        int size =m.end()-m.start();
                        if( m.start() == 0){
                            //System.out.println(s.charAt(currenti)+ " " + s.charAt(currenti+1) + " "+ s.charAt(currenti+2)+ " "+s.charAt(currenti+3)+  " "+s.charAt(currenti+4) + " "+y);
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

    /**
     * Renvoit la vrai longueur d'un string sans les characteres ANSI.
     * @param s String dont on cherche la longeure
     * @return Vrai longueure du string
     * @author Quentin
     */
    public static int getTrueLength(String s){
        return s.replaceAll("\u001B\\[[0-9]*m", "").length();
    }
}
