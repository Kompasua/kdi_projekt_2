/**
 * Randy.java 
 * Ghost children
 * 
 * @author Anton Bubnov and Tony Stankov
 * 
 * Ghosts of type Randy move only randomly in the fashion as described
 * in Project 1. Randy is red when hunting and grey when fleeing.
 */

public class Randy extends Ghost {

    public Randy(PaKman game) {
        super(game, "sprites/randy.gif");
        reset();
    }

    @Override
    public void act() {
        moveRandom();
        // If moving on west, mirror sprite so it looks in the proper direction
        if (getDirection() > 150 && getDirection() < 210)
            setHorzMirror(false);
        else
            setHorzMirror(true);
    }
}
// EOF