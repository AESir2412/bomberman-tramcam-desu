package uet.oop.bomberman.control;

import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Graphics;
import uet.oop.bomberman.graphics.Sprite;

public class Camera {
    private int x;
    private int y;
    private int screenWidth;
    private int screenHeight;

    public Camera(int x, int y, int screenWidth, int screenHeight) {
        this.x = x;
        this.y = y;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    public void update(Entity bomber) {
        x = bomber.getX() - Graphics.WIDTH * Sprite.DEFAULT_SIZE;
        if (x < 0) x = 0;
        if (x + Graphics.WIDTH * Sprite.SCALED_SIZE > screenWidth * Sprite.SCALED_SIZE) {
            x = screenWidth * Sprite.SCALED_SIZE - Graphics.WIDTH * Sprite.SCALED_SIZE;
        }

        y = bomber.getY() - Graphics.HEIGHT * Sprite.DEFAULT_SIZE;
        if (y < 0) y = 0;
        if (y + Graphics.HEIGHT * Sprite.SCALED_SIZE > screenHeight * Sprite.SCALED_SIZE) {
            y = screenHeight * Sprite.SCALED_SIZE - Graphics.HEIGHT * Sprite.SCALED_SIZE;
        }
    }

    /**
     * Getter for x.
     */
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }
}
