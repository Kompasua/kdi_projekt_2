// Ghost.java
// Used for PaKman

import ch.aplu.jgamegrid.*;
import java.awt.Color;
import java.util.*;

public class Silly extends Ghost
{
    private PaKman game;

    public Silly(PaKman game) {
        super(game, "sprites/silly.gif");
        this.game = game;
        reset();
    }
    // TODO add functionality according the task
}