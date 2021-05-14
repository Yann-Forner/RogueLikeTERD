package Model.Entitys.Items.Foods;

import Model.Map.Etage;
import Model.Utils.Procedure;

/**
 * Factory permettant de générer les consommables de nourritures.
 * @author JP
 */
public class FoodFactory {
    public enum FoodType{
        APPLE(20,"Pomme","🍎","p"),
        BANANA(30,"Banane","🍌","b"),
        CARROT(50,"Carrote","🥕","c"),
        ORANGE(70,"Orange","🍊","o"),
        PEACH(100,"Peche","🍑","g"),
        BURGER(10,"Burger","🍔","b");

        private final int heal;
        private final String nom;
        private final String forme_linux;
        private final String forme_windows;

        FoodType(int heal,String nom,String forme_linux,String forme_windows){
            this.heal = heal;
            this.nom = nom;
            this.forme_linux = forme_linux;
            this.forme_windows = forme_windows;
        }

        public int getHeal() {
            return heal;
        }

        public String getNom() {
            return nom;
        }

        public String getForme_linux() {
            return forme_linux;
        }

        public String getForme_windows() {
            return forme_windows;
        }
    }

    /**
     * Méthode factory pour les nourritures
     * @param etage Etage ou sera généré la nourriture
     * @param ft Type de la nourriture
     * @return Retourne la nourriture générée
     */
    public static AbstractFood getNewFood(Etage etage, FoodType ft) {
        if(ft.equals(FoodType.BURGER)){
            return new Burger(etage, Procedure.getAccesibleRandomPosition(true, etage),ft);
        }
        return new Fruit(etage, Procedure.getAccesibleRandomPosition(true, etage), ft);
    }
}
