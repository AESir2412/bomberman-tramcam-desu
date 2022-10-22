package uet.oop.bomberman;

//new map maker feature

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Grass;
import uet.oop.bomberman.entities.Wall;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

import uet.oop.bomberman.control.Timer;
import uet.oop.bomberman.gameMenu.GameMenu;
import uet.oop.bomberman.graphics.Graphics;

public class BombermanGame extends Application {
    
    public static final int WIDTH = 20;
    public static final int HEIGHT = 15;
    
    private GraphicsContext gc;
    private Canvas canvas;
    private List<Entity> entities = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();

    private Timer timer;
    private static GameMenu menu;
    private Graphics graphics;
    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root);
        timer = new Timer(this);

        // Them scene vao stage
        stage.setScene(scene);
        stage.show();

        //load map old
        createMap();

        Entity bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());
        entities.add(bomberman);
    }

    public void loop() {
        update();
        render();
    }

    public void createMap() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                Entity object;
                if (j == 0 || j == HEIGHT - 1 || i == 0 || i == WIDTH - 1) {
                    object = new Wall(i, j, Sprite.wall.getFxImage());
                }
                else {
                    object = new Grass(i, j, Sprite.grass.getFxImage());
                }
                stillObjects.add(object);
            }
        }
    }

    public void update() {
        switch (menu.getGameState()) {
            case IN_MENU:
            case IN_END_STATE:
                menu.update();
                break;
            case END:
                System.exit(0);
                break;
            default:
                throw new IllegalArgumentException("Invalid game state");
        }
    }

    public void render() {
        switch (menu.getGameState()) {
            case IN_MENU:
            case IN_END_STATE:
                graphics.renderMenu(menu);
                break;
            case END:
                break;
            default:
                throw new IllegalArgumentException("Invalid game state");
        }
    }
}
