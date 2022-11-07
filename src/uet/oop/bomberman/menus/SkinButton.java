package uet.oop.bomberman.menus;

import com.almasb.fxgl.dsl.FXGL;
import javafx.beans.binding.Bindings;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

//Tuong tu menu button, nma text chuyen mau khac ti
public class SkinButton extends Parent {
    Text text;

    public SkinButton(String name, Runnable action) {
        this(name, 30, action);
    }

    public SkinButton(String name, int size, Runnable action) {
        text = FXGL.getUIFactoryService().newText(name, Color.WHITE, size);
        text.setStrokeWidth(1.5);
        text.strokeProperty().bind(text.fillProperty());

        if (name == "DEFAULT") {
            text.fillProperty().bind(
                    Bindings.when(hoverProperty())
                            .then(Color.RED)
                            .otherwise(Color.WHITE)
            );
        }

        if (name == "SKIN 1") {
            text.fillProperty().bind(
                    Bindings.when(hoverProperty())
                            .then(Color.BLUE)
                            .otherwise(Color.WHITE)
            );
        }

        if (name == "SKIN 2") {
            text.fillProperty().bind(
                    Bindings.when(hoverProperty())
                            .then(Color.GREEN)
                            .otherwise(Color.WHITE)
            );
        }

        if (name == "SKIN 3") {
            text.fillProperty().bind(
                    Bindings.when(hoverProperty())
                            .then(Color.YELLOW)
                            .otherwise(Color.WHITE)
            );
        }

        setOnMouseClicked(e -> action.run());

        setPickOnBounds(true);

        getChildren().add(text);
    }
}
