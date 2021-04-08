package Model.Map.Room_Strategy.Formes;

import Model.Map.Cell;
import Model.Map.Room;
import Model.Map.Room_Strategy.RoomStrategy;
import Model.Utils.Affichage;

public abstract class CircleRoomStrategy extends RoomStrategy {

    protected void drawCircle(Room room , int r ){
        int xc = (room.getWidth()-1)/2;
        int yc = (room.getHeigth()-1)/2;
        int x,y,p;
        x=0;
        y=r;
        p=3-(2*r);
        Cell.Style style = new Cell.Style(Cell.Style.CellType.NORMAL,Affichage.BLUE);
        room.set(x,y, new Cell(true, style));

        for (x = 0; x <= y ; x++) {
            if(p<0)p=(p+(4*x)+6);
            else {
                y-=1;
                p+=((4*(x-y)+10));
            }
            room.set(xc+x,yc-y,new Cell(true, style));
            room.set(xc-x,yc-y,new Cell(true, style));
            room.set(xc+x,yc+y,new Cell(true, style));
            room.set(xc-x,yc+y,new Cell(true, style));
            room.set(xc+y,yc-x,new Cell(true, style));
            room.set(xc-y,yc-x,new Cell(true, style));
            room.set(xc+y,yc+x,new Cell(true, style));
            room.set(xc-y,yc+x,new Cell(true, style));
        }
    }
    protected void BresenhamCircle(Room room){
        int r = (room.getWidth()/2);
       drawCircle(room,r);
    }

    protected void fillInteriorCircle(Room r){
        for (int y = 1; y < r.getHeigth()-1; y++) {
            boolean fstBorder = false;
            boolean sndBorder = false;
            for (int x = 0; x < r.getWidth() ; x++) {
                if(sndBorder)break;
                if(fstBorder){
                    if(r.get(x,y).getType() == Cell.Style.CellType.NORMAL)sndBorder=true;
                    else r.set(x,y,new Cell(true, new Cell.Style(Cell.Style.CellType.NORMAL, Affichage.BLUE)));
                }
                if(!fstBorder && r.get(x,y).getType() == Cell.Style.CellType.NORMAL &&  r.get(x+1,y).getType() == Cell.Style.CellType.VOID )fstBorder = true;
            }

        }
    }

    @Override
    public int getNbrMaxMobPerRoom() {
        return 1;
    }
}
