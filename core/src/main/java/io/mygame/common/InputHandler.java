package io.mygame.common;

import com.badlogic.gdx.Gdx;
import io.mygame.enums.Direction;
import io.mygame.entities.Player;

/**
 * The InputHandler class handles user input for player movement.
 * It listens for directional inputs and moves the player accordingly,
 * updating the player's position and direction.
 */
public class InputHandler{
    /************ PLAYER REFERENCE ************/
    private final Player player;

    /**
     * Constructor that initializes the InputHandler with a player instance.
     *
     * @param player The player instance to be controlled by this InputHandler.
     */
    public InputHandler(Player player) {
        this.player = player;
    }

    /**
     * Moves the player up, sets direction to BACK.
     * Increases the Y-coordinate of the player based on speed and delta time.
     */
    public void moveUp() {
        player.setY(player.getY() + player.getSPEED() * Gdx.graphics.getDeltaTime());
        player.setDirection(Direction.BACK);
        player.setIsMoving(true);
    }

    /**
     * Moves the player down, sets direction to FRONT.
     * Decreases the Y-coordinate of the player based on speed and delta time.
     */
    public void moveDown() {
        player.setY(player.getY() - player.getSPEED() * Gdx.graphics.getDeltaTime());
        player.setDirection(Direction.FRONT);
        player.setIsMoving(true);
    }

    /**
     * Moves the player left, sets direction to LEFT.
     * Decreases the X-coordinate of the player based on speed and delta time.
     */
    public void moveLeft() {
        player.setX(player.getX() - player.getSPEED() * Gdx.graphics.getDeltaTime());
        player.setDirection(Direction.LEFT);
        player.setIsMoving(true);
    }

    /**
     * Moves the player right, sets direction to RIGHT.
     * Increases the X-coordinate of the player based on speed and delta time.
     */
    public void moveRight() {
        player.setX(player.getX() + player.getSPEED() * Gdx.graphics.getDeltaTime());
        player.setDirection(Direction.RIGHT);
        player.setIsMoving(true);
    }

    /**
     * Moves the player diagonally up-right, sets direction to RIGHT.
     * Moves the player along both axes at a reduced speed for diagonal movement.
     */
    public void moveUpRight() {
        float diagonalSpeed = player.getSPEED() * Gdx.graphics.getDeltaTime() / (float) Math.sqrt(2);
        player.setX(player.getX() + diagonalSpeed);
        player.setY(player.getY() + diagonalSpeed);
        player.setDirection(Direction.RIGHT);
        player.setIsMoving(true);
    }

    /**
     * Moves the player diagonally up-left, sets direction to LEFT.
     * Moves the player along both axes at a reduced speed for diagonal movement.
     */
    public void moveUpLeft() {
        float diagonalSpeed = player.getSPEED() * Gdx.graphics.getDeltaTime() / (float) Math.sqrt(2);
        player.setX(player.getX() - diagonalSpeed);
        player.setY(player.getY() + diagonalSpeed);
        player.setDirection(Direction.LEFT);
        player.setIsMoving(true);
    }

    /**
     * Moves the player diagonally down-right, sets direction to RIGHT.
     * Moves the player along both axes at a reduced speed for diagonal movement.
     */
    public void moveDownRight() {
        float diagonalSpeed = player.getSPEED() * Gdx.graphics.getDeltaTime() / (float) Math.sqrt(2);
        player.setX(player.getX() + diagonalSpeed);
        player.setY(player.getY() - diagonalSpeed);
        player.setDirection(Direction.RIGHT);
        player.setIsMoving(true);
    }

    /**
     * Moves the player diagonally down-left, sets direction to LEFT.
     * Moves the player along both axes at a reduced speed for diagonal movement.
     */
    public void moveDownLeft() {
        float diagonalSpeed = player.getSPEED() * Gdx.graphics.getDeltaTime() / (float) Math.sqrt(2);
        player.setX(player.getX() - diagonalSpeed);
        player.setY(player.getY() - diagonalSpeed);
        player.setDirection(Direction.LEFT);
        player.setIsMoving(true);
    }
}
