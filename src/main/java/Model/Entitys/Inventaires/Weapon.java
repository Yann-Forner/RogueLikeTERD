package Model.Entitys.Inventaires;

public class Weapon implements Objets{
    private int damages;
    private String nom;

    public Weapon(int damages,String nom){
        this.damages=damages;
        this.nom=nom;
    }

    public int getDegats() {
        return damages;
    }
}
