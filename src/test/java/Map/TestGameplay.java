package Map;

import Model.Entitys.Items.AbstractItem;
import Model.Entitys.Items.Potions.HealPotion;
import Model.Entitys.Items.Potions.PotionFactory;
import Model.Entitys.Player.Classes.ClassFactory;
import Model.Entitys.Player.Player;
import Model.Map.Etage;
import Model.Map.Map;
import Model.Utils.*;
import junit.framework.TestCase;

public class TestGameplay extends TestCase {

    protected Map map;
    protected Player player;
    protected Etage etage;

    public TestGameplay() {
        player = ClassFactory.getNewPlayer("Testeur", ClassFactory.Class.ARCHER);
        TourManager tm = new TourManager(player);
        tm.setMap();
        Start.setTourManager(tm);
        map = tm.getMap();
        etage = map.getCurrent();

    }

    public void Testgameplay(){

    }

    public void testPoche(){
        System.out.println("----------------ETAGE DE BASE----------------");
        System.out.println(etage);
        AbstractItem item = PotionFactory.getNewPotion(etage, PotionFactory.PotionType.HEAL_POTION);
        Pair<Position, Player.Direction> rightPosition = getAccessiblePosition(item.getPosition()) ;
        player.move(rightPosition.getLeft());
        assertSame(player.getPosition(),rightPosition.getLeft());
        Position playerPos = player.getPosition();
        etage.addItem(item);
        while (!player.getInventory().isPotionsFull()){
            player.getInventory().addPotion(PotionFactory.getNewPotion(etage, PotionFactory.PotionType.HEAL_POTION));
        }
        System.out.println("----------------AVEC ITEM----------------");
        System.out.println(etage);
        player.moveDirection(rightPosition.getRight());
        System.out.println("----------------DEPLACEMENT SUR ITEM----------------");
        System.out.println(etage);
        assertSame(player,etage.get(item.getPosition()).getEntity());;

        System.out.println(etage);
        System.out.println(player.getPoche());
        assertSame(player.getPoche(), item); FAIL

        assertNotSame(player.getPosition(),playerPos);
        try {
            Thread.sleep(player.getClasse().getSpeed());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(player.getPoche());
        player.moveDirection(getInvertedDirection(rightPosition.getRight()));
        System.out.println("----------------DEPLACEMENT HORS ITEM----------------");
        System.out.println(player.getPoche());
        System.out.println(etage);
    }

    public void testPocheWithCollideToAWall(){

        Position pos = Procedure.getAccesibleRandomPosition(false,etage,etage.getRooms().get(0));

        while(etage.get(pos).isAccesible())pos = new Position(pos.getX()+1, pos.getY());
        pos=new Position(pos.getX()-1,pos.getY());
        AbstractItem healPotion  = PotionFactory.getNewPotion(etage, PotionFactory.PotionType.HEAL_POTION);
        etage.addItem(healPotion);
        System.out.println(etage);
        /* Déplacement du joueur à coté de l'item */
        assertSame(etage.get(pos).getEntity(),healPotion);
        player.move(new Position(pos.getX()-1,pos.getY()));
        assertSame(player,etage.get(new Position(pos.getX()-1,pos.getY())).getEntity());
        System.out.println(etage);
        /* Déplacement du joueur sur l'item */
        player.moveDirection(Player.Direction.DROITE);
        assertSame(player.getPoche(),healPotion);//FAIL
        assertSame(player,etage.get(pos).getEntity());
        System.out.println(etage);
        /* Essai de déplacement sur le mur */
        player.moveDirection(Player.Direction.DROITE);
        assertSame(player.getPoche(),healPotion);
        assertSame(player,etage.get(pos).getEntity());
        /* Joueur revenant à la position initale */
        player.moveDirection(Player.Direction.GAUCHE);
        assertSame(etage.get(pos).getEntity(),healPotion);
        assertNotSame(player.getPoche(),healPotion);
    }


    public Pair<Position, Player.Direction> getAccessiblePosition(Position pos){
        if(etage.get(pos.getX()-1,pos.getY() ).isAccesible() )return new Pair<>(new Position(pos.getX()-1,pos.getY()), Player.Direction.DROITE);
        else if(etage.get(pos.getX()+1,pos.getY() ).isAccesible() )return new Pair<>(new Position(pos.getX()+1,pos.getY()), Player.Direction.GAUCHE);
        else if(etage.get(pos.getX(),pos.getY()-1 ).isAccesible() )return new Pair<>(new Position(pos.getX(),pos.getY()-1), Player.Direction.BAS);
        else return new Pair<>(new Position(pos.getX(),pos.getY()+1), Player.Direction.HAUT);
    }

    public Player.Direction getInvertedDirection(Player.Direction d) {
        if(d == Player.Direction.BAS)return Player.Direction.HAUT;
        else if (d == Player.Direction.HAUT)return Player.Direction.BAS;
        else if (d== Player.Direction.GAUCHE)return Player.Direction.DROITE;
        else return Player.Direction.GAUCHE;
    }

}

