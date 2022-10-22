package uet.oop.bomberman.entities;

public class Hitbox {
    public static int FIX_HITBOX_EXLOSION = 4;
    public static int FIX_HITBOX_ENEMY = 4;
    public static int FIX_HITBOX_BOMBER_X = 8;
    public static int FIX_HITBOX_BOMBER_Y = 4;
    public static int FIX_HITBOX_BOMB = 2;

    protected int x;
    protected int y;
    protected int width;
    protected int height;


    public Hitbox(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Getter method for x coordination.
     * @return int x
     */
    public int getX() {
        return x;
    }

    /**
     * Getter method for y coordination.
     */
    public int getY() {
        return y;
    }

    /**
     * Getter method for width.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Getter method for height.
     */
    public int getHeight() {
        return height;
    }

    public String toString() {
        return "Hitbox[" + x + ", " + y + ", " + width + ", " + height + "]";
    }

    public boolean collideWith(Hitbox that) {
        return contain(that.x, that.y) ||
                contain(that.x + that.width, that.y) ||
                contain(that.x, that.y) ||
                contain(that.x + that.width, that.y + that.height) ||
                that.contain(x, y) ||
                that.contain(x + width, y) ||
                that.contain(x, y + height) ||
                that.contain(x + width, y + height);
    }

    public boolean contain(int xThat, int yThat) {
        return x <= xThat && xThat <= x + width && y <= yThat && yThat <= y + height;
    }
}
