package uet.oop.bomberman.components.enemy;

import com.almasb.fxgl.texture.AnimationChannel;
import uet.oop.bomberman.BBMType;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.*;
import static uet.oop.bomberman.constants.Constants.SIZE_BLOCK;
import static uet.oop.bomberman.constants.Constants.spriteSheetChosen;

/** Ban chat giong Balloom nhg nhanh hon 2 lan va di qua duoc Brick.*/
public class Ghost extends Enemy {
    private int GHOST_EXTRA_SPEED_MULTIPLIER = 2;

    @Override
    public void onUpdate(double tpf) {
        entity.setScaleUniform(0.9);
        entity.translateX(dx * tpf * GHOST_EXTRA_SPEED_MULTIPLIER);
        entity.translateY(dy * tpf * GHOST_EXTRA_SPEED_MULTIPLIER);

        setAnimationStage();
    }

    public Ghost() {
        super();
        onCollisionBegin(BBMType.GHOST_E, BBMType.WALL,
                (b, w) -> b.getComponent(Ghost.class).turn());

        onCollisionBegin(BBMType.GHOST_E, BBMType.BOMB,
                (b, bo) -> b.getComponent(Ghost.class).turn());

        onCollisionBegin(BBMType.GHOST_E, BBMType.AROUND_WALL,
                (b, w) -> b.getComponent(Ghost.class).turn());

       /* onCollisionBegin(BBMType.GHOST_E, BBMType.BRICK,
                (b, br) -> b.getComponent(Ghost.class).turn());*/

    }

    @Override
    protected void setAnimationMove() {
        animDie = new AnimationChannel(image(spriteSheetChosen), 16, SIZE_FRAME, SIZE_FRAME,
                Duration.seconds(ANIM_TIME), 22 + 16*4, 22 + 16*4);
        animWalkRight = new AnimationChannel(image(spriteSheetChosen), 16, SIZE_FRAME, SIZE_FRAME,
                Duration.seconds(ANIM_TIME), 19 + 16*4, 21 + 16*4);
        animWalkLeft = new AnimationChannel(image(spriteSheetChosen), 16, 48, 48,
                Duration.seconds(ANIM_TIME), 16 + 16*4, 18 + 16*4);
        animStop = new AnimationChannel(image(spriteSheetChosen), 16, 48, 48,
                Duration.seconds(1), 16 + 16*4, 22 + 16*4);
    }

    @Override
    public void enemyDie() {
        super.enemyDie();
        int GHOST_SCORE = 500;
        showScoreWhenEnemyDie(GHOST_SCORE);
        inc("score", GHOST_SCORE);
    }
}
