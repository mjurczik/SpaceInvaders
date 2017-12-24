/** Copyright by Marlin Jurczik **/

package red.sub.spaceinvaders.GameState;

import GameObject.Bullet;
import GameObject.Enemy;
import GameObject.EnemyBullet;
import GameObject.EnemyManager;
import GameObject.Ship;
import Listener.LevelKeyListener;
import Tools.FontLoader;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import java.util.Iterator;

/**
 * @author Marlin Jurczik
 */
public class LevelScreen extends ScreenAdapter
{
    public static final int GAME_HEIGHT = 224;
    public static final int GAME_WIDTH = 240;
    
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Ship ship;
    private EnemyManager enemyManager;
    
    private ShapeRenderer shapeRenderer;
    
    private int score;
 
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
        
        ship = new Ship();
        enemyManager = new EnemyManager();
        
        shapeRenderer = new ShapeRenderer();
        
        Gdx.input.setInputProcessor(new LevelKeyListener(this));
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        shapeRenderer.setProjectionMatrix(camera.combined);
        batch.setProjectionMatrix(camera.combined);
        
        ship.update();
        enemyManager.update();
        checkCollision();
        
             
        batch.begin();
        FontLoader.scoreFont.draw(batch, "Score: " + score, 2, 2);
        FontLoader.scoreFont.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 150, 2);
        ship.render(batch);
        enemyManager.render(batch);
        batch.end();
        
        drawDebug();
    }
    
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
    }
    
    private void checkCollision()
    {
        Array<Bullet> bullets = ship.getBullets();
        Enemy[][] enemies = enemyManager.getEnemies();
        
        Iterator<Bullet> iterBullet = bullets.iterator();
        
        while(iterBullet.hasNext())
        {
            Bullet b = iterBullet.next();
            
            Iterator<EnemyBullet> iterEnemyBullet = enemyManager.getEnemyBullets().iterator();
            while(iterEnemyBullet.hasNext())
            {
                EnemyBullet eb = iterEnemyBullet.next();
                if(eb.getRectangle().overlaps(b.getRectangle()))
                {
                    b.setActive(false);
                    eb.setActive(false);
                }
            }
            
            if(b.isActive())
            {
                for(int x = 0; x < enemies.length; x++)
                    for(int y = 0; y < enemies[0].length; y++)
                    {
                        Enemy e = enemies[x][y];
                        if(e.isActive() && e.getRectangle().overlaps(b.getRectangle()))
                        {
                            b.setActive(false);
                            e.setActive(false);
                            enemyManager.addExplosion(e.getX(), e.getY());

                            switch(e.getType())
                            {
                                case 0: score += 20; break;
                                case 1: score += 10; break;
                                case 2: score += 30; break;
                            }
                        }
                    }
            }
        }
    }
    
    public Ship getShip()
    {
        return ship;
    }
    
    @Override
    public void dispose()
    {
        ship.dispose();
    }
}
