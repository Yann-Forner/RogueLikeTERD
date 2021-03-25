package Model.Entitys.Inventaires;

public class Armures implements Objets{
    private int protection;
    private String nom;

    public Armures(int protection,String nom){
        this.protection=protection;
        this.nom=nom;
    }

    public int getProtection() {
        return protection;
    }
}
