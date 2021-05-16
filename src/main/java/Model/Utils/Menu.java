package Model.Utils;

import Model.Entitys.Items.AbstractItem;
import Model.Entitys.Items.Inventory;
import Model.Entitys.Items.Potions.AbstractPotion;
import Model.Entitys.Items.Weapons.AbstractWeapon;
import Model.Entitys.Player.Player;
import Model.Map.Cell;
import Model.Map.Etage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;


/**
 * Classe servant a afficher le menu sur la droite du terminal
 * @author Yann
 */
public class Menu {
    private final Etage etage;
    private static final String TAB = "   ";
    private static final String couleur = Affichage.RESET + Affichage.BLUE;
    public String[] Lignes;
    private static final int LARGEUR_MENU = 83;

    /**
     * Enum pour fractionner le menu en 3 parties.
     * @author Quentin
     */
    public enum Side{
        GAUCHE,MILIEU,DROITE
    }

    /**
     * Constructeur d'un menu
     * @param etage etage
     * @author Yann, Quentin
     */
    public Menu(Etage etage) {
        this.etage = etage;
        refresh();
    }

    /**
     * Fonction servant à injecter les informations dans le menu.
     * @author Yann, Quentin
     */
    private void refresh(){
        clear();
        Player player = Start.getPlayer();
        if(player != null){
            Inventory inv = player.getInventory();

            //SEED & TIMER
            setStringOnSide(2,Affichage.PURPLE+"SEED : "+Procedure.getSeed()+couleur,Side.GAUCHE,true);
            setStringOnSide(1, new String[]{"║","║","║"},Side.MILIEU,true);
            setStringOnSide(2,Affichage.PURPLE+"TIMER : "+TourManager.getTimer()+couleur,Side.DROITE,true);

            //JOUEUR
            setFullLine(4);
            setStringOnSide(5,Affichage.GREEN+"Joueur  :  " + Affichage.BRIGTH_GREEN + player.getNom() + couleur+" ",Side.GAUCHE,false);
            String lvl = " Niveau " + player.getLvl() + " [" + player.getCURRENT_EXP() + "/" + player.getMAX_EXP() + "] ";
            setStringOnSide(6,Affichage.GREEN+"Classe  :  " + Affichage.BRIGTH_GREEN + player.getClasse().getNom(),Side.GAUCHE,false);
            setStringOnSide(7,Affichage.YELLOW+"Argent  :  " + Affichage.BRIGTH_YELLOW + player.getInventory().getMoney()+" $",Side.GAUCHE,false);
            setStringOnSide(8,Affichage.RED+"Force   :  "+ Affichage.BRIGTH_RED + player.getForce(),Side.GAUCHE,false);
            setStringOnSide(5, new String[]{
                    Affichage.RED+"╔"+"═".repeat(lvl.length())+"╗   ",
                    Affichage.RED+"║"+lvl+"║   ",
                    Affichage.RED+"╚"+"═".repeat(lvl.length())+"╝   "
            },Side.DROITE,true);
            setStringOnSide(9,Affichage.RED+"Portée  :  "+ Affichage.BRIGTH_RED + (inv.getWeapons().size()==0 ? 1 :  inv.getWeapons().get(0).getRange()),Side.GAUCHE,false);
            setStringOnSide(8,Affichage.RED+"Etage n°"+(Objects.requireNonNull(Start.getMap()).getIndexCurrent()+1)+"",Side.DROITE,true);

            //INVENTAIRE
            setFullLine(10);
            setStringOnSide(12, Affichage.GREEN+"Inventaire",Side.MILIEU,true);

            //ARMES
            ArrayList<AbstractWeapon> armesList = inv.getWeapons();
            setStringOnSide(14, Affichage.GREEN+"Vous avez " + armesList.size() + " armes :",Side.GAUCHE,false);
            setArrayOnTheWholeLine(15,armesList, armesList.size() != 0 && player.getClasse().canUse(armesList.get(0)),inv.isWeaponsFull());

            //POTIONS
            ArrayList<AbstractPotion> potionsList = inv.getPotions();
            setStringOnSide(16, Affichage.GREEN+"Vous avez " + potionsList.size() + " potions :",Side.GAUCHE,false);
            setArrayOnTheWholeLine(17,potionsList,true, inv.isPotionsFull());

            //MONSTRES
            setFullLine(18);
            setStringOnSide(20,Affichage.YELLOW+"Monstres",Side.MILIEU,true);
            setListeObjects(new ArrayList<>(etage.getMonsters()),22);

            //ITEMS
            setFullLine(25);
            setStringOnSide(27,Affichage.BRIGTH_CYAN+"Items",Side.MILIEU,true);
            ArrayList<Object> abstractItems = new ArrayList<>(etage.getItems());
            if(player.getPoche()!=null){
                abstractItems.add(player.getPoche());
            }
            setListeObjects(abstractItems,29);

            //CELLULES
            setFullLine(32);
            setStringOnSide(34,Affichage.BRIGTH_GREEN+"Cellules",Side.MILIEU,true);
            ArrayList<String> types = new ArrayList<>();
            Arrays.stream(Cell.Style.CellType.values()).forEach((v) -> types.add( " "+ new Cell(true,new Cell.Style(v)) + couleur + " = " + v + "  "));
            setArrayOnMultipleLine(36, types);

            setEndOfLine();
        }
    }

    /**
     * Affiche le nombre d'objets selon le type dans le menu.
     * @param list L'arraylist contenant les objets.
     * @param y La ligne de l'affichage
     * @author Quentin
     */
    private void setListeObjects(ArrayList<Object> list, int y){
        HashMap<Class<?>, Pair<String,Integer>> objects = new HashMap<>(){};
        for (Object obj : list){
            Pair<String, Integer> pair = objects.get(obj.getClass());
            if(pair == null){
                pair = new Pair<>(obj.toString(), 0);
            }
            pair.setRight(pair.getRight() + 1);
            objects.put(obj.getClass(),pair);
        }
        ArrayList<String> items = new ArrayList<>();
        objects.forEach((k, v) -> items.add( " "+v.getLeft() + couleur +" = " + k.getSimpleName() + " x"+v.getRight()+"  "));
        setArrayOnMultipleLine(y,items);
    }

    /**
     * Réinitialise le menu.
     * @author Yann, Quentin
     */
    private void clear(){
        Lignes = new String[etage.getHeigth()];
        Arrays.fill(Lignes, couleur + TAB);
        setFullLine(0);
        for (int i = 1; i < etage.getHeigth()-1 ; i++){
            addToLine(i,"║");
        }
        setFullLine(etage.getHeigth()-1);
    }

    /**
     * Rajoute un string a la fin de la ligne.
     * @param y Hauteur d ela ligne
     * @param s String a rajouter
     * @author Quentin
     */
    private void addToLine(int y, String s){
        Lignes[y] = Lignes[y] + s;
    }

    /**
     * Crée une ligne sur tout le menu a la hauteur y.
     * @param y Hauteur de la ligne
     * @author Quentin
     */
    private void setFullLine(int y){
        String line;
        if(y==0){
            line = "╔═════════════════════════════════════════════════════════════════════════════════╗";
        }
        else if (y==Lignes.length-1){
            line = "╚═════════════════════════════════════════════════════════════════════════════════╝";
        }
        else{
            Lignes[y] = couleur + TAB + "╠" + "═".repeat(LARGEUR_MENU-2);
            return;
        }
        addToLine(y,line);
    }

    /**
     * Affiche l'ArrayList sur plusieurs ligne lorsqu'elle depasse de celle courante.
     * @param y hauteur de depart de l'affichage
     * @param arrayList ArrayList des Strings a afficher
     * @author Quentin
     */
    private void setArrayOnMultipleLine(int y, ArrayList<String> arrayList){
        StringBuilder sb = new StringBuilder();
        for(String s : arrayList){
            if(Affichage.getTrueLength(sb.toString()) + Affichage.getTrueLength(s) < LARGEUR_MENU-1){
                sb.append(s);
            }
            else{
                addToLine(y,sb.toString());
                sb = new StringBuilder();
                sb.append(s);
                y++;
            }
        }
        addToLine(y,sb.toString());
    }

    /**
     * Ajoute un string a un coté du menu.
     * @param y Hauteur de la ligne
     * @param s String a ajouter
     * @param side Coté ou le string doit etre ajouté
     * @param centered Si le string doit etre centré sur son coté
     * @author Quentin
     */
    private void setStringOnSide(int y, String s, Side side, boolean centered){
        StringBuilder sb = new StringBuilder();
        int trueLengthL = Affichage.getTrueLength(Lignes[y]);
        int trueLengthS = Affichage.getTrueLength(s);
        int width = (centered ? trueLengthS/2 : trueLengthS);
        sb.append(switch (side){
            case GAUCHE -> Affichage.addSpace(TAB,0,centered ? 41/2-width : 0);
            case MILIEU -> Affichage.addSpace("", trueLengthL,43-width + width%2);
            case DROITE -> Affichage.addSpace("", trueLengthL,(LARGEUR_MENU-41/2)-width-1);
        });
        sb.append(s);
        addToLine(y,sb.toString());
    }

    /**
     * Ajoute un tableau de string a un coté du menu sur plusieurs ligne.
     * @param y Hauteur de la ligne
     * @param s String a ajouter
     * @param side Coté ou le string doit etre ajouté
     * @param centered Si le string doit etre centré sur son coté
     * @author Quentin
     */
    private void setStringOnSide(int y, String[] s, Side side, boolean centered){
        for(String string : s){
            setStringOnSide(y,string,side,centered);
            y++;
        }
    }

    /**
     * Affiche l'Arrayliste sur toute la ligne et la coupe si elle est trop grande.
     * @param y Hauteur de la ligne
     * @param arrayList ArrayList d'Items a afficher.
     * @param canUse Defini si le joueur peut utiliser cet item
     * @author Quentin
     */
    private void setArrayOnTheWholeLine(int y, ArrayList<? extends AbstractItem> arrayList, boolean canUse, boolean isFull){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arrayList.size(); i++) {
            Object obj = arrayList.get(i);
            if(Affichage.getTrueLength(sb.toString())+Affichage.getTrueLength(obj.toString())<LARGEUR_MENU-10){
                if(i==0){
                    String color = canUse ? Affichage.GREEN : Affichage.RED;
                    sb.append(color);
                    sb.append(TAB);
                    sb.append("[ ").append(obj).append(Affichage.RESET).append(color).append(" ]");
                }
                else{
                    sb.append(obj).append(Affichage.RESET);
                }
                sb.append(" ");
            }
            else{
                sb.append(Affichage.RED).append("...");
                break;
            }
        }
        if(isFull){
            sb.append(Affichage.RED).append(Affichage.BOLD).append("   MAX");
        }
        addToLine(y,sb.toString());
    }

    /**
     * Rejoute la ligne y du tableau au StringBuilder.
     * @param y La hauteur de la ligne.
     * @param sb Le StringBuilder ou ajouter la ligne
     * @author Quentin
     */
    public void appendLigne(int y, StringBuilder sb){
        sb.append(Lignes[y]);
    }

    /**
     * Ajoute la fin du tableau a la ligne.
     * @author Quentin
     */
    private void setEndOfLine(){
        for (int i = 0; i < Lignes.length-1; i++) {
            if(i>0 && i < Lignes.length-1){
                String end = couleur + (Lignes[i].charAt(Lignes[i].length()-1) == '═' ? "╣" : "║");
                Lignes[i] = Lignes[i] + Affichage.addSpace("",Affichage.getTrueLength(Lignes[i])-2,LARGEUR_MENU) + end;
            }
            Lignes[i] = Lignes[i] + "\n";
        }
    }

}
