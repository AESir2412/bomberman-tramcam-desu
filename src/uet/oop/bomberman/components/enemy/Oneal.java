package uet.oop.bomberman.components.enemy;


import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Required;
import com.almasb.fxgl.pathfinding.astar.AStarMoveComponent;
import com.almasb.fxgl.texture.AnimationChannel;
import uet.oop.bomberman.BBMType;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.*;
import static uet.oop.bomberman.constants.Constants.spriteSheetChosen;

@Required(AStarMoveComponent.class)
public class Oneal extends Enemy {
    private double oldX = 0;
    private double oldY = 0;

    protected boolean die = false;
    protected AStarMoveComponent astar;

    public Oneal() {
        super();
        onCollisionBegin(BBMType.ONEAL_E, BBMType.BOMB,
                (o, w) -> o.getComponent(Oneal.class).turn());

        onCollisionBegin(BBMType.ONEAL_E, BBMType.WALL,
                (o, w) -> o.getComponent(Oneal.class).turn());

        onCollisionBegin(BBMType.ONEAL_E, BBMType.AROUND_WALL,
                (o, w) -> o.getComponent(Oneal.class).turn());

        onCollisionBegin(BBMType.ONEAL_E, BBMType.BRICK,
                (o, br) -> o.getComponent(Oneal.class).turn());

    }

    @Override
    public void onUpdate(double tpf) {
        if (!die) {
            if (isDetectPlayer()) {
                astar.resume();
                moveAi();
            } else {
                astar.pause();
                moveNotAi(tpf);
            }
            setAnimationStage();
        }
    }

    //Neu k detect duoc player, chay ham giong y het bthg luc di chuyen o enemy
    protected void moveNotAi(double tpf) {
        // fix bug move
        double x = entity.getX() - oldX;
        double y = entity.getY() - oldY;

        oldX = entity.getX();
        oldY = entity.getY();

        if (x == 0 && y == 0) turn();
        //
        entity.setScaleUniform(0.9);
        entity.translateX(dx * tpf);
        entity.translateY(dy * tpf);
    }

    private void moveAi() {
        var player = FXGL.getGameWorld().getSingleton(BBMType.PLAYER);

        int x = player.call("getCellX");
        int y = player.call("getCellY");

        //Di chuyen den vi tri x, y cua player
        astar.moveToCell(x, y);
    }

    @Override
    protected void setAnimationMove() {
        animDie = new AnimationChannel(image(spriteSheetChosen), 16, 48, 48,
                Duration.seconds(ANIM_TIME), 38, 38);
        animWalkRight = new AnimationChannel(image(spriteSheetChosen), 16, 48, 48,
                Duration.seconds(ANIM_TIME), 35, 37);
        animWalkLeft = new AnimationChannel(image(spriteSheetChosen), 16, 48, 48,
                Duration.seconds(ANIM_TIME), 32, 34);
        animStop = new AnimationChannel(image(spriteSheetChosen), 16, 48, 48,
                Duration.seconds(1), 32, 36);
    }

    @Override
    public void enemyDie() {
        super.enemyDie();

        int ONEAL_SCORE = 200;
        showScoreWhenEnemyDie(ONEAL_SCORE);
        inc("score", ONEAL_SCORE);
        die = true;
        astar.pause();
    }

    @Override
    public void enemyStop() {
        super.enemyStop();
        astar.pause();
    }
}

