package Model.Map.Room_Strategy;

import Model.Map.Cell;
import Model.Map.Etage;
import Model.Map.Room;
import Model.Utils.Position;

import java.util.ArrayList;

public abstract class BossRoomStrategy extends RoomStrategy {


    protected void BresenhamCircle(Room room){
        int r = (room.getWidth()/2);
        int xc = (room.getWidth()-1)/2;
        int yc = (room.getHeigth()-1)/2;
        int x,y,p;
        x=0;
        y=r;
        p=3-(2*r);

        room.set(x,y, new Cell(false, Cell.CellType.BORDER));

        for (x = 0; x <= y ; x++) {
            if(p<0)p=(p+(4*x)+6);
            else {
                y-=1;
                p+=((4*(x-y)+10));
            }
            room.set(xc+x,yc-y,new Cell(false, Cell.CellType.BORDER));
            room.set(xc-x,yc-y,new Cell(false, Cell.CellType.BORDER));
            room.set(xc+x,yc+y,new Cell(false, Cell.CellType.BORDER));
            room.set(xc-x,yc+y,new Cell(false, Cell.CellType.BORDER));
            room.set(xc+y,yc-x,new Cell(false, Cell.CellType.BORDER));
            room.set(xc-y,yc-x,new Cell(false, Cell.CellType.BORDER));
            room.set(xc+y,yc+x,new Cell(false, Cell.CellType.BORDER));
            room.set(xc-y,yc+x,new Cell(false, Cell.CellType.BORDER));
        }



    }

    protected void fillInteriorCircle(Room r){
        System.out.println(r.getWidth() + " "+ r.getHeigth());
        for (int y = 1; y < r.getHeigth()-1; y++) {
            boolean fstBorder = false;
            boolean sndBorder = false;
            for (int x = 0; x < r.getWidth() ; x++) {
                if(sndBorder)break;
                if(fstBorder){
                    if(r.get(x,y).getType() == Cell.CellType.BORDER)sndBorder=true;
                    else r.set(x,y,new Cell(true, Cell.CellType.NORMAL));
                }
                if(!fstBorder && r.get(x,y).getType() == Cell.CellType.BORDER &&  r.get(x+1,y).getType() == Cell.CellType.VOID )fstBorder = true;
            }

        }
    }

    @Override
    public int getNbrMaxMobPerRoom() {
        return 1;
    }
}
