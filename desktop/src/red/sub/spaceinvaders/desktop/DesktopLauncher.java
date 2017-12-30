package red.sub.spaceinvaders.desktop;

import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import red.sub.spaceinvaders.GameState.LevelScreen;
import red.sub.spaceinvaders.SpaceInvaders;

public class DesktopLauncher 
{
    public static final String GAME_NAME = "ASIG";    
    
    public static void main (String[] arg) 
    {
        int[] displaySize = getResulution();
        
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = displaySize[0];
        config.height = displaySize[1];
        config.fullscreen = false;
        config.resizable = false;
        config.x = -1;
        config.y = -1;
        config.title = GAME_NAME;
        new LwjglApplication(new SpaceInvaders(), config);
    }

    /**
     * Set Resulution depending on screen height
     */
    private static int[] getResulution()
    {
        int[] size = new int[2];
        
        DisplayMode display = LwjglApplicationConfiguration.getDesktopDisplayMode();
        int multiplier = display.height / LevelScreen.GAME_HEIGHT;
        size[0] = LevelScreen.GAME_WIDTH * multiplier;
        size[1] = LevelScreen.GAME_HEIGHT * multiplier;
        
        return size;
    }
                
}
