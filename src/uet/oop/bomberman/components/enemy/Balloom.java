package uet.oop.bomberman.components.enemy;

import com.almasb.fxgl.texture.AnimationChannel;
import uet.oop.bomberman.BBMType;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.*;
import static uet.oop.bomberman.constants.Constants.SIZE_BLOCK;
import static uet.oop.bomberman.constants.Constants.spriteSheetChosen;

public class Balloom extends Enemy {

    public Balloom() {
        super();
        onCollisionBegin(BBMType.BALLOOM_E, BBMType.WALL,
                (b, w) -> b.getComponent(Balloom.class).turn());

        onCollisionBegin(BBMType.BALLOOM_E, BBMType.BOMB,
                (b, bo) -> b.getComponent(Balloom.class).turn());

        onCollisionBegin(BBMType.BALLOOM_E, BBMType.AROUND_WALL,
                (b, w) -> b.getComponent(Balloom.class).turn());

        onCollisionBegin(BBMType.BALLOOM_E, BBMType.BRICK,
                (b, br) -> b.getComponent(Balloom.class).turn());


    }

    @Override
    protected void setAnimationMove() {
        animDie = new AnimationChannel(image(spriteSheetChosen), 16, SIZE_FRAME, SIZE_FRAME,
                Duration.seconds(ANIM_TIME), 22, 22);
        animWalkRight = new AnimationChannel(image(spriteSheetChosen), 16, SIZE_FRAME, SIZE_FRAME,
                Duration.seconds(ANIM_TIME), 19, 21);
        animWalkLeft = new AnimationChannel(image(spriteSheetChosen), 16, 48, 48,
                Duration.seconds(ANIM_TIME), 16, 18);
        animStop = new AnimationChannel(image(spriteSheetChosen), 16, 48, 48,
                Duration.seconds(1), 16, 22);
    }

    @Override
    public void enemyDie() {
        super.enemyDie();
        int BALLOOM_SCORE = 100;
        showScoreWhenEnemyDie(BALLOOM_SCORE);
        inc("score", BALLOOM_SCORE);
    }
}
