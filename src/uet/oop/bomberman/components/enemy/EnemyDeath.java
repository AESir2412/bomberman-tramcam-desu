package uet.oop.bomberman.components.enemy;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGLForKtKt.image;
import static uet.oop.bomberman.constants.Constants.SIZE_BLOCK;
import static uet.oop.bomberman.constants.Constants.spriteSheetChosen;

public class EnemyDeath extends Component {

    private final AnimatedTexture texture;

    public EnemyDeath() {
        AnimationChannel animationChannel = new AnimationChannel(image(spriteSheetChosen), 16, SIZE_BLOCK, SIZE_BLOCK,
                Duration.seconds(1.5), 75, 77);

        texture = new AnimatedTexture(animationChannel);
        texture.loop();
    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
    }
}
