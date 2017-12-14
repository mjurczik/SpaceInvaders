package red.sub.spaceinvaders;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import red.sub.spaceinvaders.GameState.LevelScreen;

public class SpaceInvaders extends Game {
	SpriteBatch batch;
	Texture img;

    @Override
    public void create()
    {
        batch = new SpriteBatch();
        
        LevelScreen lvlScr = new LevelScreen(batch);
        this.setScreen(lvlScr);
    }
	
}
