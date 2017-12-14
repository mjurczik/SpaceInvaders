/** Copyright by Marlin Jurczik **/

package red.sub.spaceinvaders.GameState;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * @author Marlin Jurczik
 */
public class LevelScreen extends ScreenAdapter
{
    public static final int GAME_HEIGHT = 256;
    public static final int GAME_WIDTH = 256;
    
    private SpriteBatch batch;
    private OrthographicCamera camera;
    
    private Texture shipTex;
    
    
    public LevelScreen(SpriteBatch batch)
    {
        this.batch = batch;
    }
    @Override
    public void show()
    {
        camera = new OrthographicCamera();
        //camera.setToOrtho(true);
        camera.setToOrtho(true, GAME_WIDTH, GAME_HEIGHT);
        
        shipTex = new Texture(Gdx.files.internal("textures/Ship.png"));
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        
        batch.setProjectionMatrix(camera.combined);
             
        batch.begin();
        batch.draw(shipTex, 10, 10);
        batch.end();
    }
    
    @Override
    public void dispose()
    {
        
    }
}
