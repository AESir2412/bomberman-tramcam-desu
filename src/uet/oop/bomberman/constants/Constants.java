package uet.oop.bomberman.constants;

/**
 * Game configuration, this is not recommended. But there is no time for refactoring.
 */
public class Constants {
    public static final int WIDTH = 1080; //kich thuoc windows
    public static final int HEIGHT = 720;

    public static final String TITLE = "BOMBERMAN";
    public static final String VERSION = "1.0";

    public static final String FONT = "Retro Gaming.ttf";

    public static final int TIME_PER_LEVEL = 300;
    public static final int START_LEVEL = 0;

    public static boolean isSoundEnabled = true;
    public static boolean requestNewGame = false;
    public static final int SIZE_BLOCK = 48;
    public static final int GAME_WORLD_WIDTH = 1488; //kich thuoc map real
    public static final int GAME_WORLD_HEIGHT = 720;
    public static final int SPEED = 200;
    public static final int ENEMY_SPEED = 70;
    public static final int SCORE_ITEM = 20;
    public static final int MAX_LEVEL = 3;

    //default skin chosen nen khoi tao trc
    public static boolean defaultSkinChosen = true;
    public static boolean skin1Chosen;
    public static boolean skin2Chosen;
    public static boolean skin3Chosen;

    //default skin chosen nen khoi tao trc
    public static String spriteSheetChosen = "sprites.png";
}
