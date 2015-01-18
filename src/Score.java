/**
 * Score.java
 * This class contains scores and lives number of player.
 * 
 * @author Anton Bubnov and Tony Stankov
 *
 */
public class Score {
    private PaKman game; // Current game object
    private int score; // Score earned in previous levels
    private int curScore; // Score earned in current levels
    private int lives; // Default lives value
    private int curLives; // Number of current lives

    Score(PaKman _game, int _lives) {
        // Needs to call PaKman.displayScore()
        this.game = _game;
        // Save default lives number for next rounds
        this.lives = _lives;
        // Set values for this round
        score = 0;
        curScore = 0;
        curLives = lives;
    }

    /**
     * @return the score
     */
    public int getScore() {
        return score;
    }

    /**
     * @param score
     * the score to set
     */
    public void setScore(int score) {
        this.score = score;
        updateTitle();
    }

    /**
     * @return the curScore
     */
    public int getCurScore() {
        return curScore;
    }

    /**
     * @param curScore
     * the curScore to set
     */
    public void setCurScore(int curScore) {
        this.curScore = curScore;
        updateTitle();
    }

    /**
     * @return the lives
     */
    public int getLives() {
        return lives;
    }

    /**
     * @param lives
     * the lives to set
     */
    public void setLives(int lives) {
        this.lives = lives;
        updateTitle();
    }

    /**
     * @return the curLives
     */
    public int getCurLives() {
        return curLives;
    }

    /**
     * @param curLives
     * the curLives to set
     */
    public void setCurLives(int curLives) {
        this.curLives = curLives;
        updateTitle();
    }

    /**
     * Decrease lives number in one. Check if lives available.
     * 
     * @return false if dead, else true
     */
    public boolean decrementLives() {
        curLives -= 1;
        updateTitle();
        return curLives == 0 ? false : true;
    }

    /**
     * Reset values for new game round.
     */
    public void reset() {
        score = 0;
        curScore = 0;
        curLives = lives; // Set default for this round live value
        updateTitle();
    }

    /**
     * Increase score on given value
     * 
     * @param number
     * of points
     */
    public void addCurScore(int num) {
        curScore += num;
        updateTitle();
    }

    /**
     * Saves current score and reset it. Used when level is complete.
     */
    public void saveCurScore() {
        score += curScore;
        curScore = 0;
        updateTitle();
    }

    /**
     * Update score and lives number in title.
     */
    public void updateTitle() {
        game.displayScore(0, score + curScore, curLives);
    }

}
// EOF