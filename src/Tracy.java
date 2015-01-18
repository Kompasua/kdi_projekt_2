/**
 * Tracy.java 
 * Ghost children
 * 
 * @author Anton Bubnov and Tony Stankov
 * 
 * Ghosts of type Tracy move as described in Project 1, with the following
 * modifications:
 * i. When a move/flee is not possible, only a single random step is made.
 * ii. When the ghost is at less than 5 cells distance in both horizontal
 * and vertical direction from its starting position, it will only move randomly.
 * Tracy is green when hunting and grey when fleeing.
 */

import ch.aplu.jgamegrid.Location;

public class Tracy extends Ghost {

    public Tracy(PaKman game) {
        super(game, "sprites/tracy.gif");
        reset();
    }

    @Override
    public void act() {
        Location start = getLocationStart();
        if (Math.abs(start.getX() - getLocation().x) < 5
                && Math.abs(start.getY() - getLocation().y) < 5) {
            moveRandom();
        } else {
            act(1);
        }
        // If moving on west, mirror sprite so it looks in the proper direction
        if (getDirection() > 150 && getDirection() < 210)
            setHorzMirror(false);
        else
            setHorzMirror(true);
    }
}
// EOF