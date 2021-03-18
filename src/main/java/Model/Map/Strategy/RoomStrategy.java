package Model.Map.Strategy;

import Model.Cell;

import java.util.ArrayList;

public interface RoomStrategy {
    ArrayList<ArrayList<Cell>> composeRoom (ArrayList<ArrayList<Cell>> oldRoom);

}
