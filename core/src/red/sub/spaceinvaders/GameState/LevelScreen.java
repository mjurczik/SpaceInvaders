/** Copyright by Marlin Jurczik **/

package red.sub.spaceinvaders.GameState;

import red.sub.spaceinvaders.GameObject.Bullet;
import red.sub.spaceinvaders.GameObject.Enemy;
import red.sub.spaceinvaders.GameObject.EnemyBullet;
import red.sub.spaceinvaders.GameObject.EnemyManager;
import red.sub.spaceinvaders.GameObject.ShieldManager;
import red.sub.spaceinvaders.GameObject.Ship;
import red.sub.spaceinvaders.Listener.LevelKeyListener;
import red.sub.spaceinvaders.Tools.FontLoader;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import java.util.Iterator;
import red.sub.spaceinvaders.GameObject.StageManager;

/**
 * @author Marlin Jurczik
 */
public class LevelScreen extends ScreenAdapter
{
    public static final int GAME_HEIGHT = 224;
    public static final int GAME_WIDTH = 240;
    
    private SpriteBatch batch;
    private OrthographicCamera camera;
    
    private StageManager stageManager;
    
    private ShapeRenderer shapeRenderer;
 
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
        
        stageManager = new StageManager();
        
        shapeRenderer = new ShapeRenderer();
        
        Gdx.input.setInputProcessor(new LevelKeyListener(stageManager));
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        shapeRenderer.setProjectionMatrix(camera.combined);
        batch.setProjectionMatrix(camera.combined);
                
        stageManager.update();
        
        batch.begin();
        //FontLoader.scoreFont.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 150, Y_OFFSET);
        stageManager.render(batch);
        batch.end();
        
        //drawDebug();
    }
    
    /*
    private void drawDebug()
    {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        
        shapeRenderer.setColor(Color.RED);
        Iterator<Bullet> iter = ship.getBullets().iterator();
        while(iter.hasNext())
        {
            Bullet b = iter.next();
            shapeRenderer.rect(b.getRectangle().x, b.getRectangle().y, b.getRectangle().width, b.getRectangle().height);
        }
        
        for(int x = 0; x < enemyManager.getEnemies().length; x++)
            for(int y = 0; y < enemyManager.getEnemies()[0].length; y++)
            {
                Enemy e = enemyManager.getEnemies()[x][y];
                if(e.isActive())
                    shapeRenderer.rect(e.getRectangle().x, e.getRectangle().y, e.getRectangle().width, e.getRectangle().height);
            }
        
        Iterator<EnemyBullet> iterEnemyBullet = enemyManager.getEnemyBullets().iterator();
        while(iterEnemyBullet.hasNext())
        {
            EnemyBullet b = iterEnemyBullet.next();
            shapeRenderer.rect(b.getRectangle().x, b.getRectangle().y, b.getRectangle().width, b.getRectangle().height);
        }
        
        shapeRenderer.end();
    }*/
    
    @Override
    public void dispose()
    {
        stageManager.dispose();
    }
}
