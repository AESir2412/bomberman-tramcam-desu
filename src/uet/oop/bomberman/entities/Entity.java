package uet.oop.bomberman.entities;

import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.control.Camera;
import uet.oop.bomberman.graphics.Sprite;

public abstract class Entity {
    //Tọa độ X tính từ góc trái trên trong Canvas
    protected int x;

    //Tọa độ Y tính từ góc trái trên trong Canvas
    protected int y;
    protected int valueCompare;
    protected Image img;
    Hitbox hitbox;

    //Khởi tạo đối tượng, chuyển từ tọa độ đơn vị sang tọa độ trong canvas
    public Entity( int xUnit, int yUnit, Image img) {
        this.x = xUnit * Sprite.SCALED_SIZE;
        this.y = yUnit * Sprite.SCALED_SIZE;
        this.img = img;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }

    public void updateCount(){

    }

    // public void render(GraphicsContext gc, Camera camera) {
    //     gc.drawImage(img, x - camera.getX(), y - camera.getY());
    // }
    public abstract void render(GraphicsContext gc, Camera camera);

    public void update(){

    }

    public void update(List<List<Entity>> map, List<Bomber> bombers) {
    }

    /**
     * Getter method for x coordination.
     * @return int x
     */
    public int getX() {
        return x;
    }
    public void setX(int X){
        x = X;
    }
    public void setXUnit(int X){
        x = X * Sprite.SCALED_SIZE;
    }
    public int getModX(){
        return Math.round((x - 16) / Sprite.SCALED_SIZE) + 1;
    }
    /**
     * Getter method for y coordination.
     *
     * @return int y
     */
    public int getY() {
        return y;
    }
    public void setY(int Y){
        y = Y;
    }
    public void setYUnit(int Y){
        y = Y * Sprite.SCALED_SIZE;
    }
    public int getModY(){
        return Math.round((y - 16) / Sprite.SCALED_SIZE) + 1;
    }

    public int getValueCompare(){
        return valueCompare;
    }
    public void setValueCompare(int value){
        valueCompare = value;
    }

    /**
     * Setter method for hitbox.
     */
    public void setHitbox(Hitbox hitbox) {
        this.hitbox = hitbox;
    }

    /**
     * Getter method for hitbox.
     */
    public Hitbox getHitbox() {
        return hitbox;
    }

    public String toString() {
        return "Entity [" + x + ", " + y + "]";
    }

    public int getFixHitbox() {
        return 0;
    }
}
