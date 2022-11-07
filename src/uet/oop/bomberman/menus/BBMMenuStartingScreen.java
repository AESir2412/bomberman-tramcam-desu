package uet.oop.bomberman.menus;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.input.view.KeyView;
import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static com.almasb.fxgl.dsl.FXGL.*;
import static javafx.scene.input.KeyCode.*;
import static uet.oop.bomberman.constants.Constants.*;

/**
 * Main menu, displayed at the start of the game.
 */
public class BBMMenuStartingScreen extends FXGLMenu {
    public BBMMenuStartingScreen() {
        super(MenuType.MAIN_MENU);
        displayBackground();
        /*displayTitle();*/
        displayOptionsBox();
    }

    private void displayBackground() {
        ImageView bg = new ImageView();
        bg.setImage(new Image("assets/textures/titleScreen.png"));
        bg.setX(0);
        bg.setY(0);
        getContentRoot().getChildren().add(bg);
    }

    private void displayOptionsBox() {
        var buttonTextSize = 40;

        // UI Button
        var menuBox = new VBox(
                new MenuButton("NEW GAME", buttonTextSize, this::fireNewGame),
                new MenuButton("HOW TO PLAY", buttonTextSize, this::howToPlay),
                new MenuButton("SKIN", buttonTextSize, this::skinChoose),
                new MenuButton("EXIT", buttonTextSize, this::fireExit)
        );

        var offsetCenterX = buttonTextSize * 2.5 + 250;
        var offsetCenterY = buttonTextSize * 2 - 20;

        // set pos menu button
        menuBox.setAlignment(Pos.BOTTOM_CENTER);
        menuBox.setTranslateX(getAppWidth() / 2.0 - offsetCenterX);
        menuBox.setTranslateY(getAppHeight() / 2.0 + offsetCenterY);
        menuBox.setSpacing(20);
        getContentRoot().getChildren().addAll(menuBox);
    }

    private void howToPlay() {
        var htp = new GridPane();
        htp.setVgap(10);
        htp.setHgap(35);
        htp.addRow(0, getUIFactoryService().newText("CONTROL"),
                new HBox(4, new KeyView(W), new KeyView(S), new KeyView(A), new KeyView(D)));
        htp.addRow(1, getUIFactoryService().newText("PLACING BOMB"),
                new KeyView(SPACE));
        htp.addRow(2, getUIFactoryService().newText("OPEN GAME MENU"),
                new KeyView(ESCAPE));

        FXGL.getDialogService().showBox("HOW TO PLAY", htp, getUIFactoryService().newButton("OK"));
    }

    private void skinChoose() {
        var buttonTextSize = 30;

        // UI Button
        var menuskinBox = new VBox(
                new SkinButton("DEFAULT", buttonTextSize, this::defaultSkin),
                new SkinButton("SKIN 1", buttonTextSize, this::skin1),
                new SkinButton("SKIN 2", buttonTextSize, this::skin2),
                new SkinButton("SKIN 3", buttonTextSize, this::skin3)
        );

        // set pos menu button
        menuskinBox.setAlignment(Pos.BASELINE_CENTER);
        menuskinBox.setTranslateX(0);
        menuskinBox.setTranslateY(-40);
        menuskinBox.setSpacing(30);

        FXGL.getDialogService().showBox("SKIN", menuskinBox, getUIFactoryService().newButton("OK"));
    }

    private void defaultSkin () {
        defaultSkinChosen = true;
        skin1Chosen = false;
        skin2Chosen = false;
        skin3Chosen = false;
        SkinReselection.skinReselect();
        if (defaultSkinChosen) {
            showMessage("Default Skin Chosen!");
        }
    }

    private void skin1 () {
        defaultSkinChosen = false;
        skin1Chosen = true;
        skin2Chosen = false;
        skin3Chosen = false;
        SkinReselection.skinReselect();
        if (skin1Chosen) {
            showMessage("Skin 1 Chosen!");
        }
    }

    private void skin2 () {
        defaultSkinChosen = false;
        skin1Chosen = false;
        skin2Chosen = true;
        skin3Chosen = false;
        SkinReselection.skinReselect();
        if (skin2Chosen) {
            showMessage("Skin 2 Chosen!");
        }
    }

    private void skin3 () {
        defaultSkinChosen = false;
        skin1Chosen = false;
        skin2Chosen = false;
        skin3Chosen = true;
        SkinReselection.skinReselect();
        if (skin3Chosen) {
            showMessage("Skin 3 Chosen!");
        }
    }
}