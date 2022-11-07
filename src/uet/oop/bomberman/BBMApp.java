package uet.oop.bomberman;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.app.scene.Viewport;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.pathfinding.CellState;
import com.almasb.fxgl.pathfinding.astar.AStarGrid;
import com.almasb.fxgl.physics.PhysicsWorld;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import uet.oop.bomberman.components.Player;
import uet.oop.bomberman.menus.BBMMenuInGame;
import uet.oop.bomberman.menus.BBMMenuStartingScreen;
import uet.oop.bomberman.ui.BBMHUD;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getPhysicsWorld;
import static uet.oop.bomberman.constants.Constants.*;

public class BBMApp extends GameApplication {
    private static Entity getPlayer() {
        return FXGL.getGameWorld().getSingleton(BBMType.PLAYER);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setHeight(HEIGHT);
        settings.setWidth(WIDTH);
        settings.setTitle("Bomberman");
        settings.setVersion("Tram Cam Desu");
        settings.setIntroEnabled(false);
        settings.setMainMenuEnabled(true);
        settings.setGameMenuEnabled(true);
        settings.setPreserveResizeRatio(true);
        settings.setManualResizeEnabled(true);
        settings.setDeveloperMenuEnabled(true);
        settings.setFontUI(FONT);
        settings.setSceneFactory(new SceneFactory() {
            @NotNull
            @Override
            public FXGLMenu newMainMenu() {
                return new BBMMenuStartingScreen();
            }

            @NotNull
            @Override
            public FXGLMenu newGameMenu() {
                return new BBMMenuInGame();
            }
        });
    }

    @Override
    protected void initGame() {
        FXGL.getGameWorld().addEntityFactory(new BBMFactory());
        loadNextLevel();
        FXGL.spawn("background");
        startCountdown();
        getWorldProperties().<Integer>addListener("time", this::killPlayerWhenTimeEnd);
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("bomb", 1);
        vars.put("flame", 1);
        vars.put("score", 0);
        vars.put("speed", SPEED);
        vars.put("time", TIME_PER_LEVEL);
        vars.put("level", START_LEVEL);
        vars.put("immortality", false);
        vars.put("numOfEnemy", 10);
        vars.put("life", 3);
    }

    @Override
    protected void onPreInit() {
        if (isSoundEnabled) {
            getSettings().setGlobalMusicVolume(0.5);
            getSettings().setGlobalSoundVolume(0.4);
        } else {
            getSettings().setGlobalMusicVolume(0);
            getSettings().setGlobalSoundVolume(0);
        }
        loopBGM("MenuMusic.mp3");
    }

    @Override
    protected void initUI() {
        var hud = new BBMHUD();
        getGameTimer().runOnceAfter(() -> FXGL.addUINode(hud.getHUD(), 0, 0), Duration.seconds(3));

    }

    //Ham update khi het thoi gian, va khi co requestNewGame
    @Override
    protected void onUpdate(double tpf) {

        if (geti("time") == 0) {
            showMessage("Game Over!!!", () -> getGameController().gotoMainMenu());
        }

        if (requestNewGame) {
            requestNewGame = false;
            getPlayer().getComponent(Player.class).die();
            getGameTimer().runOnceAfter(() -> getGameScene().getViewport().fade(() -> {
                if (geti("life") <= 0) {
                    play("LoseGame.wav");
                    showMessage("Game Over!!!",
                            () -> getGameController().gotoMainMenu());
                }
                setLevel();
                set("immortality", false);
            }), Duration.seconds(0.5));
        }
    }

    //Nhan input va chay o ham Player
    @Override
    protected void initInput() {
        FXGL.getInput().addAction(new UserAction("Move Up") {
            @Override
            protected void onAction() {
                getPlayer().getComponent(Player.class).up();
            }

            @Override
            protected void onActionEnd() {
                getPlayer().getComponent(Player.class).stop();
            }
        }, KeyCode.W);

        FXGL.getInput().addAction(new UserAction("Move Down") {
            @Override
            protected void onAction() {
                getPlayer().getComponent(Player.class).down();
            }

            @Override
            protected void onActionEnd() {
                getPlayer().getComponent(Player.class).stop();
            }
        }, KeyCode.S);

        FXGL.getInput().addAction(new UserAction("Move Left") {
            @Override
            protected void onAction() {
                getPlayer().getComponent(Player.class).left();
            }

            @Override
            protected void onActionEnd() {
                getPlayer().getComponent(Player.class).stop();
            }
        }, KeyCode.A);

        FXGL.getInput().addAction(new UserAction("Move Right") {
            @Override
            protected void onAction() {
                getPlayer().getComponent(Player.class).right();
            }

            @Override
            protected void onActionEnd() {
                getPlayer().getComponent(Player.class).stop();
            }
        }, KeyCode.D);

        FXGL.getInput().addAction(new UserAction("Place Bomb") {
            @Override
            protected void onActionBegin() {
                getPlayer().getComponent(Player.class).placeBomb();
            }
        }, KeyCode.SPACE);
    }

    //Check va cham
    @Override
    protected void initPhysics() {
        PhysicsWorld physics = getPhysicsWorld();
        physics.setGravity(0, 0);

        //Check va cham voi Enemy
        onCollisionBegin(BBMType.PLAYER, BBMType.PORTAL, this::endLevel);
        onCollisionBegin(BBMType.PLAYER, BBMType.FLAME, (p, f) -> playerKilled());
        onCollisionBegin(BBMType.PLAYER, BBMType.BALLOOM_E, (p, b) -> playerKilled());
        onCollisionBegin(BBMType.PLAYER, BBMType.GHOST_E, (p, b) -> playerKilled());
        onCollisionBegin(BBMType.PLAYER, BBMType.DAHL_E, (p, dh) -> playerKilled());
        onCollisionBegin(BBMType.PLAYER, BBMType.ONEAL_E, (p, o) -> playerKilled());
        //Cac va cham khac o components
    }

    //Check xem da hoan thien level chua
    private void endLevel(Entity player, Entity portal) {
        if (geti("numOfEnemy") > 0) return;
        play("next_level.wav");
        getPlayer().getComponent(Player.class).setExploreCancel(true);
        var timer = FXGL.getGameTimer();
        timer.runOnceAfter(this::fadeBetweenLevel, Duration.seconds(1));
    }

    //Transition fade before loadNextLevel
    private void fadeBetweenLevel() {
        var gameScene = FXGL.getGameScene();
        var viewPort = gameScene.getViewport();
        viewPort.fade(this::loadNextLevel);
    }

    //Khi player chet hoac het thoi gian
    private void playerKilled() {
        //Neu dang bat tu thi khong chay ham nay
        if (!getb("immortality")) {
            set("immortality", true);
            play("BombermanDead.wav");
            if (geti("life") > 0) inc("life", -1);
            set("score", 0);
            getPlayer().getComponent(Player.class).setExploreCancel(true);
            requestNewGame = true;
        }
    }

    //Load level moi (man hinh den chua chu level_
    private void loadNextLevel() {
        if (FXGL.geti("level") >= MAX_LEVEL) {
            showMessage("You Win! Congratulations!", () -> getGameController().gotoMainMenu());
        } else {
            getSettings().setGlobalMusicVolume(0);
            getInput().setProcessInput(false);

            play("GameStarted.wav");
            inc("level", +1);
            /*AnchorPane pane = creStartStage();*/
            ImageView trans = new ImageView();
            trans.setImage(new Image("assets/textures/level" + FXGL.geti("level") + ".png"));
           /* var trans = FXGL.getAssetLoader().loadTexture("assets/textures/level" + FXGL.geti("level") + ".png");
            trans.setTranslateX(0);
            trans.setTranslateY(0);*/
            FXGL.getGameScene().addUINode(trans);

            /*FXGL.addUINode(pane);*/
            getGameTimer().runOnceAfter(() -> {
                FXGL.getGameScene().removeUINode(trans);
                getSettings().setGlobalMusicVolume(0.05);
                getInput().setProcessInput(true);
                setLevel();
            }, Duration.seconds(3));
        }
    }

    //Stage x luc chuyen giua cac level
    /*private AnchorPane creStartStage() {*/
      /*  AnchorPane pane = new AnchorPane();*/
        /*Shape shape = new Rectangle(1080, 720, Color.BLACK);

        var text = FXGL.getUIFactoryService().newText("STAGE " + geti("level"), Color.WHITE, 40);
        text.setTranslateX((WIDTH >> 1) - 80);
        text.setTranslateY((HEIGHT >> 1) - 20);
        pane.getChildren().addAll(shape, text);

        return pane;*/
/*    }*/

    private ImageView transitionBetweenLevel() {
        ImageView trans = new ImageView();
        trans.setImage(new Image("assest/level" + FXGL.geti("level") + ".png"));
        return trans;
    }

    //set cho level
    private void setLevel() {
        FXGL.setLevelFromMap("level" + FXGL.geti("level") + ".tmx");

        Viewport viewport = getGameScene().getViewport();
        viewport.setBounds(0, 0, GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT);
        viewport.bindToEntity(
                getPlayer(),
                FXGL.getAppWidth() / 2.0f,
                FXGL.getAppHeight() / 2.0f);
        viewport.setLazy(true);
        set("time", TIME_PER_LEVEL);
        set("bomb", 1);
        set("flame", 1);
        set("numOfEnemy", 10);
        setGridForAi();
    }

    private void setGridForAi() {
        AStarGrid grid = AStarGrid.fromWorld(getGameWorld(), 31, 15,
                SIZE_BLOCK, SIZE_BLOCK, (type) -> {
                    if (type == BBMType.BRICK
                            || type == BBMType.WALL
                            || type == BBMType.GRASS
                            || type == BBMType.AROUND_WALL) {
                        return CellState.NOT_WALKABLE;
                    } else {
                        return CellState.WALKABLE;
                    }
                });

        AStarGrid _grid = AStarGrid.fromWorld(getGameWorld(), 31, 15,
                SIZE_BLOCK, SIZE_BLOCK, (type) -> {
                    if (type == BBMType.AROUND_WALL || type == BBMType.WALL) {
                        return CellState.NOT_WALKABLE;
                    } else {
                        return CellState.WALKABLE;
                    }
                });

        set("grid", grid);
        set("_grid", _grid);
    }

    private void startCountdown() {
        run(() -> inc("time", -1), Duration.seconds(1));
    }

    private void killPlayerWhenTimeEnd(int previous, int current) {
        if (current == 0) {
            playerKilled();
        }
    }

}

