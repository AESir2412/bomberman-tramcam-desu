package uet.oop.bomberman.gameMenu;

import uet.oop.bomberman.control.KeyCheck;
import uet.oop.bomberman.control.Timer;

public class GameMenu {

    public static enum GAME_STATE {
        IN_MENU, IN_GAME, IN_PAUSE, END, IN_END_STATE;
    }

    //Tham so check game state
    public static GAME_STATE preGameState;
    public static GAME_STATE gameState;

    private static KeyCheck keyCheck;

    private long delayInput = 0;

    private final int SINGLE_GAME = 0;
    private final int EXIT = 1;


    /**Constructor cho gameState**/
    public GAME_STATE getGameState() {
        return gameState;
    }

    public void setGameState(GAME_STATE state) {
        gameState = state;
    }
    /***/


    /**Ham khoi tao menu ban dau gom cac nut chon*/
    public GameMenu(KeyCheck k){
        switch(gameState) {
            case IN_MENU:
                long now = Timer.now();
                if (now - delayInput > Timer.TIME_FOR_SINGLE_INPUT) {
                    delayInput = now;
                }
                break;
            case IN_GAME:
        }
    }

    public void update() {

    }
}
