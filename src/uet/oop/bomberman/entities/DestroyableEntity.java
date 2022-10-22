package uet.oop.bomberman.entities;

import javafx.scene.image.Image;

public abstract class DestroyableEntity extends MovingEntity {

    protected boolean death = false;

    public DestroyableEntity(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        //TODO Auto-generated constructor stub
    }



    public abstract void die();

    public void update() {
    }

    @Override
    public Image chooseSprite() {
        // TODO Auto-generated method stub
        return null;
    }
    public boolean getDeath(){
        return death;
    }
}