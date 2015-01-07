// PaKman.java
// Simple PaKman implementation

import ch.aplu.jgamegrid.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class PaKman extends GameGrid implements GGKeyListener
{
    protected PaKActor pacActor;
    private Ghost pinky;
    private Level theLevel;
    private boolean checkCollisions;    // For the collision mechanism below

    public PaKman() {
        super(30, 33, 20, true);    // Need to set the winsize, because it cannot be changed.
        pacActor = new PaKActor(this);
        setSimulationPeriod(100);
        setTitle("PaKman");
        addKeyListener(this);
        addActListener(new CheckerReset());
        
        setupLevel(new Level(this));
        
        // Show and activate the game window
        show();
        activate();
    }

    
    public void reset() {
        checkCollisions = true;
        removeAllActors();
        setupLevel(new Level(this));
        updateTitle(); //Update score and lives if was "game over"
    }

    
    /**
     * Toggle hunting/fleeing mode.
     */
    public void toggleHunting() {
        pinky.toggleHunting();
    }
    
    
    /**
     * Setup the given level:
     * <ul>
     *  <li> draw the maze; </li>
     *  <li> put pakman at its starting position; </li>
     *  <li> create the ghost(s) at their starting positions; </li>
     *  <li> initializes internae</li>
     * </ul>
     */
    public void setupLevel(Level level) {
        theLevel = level;
        setNbHorzCells(level.getSize().x);
        setNbVertCells(level.getSize().y);
        level.drawLevel();
        addActor(new PostMovementChecker(), level.getSize());
        addActor(pacActor, level.getPakmanStart());
        addActor(new PreMovementChecker(), level.getSize());
        
        pinky = new Ghost(this);
        addActor(pinky, level.getGhostStart());
        
        // pakman acts after ghosts and between movement checkers, to ensure correct collision detection
        setActOrder(Ghost.class, PreMovementChecker.class, PaKActor.class, PostMovementChecker.class);
    }
    
    
    /**
     * Called with pac == pacActor when pacActor and some other actor are at the same location.
     * @return 0
     */
    public int collide(Actor pac, Actor other) {
        pac.collide(pac, other);
        other.collide(other, pac);
        checkLives();
        return 0;
    }
    
    
    /**
     * Decrease lives number. Check if level failed or game over
     * and reset pacActor if the last one.
     */
    private void checkLives(){
        int lives = pacActor.getLives();
        if (lives <=0){
            updateTitle();
            gameOver();
            pacActor = new PaKActor(this); //Create new pacActor with default params.
        }else{
            pacActor.setLives(lives-1);
            pacActor.setCurScore(0);
            updateTitle();
            levelFail();
        }
    }
    
    
    /**
     * Update game window title, which contains score and number of lives, 
     * with actual data.
     */
    public void updateTitle(){
        displayScore(0, pacActor.getCurScore() + pacActor.getScore(), pacActor.getLives());
    }

    
    /**
     * Return some ghost of type Randy. For testing purposes only
     */
    public Actor getRandy() {
        return null;
    }

    
    /**
     * Return some ghost of type Silly.
     */
    public Actor getSilly() {
        return null;
    }

    
    /**
     * Return some ghost of type Tracy.
     */
    public Actor getTracy() {
        return null;
    }
    
    //==================================================================================================
    //=========================== DO NOT CHANGE ANYTHIGN BELOW THIS LINE ===============================
    //==================================================================================================

    /**
     * Return PaKman's location.
     * @returns pakman's location
     */
    public Location wherePakman() {
        return pacActor.getLocation();
    }


    /**
     * Displays the given score string, e.g: "Player 1: 500"
     * @param player the player number (currently ignored)
     * @param score the score of this player
     * @param lives the number of lives of this player
     */
    public void displayScore(int player, int score, int lives) {
        setTitle("Score: "+ score +" lives: "+ lives);
    }
    
    
    /**
     * Return the current level.
     * @return the current level
     */
    public Level getLevel() {
        return theLevel;
    }       
    

    
    /**
     * Display a message and pause the game.
     * @param message the message to display
     */
    private void pause(String message) {
        GGBackground bg = getBg();
        bg.setPaintColor(Color.red);
        bg.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 48));
        bg.drawText(message, new Point(toPoint(new Location(6, 15))));
        refresh();
        doPause();
    }
 
    
    /**
     * Display a game over message and pause the game.
     */
    public void gameOver() {
        pause("Game Over");
    }
 
    
    /**
     * Display a next level message and pause the game.
     */
    public void levelDone() {
        pause("You won!");
    }

    
    /**
     * Display a you died message and pause the game.
     */
    public void levelFail() {
        pause("You died");
    }
    
    
    public static void main(String[] args) {
        new PaKman();
    }

    //===================================== Actor collision detection methods ======================
    /* Because the GameGrid collision detector doesn't detect collision when two actor move
       simultaneously and cross each other, we implement our own detectors. There are two actors
       (so they will be called IN the gameloop), to be called before and after the pacman(s) move.
     */
    private class PreMovementChecker extends Actor {
        public void act() {
            checkCollision();
        }
    }
    
    private class PostMovementChecker extends Actor {
        public void act() {
            checkCollision();
        }
    }
    
    private class CheckerReset implements GGActListener {
        public void act() {
            checkCollisions = true;
        }
    }

    
    /**
     * Check whether pakman and a ghost collide.
     * If so, call the collide() method.
     */
    public void checkCollision() {
        if (!checkCollisions)
            return;
            
        for (Actor a : getActors()) {
            if (a == pacActor)
                continue;
            if (pacActor.getLocation().equals(a.getLocation())) {
                collide(pacActor, a);
                checkCollisions = false;
                return;
            }
        }     
    }
    
    
    
    //========================================== Key listener methods =============================
    
    /** KeyListener method (no function for us) */
    public boolean keyPressed(KeyEvent event) {
        return false;
    }

    /**
     * KeyListener method.
     * Here we act on the (press and) release of keys.
     * Currently implemented:
     * f toggles hunting/fleeing mode.
     */
    public boolean keyReleased(KeyEvent event) {
        switch (event.getKeyChar()) {
            case 'f': toggleHunting(); return true;
        }
        
        return false;
    }
}
