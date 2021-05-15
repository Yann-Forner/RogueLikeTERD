package Model.Entitys.Items.Misc;

import Model.Entitys.Entity;
import Model.Entitys.Items.AbstractItem;
import Model.Entitys.Items.Potions.PotionFactory;
import Model.Entitys.Items.Weapons.WeaponFactory;
import Model.Entitys.Player.Player;
import Model.Map.Etage;
import Model.Utils.*;

import java.util.ArrayList;

public class Chest extends AbstractItem {

    public Chest(Etage etage, Position position) {
        super(etage, position, "Coffre");
    }

    @Override
    public void useItem(Player player) {
        ArrayList<AbstractItem> items = new ArrayList<>();
        int nbrObj = switch (Procedure.getRandomInt(9,0)){
            case 0,1,2,3,4,5,6 -> 1;
            case 7,8 -> 2;
            default -> 3;
        };
        for (int i = 0; i < nbrObj; i++) {
            items.add(switch (Procedure.getRandomInt(20,0)){
                case 0 -> WeaponFactory.getNewWeapon(getEtage(), WeaponFactory.WeaponType.SWORD);
                case 1 -> WeaponFactory.getNewWeapon(getEtage(), WeaponFactory.WeaponType.WAND);
                case 2 -> WeaponFactory.getNewWeapon(getEtage(), WeaponFactory.WeaponType.BOW);
                case 3,4,5,6 -> PotionFactory.getNewPotion(getEtage(), PotionFactory.PotionType.HEAL_POTION);
                case 7,8,9,10 -> PotionFactory.getNewPotion(getEtage(), PotionFactory.PotionType.ENDURENCE_POTION);
                case 11,12 -> PotionFactory.getNewPotion(getEtage(), PotionFactory.PotionType.INVUL_POTION);
                case 13,14 -> PotionFactory.getNewPotion(getEtage(), PotionFactory.PotionType.STRENGTH_POTION);
                default -> WeaponFactory.getNewWeapon(getEtage(), WeaponFactory.WeaponType.BOW); //TODO REMPLACER PAR ARGENT
            });
        }
        for (AbstractItem i : items){
            i.onContact(player);
        }
        getEtage().removeItem(this);
    }

    @Override
    public void onContact(Entity e) {
        if(e instanceof Player){
            Player player = (Player) e;
            TourManager.addMessage(Affichage.BRIGTH_YELLOW+Affichage.BOLD+player.getNom()+" a ouvert un coffre !!!"+Affichage.RESET);
            useItem(player);
        }
    }

    @Override
    public String toString() {
        if(System.getProperty("os.name").equals("Linux")){
            return 	"📦";
        }
        else{
            return super.toString()+"€";
        }
    }
}
