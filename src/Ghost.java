/**
 * Ghost.java
 * Used for PaKman
 * @author Anton Bubnov and Tony Stankov
 */

import ch.aplu.jgamegrid.*;
import java.util.*;

public abstract class Ghost extends Actor {
    // Store all ghosts
    public static ArrayList<Ghost> list = new ArrayList<Ghost>();
    private PaKman game;
    private int randomSteps; // How many next moves are random
    protected boolean hunting; // Hunting (T) or fleeing (F)

    public Ghost(PaKman game, String filename) {
        super(false, filename, 2);
        this.game = game;
        this.setSlowDown(2);
        list.add(this); // Add every new ghost in global Array.
        reset();
    }

    /**
     * @return if hunting mode is on or off.
     */
    public boolean getMode() {
        return hunting;
    }

    /**
     * Called when the level is initialized or reset.
     */
    public void reset() {
        this.randomSteps = 0;
        this.hunting = true;
    }

    /**
     * Called once in every iteration of the game loop to calculate the actions
     * of this ghost.
     */
    public void act() {
        if (randomSteps <= 0 && !(hunting ? moveHunt() : moveFlee()))
            randomSteps = 10;

        if (randomSteps > 0) {
            randomSteps--;
            moveRandom();
        }

        // If moving on west, mirror sprite so it looks in the proper direction
        if (getDirection() > 150 && getDirection() < 210)
            setHorzMirror(false);
        else
            setHorzMirror(true);
    }

    public void act(int steps) {
        if (randomSteps <= 0 && !(hunting ? moveHunt() : moveFlee()))
            randomSteps = steps;

        if (randomSteps > 0) {
            randomSteps--;
            moveRandom();
        }

        // If moving on west, mirror sprite so it looks in the proper direction
        if (getDirection() > 150 && getDirection() < 210)
            setHorzMirror(false);
        else
            setHorzMirror(true);
    }

    /**
     * Toggle hunting/fleeing mode.
     */
    public void toggleHunting() {
        hunting = !hunting;
        randomSteps = 0;

        // Change the sprite
        show(hunting ? 0 : 1);
    }

    /**
     * Try to move one step towards pakman.
     * 
     * @return true iff moved.
     */
    protected boolean moveHunt() {
        Location pac = game.wherePakman();
        Location me = getLocation();
        int dx = pac.x - me.x;
        int dy = pac.y - me.y;
        Location.CompassDirection dir = null;

        if (dx > 0 && canMove(Location.EAST))
            dir = Location.EAST;
        else if (dy > 0 && canMove(Location.SOUTH))
            dir = Location.SOUTH;
        else if (dx < 0 && canMove(Location.WEST))
            dir = Location.WEST;
        else if (dy < 0 && canMove(Location.NORTH))
            dir = Location.NORTH;

        if (dir == null)
            return false; // Also when at pakman's position!

        setDirection(dir);
        move();
        return true;
    }

    /**
     * Try to move one step away from pakman.
     * 
     * @return true iff moved.
     */
    protected boolean moveFlee() {
        Location pac = game.wherePakman();
        Location me = getLocation();
        int dx = pac.x - me.x;
        int dy = pac.y - me.y;
        Location.CompassDirection dir = null;

        if (dx > 0 && canMove(Location.WEST))
            dir = Location.WEST;
        else if (dy > 0 && canMove(Location.NORTH))
            dir = Location.NORTH;
        else if (dx < 0 && canMove(Location.EAST))
            dir = Location.EAST;
        else if (dy < 0 && canMove(Location.SOUTH))
            dir = Location.SOUTH;

        if (dir == null)
            return false; // Also when at pakman's position!

        setDirection(dir);
        move();
        return true;
    }

    /**
     * Move one step randomly.
     * 
     * @return true always.
     */
    protected boolean moveRandom() {
        final int right = 90, left = -90;
        boolean turnLeft = canMove(getDirection() + left);
        boolean turnRight = canMove(getDirection() + right);
        boolean straight = canMove(getDirection());

        if (turnLeft || turnRight) {
            if (!straight || Math.random() < 0.5) { // Make a turn
                if (!turnLeft)
                    setDirection(getDirection() + right);
                else if (!turnRight)
                    setDirection(getDirection() + left);
                else
                    setDirection(getDirection()
                            + (Math.random() < 0.5 ? right : left));
            }
        } else if (!straight)
            setDirection(getDirection() + 180);

        move();
        return true;

    }

    /**
     * Return true if this ghost can move in the given direction.
     */
    private boolean canMove(Location.CompassDirection dir) {
        return canMove(getLocation().getNeighbourLocation(dir));
    }

    /**
     * Return true if this ghost can move in the given direction.
     */
    private boolean canMove(double dir) {
        return canMove(getLocation().getNeighbourLocation(dir));
    }

    /**
     * Return true iff the given location is a valid location for this ghost
     */
    private boolean canMove(Location location) {
        if (gameGrid.isInGrid(location)
                && (game.getLevel().getTile(location) == Tile.PASSAGE || game
                        .getLevel().getTile(location) == Tile.PILL))
            return true;
        else
            return false;
    }
}
