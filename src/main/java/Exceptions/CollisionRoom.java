package Exceptions;

import Model.Room;

public class CollisionRoom extends RuntimeException{
    Room r;

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public CollisionRoom(Room r) {
        super("La room ajoutée entre en collision avec une autre room");
        this.r = r;
    }

    public Room getR() {
        return r;
    }
}
