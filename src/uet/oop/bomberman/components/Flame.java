package uet.oop.bomberman.components;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.pathfinding.CellState;
import com.almasb.fxgl.pathfinding.astar.AStarGrid;
import com.almasb.fxgl.physics.PhysicsWorld;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import uet.oop.bomberman.BBMType;
import uet.oop.bomberman.components.enemy.*;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.*;
import static uet.oop.bomberman.constants.Constants.SIZE_BLOCK;
import static uet.oop.bomberman.constants.Constants.spriteSheetChosen;

public class Flame extends Component {
    private final AnimatedTexture texture;


    public Flame(int startF, int endF) {
        PhysicsWorld physics = getPhysicsWorld();

        onCollisionBegin(BBMType.FLAME, BBMType.WALL, (f, w) -> {
            f.removeFromWorld();
        });

        onCollisionBegin(BBMType.FLAME, BBMType.AROUND_WALL, (f, w) -> {
            f.removeFromWorld();
        });

        setCollisionBreak(BBMType.BRICK, "brick_break");

        setCollisionBreak(BBMType.GRASS, "grass_break");

        onCollisionBegin(BBMType.FLAME, BBMType.BALLOOM_E, (f, b) -> {
            double x = b.getX();
            double y = b.getY();
            b.getComponent(Balloom.class).enemyDie();
            getGameTimer().runOnceAfter(() -> {
                b.removeFromWorld();
                set("numOfEnemy", getEnemies());
            }, Duration.seconds(0.3));
            Entity entity = spawn("enemyDie", new SpawnData(x, y));
            getGameTimer().runOnceAfter(entity::removeFromWorld, Duration.seconds(1.5));
        });

        onCollisionBegin(BBMType.FLAME, BBMType.GHOST_E, (f, b) -> {
            double x = b.getX();
            double y = b.getY();
            b.getComponent(Ghost.class).enemyDie();
            getGameTimer().runOnceAfter(() -> {
                b.removeFromWorld();
                set("numOfEnemy", getEnemies());
            }, Duration.seconds(0.3));
            Entity entity = spawn("enemyDie", new SpawnData(x, y));
            getGameTimer().runOnceAfter(entity::removeFromWorld, Duration.seconds(1.5));
        });

        onCollisionBegin(BBMType.FLAME, BBMType.ONEAL_E, (f, o) -> {
            double x = o.getX();
            double y = o.getY();
            o.getComponent(Oneal.class).enemyDie();
            getGameTimer().runOnceAfter(() -> {
                o.removeFromWorld();
                set("numOfEnemy", getEnemies());
            }, Duration.seconds(0.3));
            Entity entity = spawn("enemyDie", new SpawnData(x, y));
            getGameTimer().runOnceAfter(entity::removeFromWorld, Duration.seconds(1.5));
        });

        AnimationChannel animationFlame = new AnimationChannel(image(spriteSheetChosen), 16, SIZE_BLOCK, SIZE_BLOCK,
                Duration.seconds(0.4), startF, endF);


        texture = new AnimatedTexture(animationFlame);
        texture.loop();
    }

    private int getEnemies() {
        return getGameWorld().getGroup(BBMType.ONEAL_E, BBMType.BALLOOM_E,
                BBMType.DAHL_E, BBMType.GHOST_E).getSize();
    }

    private void setCollisionBreak(BBMType type, String nameTypeBreakAnim) {
        onCollisionBegin(BBMType.FLAME, type, (f, t) -> {
            int cellX = (int)((t.getX() + 24) / SIZE_BLOCK);
            int cellY = (int)((t.getY() + 24) / SIZE_BLOCK);

            AStarGrid grid = geto("grid");
            grid.get(cellX, cellY).setState(CellState.WALKABLE);
            set("grid", grid);

            Entity bBreak = spawn(nameTypeBreakAnim, new SpawnData(t.getX(), t.getY()));
            t.removeFromWorld();
            getGameTimer().runOnceAfter(bBreak::removeFromWorld, Duration.seconds(1));
        });
    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
    }
}
