package red.sub.spaceinvaders;

import Tools.FontLoader;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import red.sub.spaceinvaders.GameState.LevelScreen;

public class SpaceInvaders extends Game 
{
    SpriteBatch batch;
    Texture img;

    @Override
    public void create()
    {
        batch = new SpriteBatch();
        FontLoader.loadFonts();
        
        LevelScreen lvlScr = new LevelScreen(batch);
        this.setScreen(lvlScr);
        
    }	
}
