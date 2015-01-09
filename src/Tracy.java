// Ghost.java
// Used for PaKman

import ch.aplu.jgamegrid.*;
import java.awt.Color;
import java.util.*;

public class Tracy extends Ghost
{
    private PaKman game;

    public Tracy(PaKman game) {
        super(game, "sprites/tracy.gif");
        this.game = game;
        reset();
    }
    // TODO add functionality according the task
}