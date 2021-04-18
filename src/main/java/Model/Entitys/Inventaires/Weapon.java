package Model.Entitys.Inventaires;

public class Weapon {
    private final int damages;
    private final String nom;
    private int range;

    public Weapon(int damages,String nom,int range){
        this.damages=damages;
        this.nom=nom;
        this.range=range;
    }

    public String getNom() {
        return nom;
    }

    public int getDegats() {
        return damages;
    }
}
