import java.awt.Color;
import ch.aplu.jgamegrid.*;

/**
 * A level in the PaKman game.
 * In every level the user has to gather the available pills without being
 * eaten by a ghost. Every level specifies the layout of the maze (a
 * rectangular grid of tiles),
 * the number and position of the pills to gather, possible special pills,
 * the initial position of the player and the number and initial
 * position(s)of the ghosts.
 * The present implementation is just a single fixed maze with player and
 * ghost position.
 */
public class Level
{
    private final int nbHorzCells = 30;
    private final int nbVertCells = 33;
    private PaKman game;
    private int pills;  // Current number of pills in the level
    private Tile[][] maze = new Tile[nbVertCells][nbHorzCells];

    /**
     * Create a new level. Currently a default fixed maze is created.
     * @param game the GameGrid to draw into.
     */
    public Level(PaKman game)
    {
        this.game = game;
        setupDefault();
        
        // Initialize the number of pills
        for (int y = 0; y < nbVertCells; y++)
            for (int x = 0; x < nbHorzCells; x++)
                if (maze[y][x] == Tile.PILL)
                    pills++;
    }

    
    /**
     * Draw the maze of this level into the given gamegrid. To be called
     * once, when the level is started.
     */
    public void drawLevel() {
        for (int y = 0; y < nbVertCells; y++)
            for (int x = 0; x < nbHorzCells; x++)
                drawTile(new Location(x, y));
    }
    
    
    /**
     * Draw a single tile.
     * @param location what to draw.
     */
    private void drawTile(Location location) {
        GGBackground bg = game.getBg();

        switch (getTile(location)) {
            case WALL:
                bg.fillCell(location, Color.gray);
                break;
            case PASSAGE:
                bg.fillCell(location, Color.lightGray);
                break;
            case PILL:
                bg.fillCell(location, Color.lightGray);
                bg.setPaintColor(Color.white);
                bg.fillCircle(game.toPoint(location), 3);
                break;
            default:
                throw new RuntimeException("Unknown tile \'"+ getTile(location) +"\'");
        }
    }
    
    
    /**
     * Return the startposition of the pakman.
     * @return the starting location of pakman.
     */
    public Location getPakmanStart() {
        return new Location(14, 24);
    }
    
    
    /**
     * Return the startposition of the ghost.
     * @return the starting position of the ghost.
     */
    public Location getGhostStart() {
        return new Location(13, 15);
    }
    
    
    /**
     * Return the tile at the given location.
     * @param location which tile to return.
     * @throws ArrayIndexOutOfBoundsException if location is outside the maze.
     */
    public Tile getTile(Location location) {
        return maze[location.y][location.x];
    }
    
    
    /**
     * Replace the tile at the given location.
     * @param location where to replace the tile.
     * @param tile the new tile to put there
     * @returns the replaced tile.
     * @throws ArrayIndexOutOfBoundsException if location is outside the maze.
     */
    public Tile setTile(Location location, Tile tile) {
        Tile old = getTile(location);
        maze[location.y][location.x] = tile;
        drawTile(location);
        return old;
    }
    
    
    /**
     * "Eats" whatever is at the given location: The tile is replaced
     * by a passage.
     * @param location which tile to eat
     * @returns the tile that was in the given location
     */
    public Tile eat(Location location) {
        Tile old = setTile(location, Tile.PASSAGE);
        if (old == Tile.PILL)
            pills--;
        return old;
    }
    
    
    /**
     * Return true iff the level is completed (i.e. all pills are eaten)
     * @returns true iff the level is completed
     */
    public boolean completed() {
        return pills == 0;
    }
    
    
    /**
     * Return the size of this level.
     * @return the location to the bottomright tile outside the maze.
     */
    public Location getSize() {
        return new Location(nbHorzCells, nbVertCells);
    }
    
    
    /**
     * Set the maze for this level to the default maze.
     */
    private void setupDefault()
    {
        /*String mazespec =
            "xx.xxxxxxxxxxxx" + // 0
            "xx.xxxxxxxxxxxx" + // 1
            "..............x" + // 2
            "xx.xxxx.xxxxx.x" + // 3
            "xx.xxxx.xxxxx.x" + // 4
            "xx.xxxx.xxxxx.x" + // 5
            "xx............." + // 6
            "xx.xxxx.xx.xxxx" + // 7
            "xx.xxxx.xx.xxxx" + // 8
            "xx......xx....x" + // 9
            "xxxxxxx.xxxxx x" + // 10
            "xxxxxxx.xxxxx x" + // 11
            "xxxxxxx.xx     " + // 12
            "xxxxxxx.xx xxx " + // 13
            "xxxxxxx.xx xxx " + // 14
            "xx     .   xx  " + // 15
            "xxxxxxx.xx xxxx" + // 16
            "xxxxxxx.xx xxxx" + // 17
            "xxxxxxx.xx     " + // 18
            "xxxxxxx.xx xxxx" + // 19
            "xxxxxxx.xx xxxx" + // 20
            "..............x" + // 21
            "xx.xxxx.xxxxx.x" + // 22
            "xx.xxxx.xxxxx.x" + // 23
            "xx...xx........" + // 24
            "xxxx.xx.xx.xxxx" + // 25
            "xxxx.xx.xx.xxxx" + // 26
            "xx......xx....x" + // 27
            "xx.xxxxxxxxxx.x" + // 28
            "xx.xxxxxxxxxx.x" + // 29
            "..............." + // 30
            ".xxxxxxxxxxxxxx" + // 31
            ".xxxxxxxxxxxxxx";   // 32*/
        //Map for testing
    	String mazespec =
            "xx xxxxxxxxxxxx" + // 0
            "xx xxxxxxxxxxxx" + // 1
            "              x" + // 2
            "xx xxxx xxxxx x" + // 3
            "xx xxxx xxxxx x" + // 4
            "xx xxxx xxxxx x" + // 5
            "xx             " + // 6
            "xx xxxx xx xxxx" + // 7
            "xx xxxx xx xxxx" + // 8
            "xx      xx    x" + // 9
            "xxxxxxx xxxxx x" + // 10
            "xxxxxxx xxxxxxx" + // 11
            "xxxxxxx xx     " + // 12
            "xxxxxxx xx xxx " + // 13
            "xxxxxxx xx xxx " + // 14
            "xx       x xx  " + // 15
            "xxxxxxx xx xxxx" + // 16
            "xxxxxxx xx xxxx" + // 17
            "xxxxxxx xx     " + // 18
            "xxxxxxx xx xxxx" + // 19
            "xxxxxxx xx xxxx" + // 20
            "         x    x" + // 21
            "xx xxxx xxxxx x" + // 22
            "xx xxxx xxxxx x" + // 23
            "xx   xx        " + // 24
            "xxxx xx xx xxxx" + // 25
            "xxxx xx xx xxxx" + // 26
            "xx      xx ...x" + // 27
            "xx xxxxxxxxxx x" + // 28
            "xx xxxxxxxxxx x" + // 29
            "               " + // 30
            " xxxxxxxxxxxxxx" + // 31
            " xxxxxxxxxxxxxx";   // 32
        
        // Copy structure into maze
        for (int i = 0; i < nbVertCells; i++)
        {
            for (int k = 0; k < nbHorzCells / 2; k++)
                maze[i][k] = charToTile(mazespec.charAt(15 * i + k));
            // Mirror at vertical axis at nbHorzCells / 2
            for (int k = nbHorzCells / 2; k < nbHorzCells; k++)
                maze[i][k] = charToTile(mazespec.charAt(15 * i + (29 - k)));
        }
    }

    
    /**
     * Convert a character from the string representation of the maze
     * to the corresponding Tile.
     */
    private Tile charToTile(char c)
    {
        switch (c) {
            case 'x': return Tile.WALL;
            case '.': return Tile.PILL;
            case ' ': return Tile.PASSAGE;
        }

        throw new RuntimeException("Unknown tile \'"+ c +"\'");
        //NEVER REACHED
    }
}

// EOF