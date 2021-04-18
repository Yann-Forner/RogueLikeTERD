package Model.Entitys.Inventaires;

public class Armures {
    private int protection;
    private String nom;

    public Armures(int protection,String nom){
        this.protection=protection;
        this.nom=nom;
    }

    public String getNom() {
        return nom;
    }

    public int getProtection() {
        return protection;
    }
}
