package uet.oop.bomberman.components.enemy;

import com.almasb.fxgl.texture.AnimationChannel;
import javafx.util.Duration;
import uet.oop.bomberman.BBMType;

import static com.almasb.fxgl.dsl.FXGL.*;
import static uet.oop.bomberman.constants.Constants.ENEMY_SPEED;
import static uet.oop.bomberman.constants.Constants.spriteSheetChosen;

/** Ban chat giong Balloom toc do di chuyen bi random sau moi lan cham tuong*/
public class Dahl extends Enemy {
    public Dahl() {
        super();
        onCollisionBegin(BBMType.DAHL_E, BBMType.WALL,
                (b, w) -> b.getComponent(Dahl.class).turn());

        onCollisionBegin(BBMType.DAHL_E, BBMType.BOMB,
                (b, bo) -> b.getComponent(Dahl.class).turn());

        onCollisionBegin(BBMType.DAHL_E, BBMType.AROUND_WALL,
                (b, w) -> b.getComponent(Dahl.class).turn());

        onCollisionBegin(BBMType.DAHL_E, BBMType.BRICK,
                (b, br) -> b.getComponent(Dahl.class).turn());
    }

    @Override
    protected void setAnimationMove() {
        animDie = new AnimationChannel(image(spriteSheetChosen), 16, SIZE_FRAME, SIZE_FRAME,
                Duration.seconds(ANIM_TIME), 22 + 16*2, 22 + 16*2);
        animWalkRight = new AnimationChannel(image(spriteSheetChosen), 16, SIZE_FRAME, SIZE_FRAME,
                Duration.seconds(ANIM_TIME), 19 + 16*2, 21 + 16*2);
        animWalkLeft = new AnimationChannel(image(spriteSheetChosen), 16, 48, 48,
                Duration.seconds(ANIM_TIME), 16 + 16*2, 18 + 16*2);
        animStop = new AnimationChannel(image(spriteSheetChosen), 16, 48, 48,
                Duration.seconds(1), 16 + 16*2, 22 + 16*2);
    }

    @Override
    public void enemyDie() {
        super.enemyDie();
        int DAHL_SCORE = 200;
        showScoreWhenEnemyDie(DAHL_SCORE);
        inc("score", DAHL_SCORE);
    }

    @Override
    public double getRandom() {
        double tmp = Math.random();
        //De no k cham qua cx k nhanh qua ma luon ra khoi map
        while (tmp < 0.3 || tmp > 0.7) {
            tmp = Math.random();
        }
        double multiplierRandomNumber = tmp * 5;
        double speedRandom = ENEMY_SPEED;
        if (Math.random() > 0.5) {
            speedRandom *= multiplierRandomNumber;
        } else {
            speedRandom *= -multiplierRandomNumber;
        }
        return speedRandom;
    }
}
