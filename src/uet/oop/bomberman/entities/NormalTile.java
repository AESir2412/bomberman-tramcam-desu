package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.control.Camera;

public class NormalTile extends Entity{
    public NormalTile(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        //TODO Auto-generated constructor stub
    }

    @Override
    public void update() {

    }

    @Override
    public void render(GraphicsContext gc, Camera camera) {
        gc.drawImage(img, x - camera.getX(), y - camera.getY());
    }
}
