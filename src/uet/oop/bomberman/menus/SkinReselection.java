package uet.oop.bomberman.menus;

import static uet.oop.bomberman.constants.Constants.*;
import static uet.oop.bomberman.constants.Constants.skin3Chosen;

public class SkinReselection {
    public static void skinReselect () {
        if (defaultSkinChosen) {
            spriteSheetChosen = "sprites.png";
        }
        if (skin1Chosen) {
            spriteSheetChosen = "sprites1.png";
        }
        if (skin2Chosen) {
            spriteSheetChosen = "sprites2.png";
        }
        if (skin3Chosen) {
            spriteSheetChosen = "sprites3.png";
        }
    }
}
