package io.mygame.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import io.mygame.entities.Player;
import com.badlogic.gdx.Input;
public class InputHandler{
    Player player;
    public InputHandler(Player player){
        this.player = player;
    }

    public void moveUp() {
        player.setY(player.getY() + player.getSPEED() * Gdx.graphics.getDeltaTime());
        player.setDirection(Player.Direction.BACK);
        player.setIsMoving(true);
    }

    public void moveDown() {
        player.setY(player.getY() - player.getSPEED() * Gdx.graphics.getDeltaTime());
        player.setDirection(Player.Direction.FRONT);
        player.setIsMoving(true);
    }

    public void moveLeft() {
        player.setX(player.getX() - player.getSPEED() * Gdx.graphics.getDeltaTime());
        player.setDirection(Player.Direction.LEFT);
        player.setIsMoving(true);
    }

    public void moveRight() {
        player.setX(player.getX() + player.getSPEED() * Gdx.graphics.getDeltaTime());
        player.setDirection(Player.Direction.RIGHT);
        player.setIsMoving(true);
    }

    public void moveUpRight() {
        float diagonalSpeed = player.getSPEED() * Gdx.graphics.getDeltaTime() / (float) Math.sqrt(2);
        player.setX(player.getX() + diagonalSpeed);
        player.setY(player.getY() + diagonalSpeed);
        player.setDirection(Player.Direction.RIGHT);
        player.setIsMoving(true);
    }

    public void moveUpLeft() {
        float diagonalSpeed = player.getSPEED() * Gdx.graphics.getDeltaTime() / (float) Math.sqrt(2);
        player.setX(player.getX() - diagonalSpeed);
        player.setY(player.getY() + diagonalSpeed);
        player.setDirection(Player.Direction.LEFT);
        player.setIsMoving(true);
    }

    public void moveDownRight() {
        float diagonalSpeed = player.getSPEED() * Gdx.graphics.getDeltaTime() / (float) Math.sqrt(2);
        player.setX(player.getX() + diagonalSpeed);
        player.setY(player.getY() - diagonalSpeed);
        player.setDirection(Player.Direction.RIGHT);
        player.setIsMoving(true);
    }

    public void moveDownLeft() {
        float diagonalSpeed = player.getSPEED() * Gdx.graphics.getDeltaTime() / (float) Math.sqrt(2);
        player.setX(player.getX() - diagonalSpeed);
        player.setY(player.getY() - diagonalSpeed);
        player.setDirection(Player.Direction.LEFT);
        player.setIsMoving(true);
    }
}
