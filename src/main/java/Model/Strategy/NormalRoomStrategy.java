package Model.Strategy;

import Model.Cell;

import java.util.ArrayList;

public class NormalRoomStrategy implements RoomStrategy{
    @Override
    public ArrayList<ArrayList<Cell>> composeRoom(ArrayList<ArrayList<Cell>> oldRoom) {
        return oldRoom;
    }
}
