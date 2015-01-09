// Ghost.java
// Used for PaKman

import ch.aplu.jgamegrid.*;
import java.awt.Color;
import java.util.*;

public class Randy extends Ghost
{
    private PaKman game;

    public Randy(PaKman game) {
        super(game, "sprites/randy.gif");
        this.game = game;
        reset();
    }
    // TODO add functionality according the task
}