/**
 * Silly.java 
 * Ghost children
 * 
 * @author Anton Bubnov and Tony Stankov
 * 
 * Ghosts of type Silly move as described in Project 1. Silly is blue
 * when hunting and grey when fleeing.
 */

public class Silly extends Ghost {

    public Silly(PaKman game) {
        super(game, "sprites/silly.gif");
        reset();
    }
}