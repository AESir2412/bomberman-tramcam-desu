package uet.oop.bomberman.menus;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import javafx.geometry.Pos;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import static com.almasb.fxgl.dsl.FXGL.*;
import static uet.oop.bomberman.constants.Constants.isSoundEnabled;

/**
 * In game menu.
 * Press esc key to open this menu.
 */
public class BBMMenuInGame extends FXGLMenu {
    public BBMMenuInGame() {
        super(MenuType.GAME_MENU);
        Shape shape = new Rectangle(1080, 720, Color.GREY);
        shape.setOpacity(0.5);
        getContentRoot().getChildren().add(shape);

        // UI background
        ImageView iv1 = new ImageView();
        iv1.setImage(new Image("assets/textures/background_demo_2.png"));
        iv1.setX(1080 / 2.0 - 520 / 2.0 - 85);
        iv1.setY(50);
     /*   iv1.setEffect(new DropShadow(5, 3.5, 3.5, Color.WHITE));
        iv1.setEffect(new Lighting());*/
        getContentRoot().getChildren().add(iv1);

        // UI title
        var title = getUIFactoryService().newText("PAUSE", Color.WHITE, 30);
        title.setStroke(Color.WHITE);
        title.setStrokeWidth(1.5);
     /*   title.setEffect(new Bloom(0.6));*/
        centerTextBind(title, getAppWidth() / 2.0 + 8, 250);


        // UI version
      /*  var version = getUIFactoryService().newText(getSettings().getVersion(), Color.WHITE, 20);
        centerTextBind(version, getAppWidth() / 2.0, 280);*/
        getContentRoot().getChildren().addAll(title);

        // UI Button
        var menuBox = new VBox(
                2,
                new MenuButton("Resume", this::fireResume),
                new MenuButton("Menu", this::fireExitToMainMenu),
                new MenuButton("Sounds", this::enableSound),
                new MenuButton("Exit", this::fireExit)
        );

        // set pos menu button
        menuBox.setAlignment(Pos.BASELINE_CENTER);
        menuBox.setTranslateX(getAppWidth() / 2.0 - 60);
        menuBox.setTranslateY(getAppHeight() / 2.0 + 10);
        menuBox.setSpacing(10);
        getContentRoot().getChildren().addAll(menuBox);
    }

    private void enableSound() {
        isSoundEnabled = !isSoundEnabled;
        if (isSoundEnabled) {
            getSettings().setGlobalMusicVolume(0.4);
            getSettings().setGlobalSoundVolume(0.4);
            showMessage("Sound enabled!");
        } else {
            getSettings().setGlobalMusicVolume(0);
            getSettings().setGlobalSoundVolume(0);
            showMessage("Sound disabled!");
        }
    }
}