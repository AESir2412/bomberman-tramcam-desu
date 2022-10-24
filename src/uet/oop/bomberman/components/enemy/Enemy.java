package uet.oop.bomberman.components.enemy;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.BoundingBoxComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import uet.oop.bomberman.BBMType;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

import java.util.List;

import static uet.oop.bomberman.constants.Constants.ENEMY_SPEED;

public abstract class Enemy extends Component {
    private double lastX = 0;
    private double lastY = 0;


    private enum TurnDirection {
        BLOCK_LEFT, BLOCK_RIGHT, BLOCK_UP, BLOCK_DOWN
    }

    protected double dx = ENEMY_SPEED;
    protected double dy = 0;

    protected final AnimatedTexture texture;
    protected static final double ANIM_TIME = 0.5;
    protected static final int SIZE_FRAME = 48;

    protected AnimationChannel animWalkRight;
    protected AnimationChannel animWalkLeft;
    protected AnimationChannel animDie;
    protected AnimationChannel animStop;
    protected int rangeDetectPlayer = 60;

    public Enemy() {
        setAnimationMove();
        texture = new AnimatedTexture(animWalkRight);
        texture.loop();
    }

    protected abstract void setAnimationMove();

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
    }

    @Override
    public void onUpdate(double tpf) {
        entity.setScaleUniform(0.9);
        entity.translateX(dx * tpf);
        entity.translateY(dy * tpf);

        setAnimationStage();
    }


    protected void setAnimationStage() {
        double dx = entity.getX() - lastX;
        double dy = entity.getY() - lastY;

        lastX = entity.getX();
        lastY = entity.getY();

        //Khi cham vao tuong (hai khoang cach giua cac frame 2 chieu = 0 )
        if (dx == 0 && dy == 0) {
            return;
        }

        if (Math.abs(dx) > Math.abs(dy)) {
            if (dx > 0) {
                texture.loopNoOverride(animWalkRight);
            } else {
                texture.loopNoOverride(animWalkLeft);
            }
        } else {
            if (dy > 0) {
                texture.loopNoOverride(animWalkLeft);
            } else {
                texture.loopNoOverride(animWalkRight);
            }
        }
    }


    private void setTurnEnemy(TurnDirection direct) {
        switch (direct) {
            case BLOCK_LEFT -> {
                entity.translateX(3);
                dx = 0.0;
                dy = getRandom();
            }
            case BLOCK_RIGHT -> {
                entity.translateX(-3);
                dx = 0.0;
                dy = getRandom();
            }
            case BLOCK_UP -> {
                entity.translateY(3);
                dy = 0.0;
                dx = getRandom();
            }
            case BLOCK_DOWN -> {
                entity.translateY(-3);
                dy = 0.0;
                dx = getRandom();
            }
        }
    }

    protected double getRandom() {
        if (Math.random() > 0.5) {
            return ENEMY_SPEED;
        }
        else return -ENEMY_SPEED;
    }

    protected void turn() {
        if (dx < 0.0) {
            setTurnEnemy(TurnDirection.BLOCK_LEFT);
        } else if (dx > 0.0) {
            setTurnEnemy(TurnDirection.BLOCK_RIGHT);
        } else if (dy < 0.0) {
            setTurnEnemy(TurnDirection.BLOCK_UP);
        } else if (dy > 0.0) {
            setTurnEnemy(TurnDirection.BLOCK_DOWN);
        }
    }

    protected boolean isDetectPlayer() {
        BoundingBoxComponent bbox = entity.getBoundingBoxComponent();
        List<Entity> list = FXGL.getGameWorld().getEntitiesInRange(bbox.range(rangeDetectPlayer, rangeDetectPlayer));
        for (Entity entity : list) {
            if (entity.isType(BBMType.BOMB)) {
                return false;
            }
        }
        for (Entity entity : list) {
            if (entity.isType(BBMType.PLAYER)) {
                return true;
            }
        }
        return false;
    }

    protected void showScoreWhenEnemyDie(int score) {
        Label labelScore = new Label();
        labelScore.setText(score + "!");
        labelScore.setFont(Font.font("Comic Sans MS", FontWeight.EXTRA_BOLD, 15));
        labelScore.setTextFill(Color.WHITE);
        FXGL.addUINode(labelScore, entity.getX() + 24, entity.getY() + 24);
        FXGL.getGameTimer().runOnceAfter(() -> FXGL.removeUINode(labelScore), Duration.seconds(2));
    }

    public void enemyDie() {
        dx = 0;
        dy = 0;
        texture.loopNoOverride(animDie);
    }

    public void enemyStop() {
        dx = 0;
        dy = 0;
        texture.loopNoOverride(animStop);
    }

    public void setRangeDetectPlayer(int rangeDetectPlayer) {
        this.rangeDetectPlayer = rangeDetectPlayer;
    }
}

