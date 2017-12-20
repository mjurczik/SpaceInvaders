/** Copyright by Marlin Jurczik **/

package GameObject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import java.util.Iterator;
import red.sub.spaceinvaders.GameState.LevelScreen;

/**
 * @author Marlin Jurczik
 */
public class EnemyManager 
{
    private static final int UPDATE_TIME_MULTIPLIER = 20;
    private static final int UPDATE_TIME_OFFSET = 20;
    
    private Array<Enemy> enemies;
    private Array<Explosion> explosions;
    private static final int E_COUNT_X = 10;
    private static final int E_COUNT_Y = 5;
    
    private Texture invader1Tex;
    private Texture invader2Tex;
    private Texture invader3Tex;
    private Texture explosionTex;
    private Texture enemyBulletTex;
    
    private TextureRegion[][] invader1Reg;
    private TextureRegion[][] invader2Reg;
    private TextureRegion[][] invader3Reg;
    private TextureRegion[][] enemyBulletReg;
    
    private int frameIdEnemy = 0;
    private int frameIdEnemyBullet = 0;
    
    private long lastUpdateEnemy = 0;
    private long updateEnemyT = 1000;
    private long lastUpdateEnemyBullet = 0;
    private long updateEnemyBulletT = 100;
    
    public EnemyManager()
    {
        enemies = new Array<Enemy>();
        explosions = new Array<Explosion>();
        loadTextures();
        initEnemies();
    }
    
    private void initEnemies()
    {
        int enemySize = 15;
        int xOffset = (LevelScreen.GAME_WIDTH / 2) - (enemySize * E_COUNT_X) / 2;
        int yOffset = (LevelScreen.GAME_HEIGHT / 2 ) - (enemySize * E_COUNT_Y) /2;
        
        for(int i = 0; i < E_COUNT_X; i++)
        {
            enemies.add(new Enemy(2, (i * enemySize) + xOffset + 2, yOffset, 8,8));
            enemies.add(new Enemy(0, (i * enemySize) + xOffset, enemySize + yOffset, 11, 8));
            enemies.add(new Enemy(0, (i * enemySize) + xOffset, (enemySize * 2) + yOffset, 11, 8));
            enemies.add(new Enemy(1, (i * enemySize) + xOffset, (enemySize * 3) + yOffset, 12, 8));
            enemies.add(new Enemy(1, (i * enemySize) + xOffset, (enemySize * 4) + yOffset, 12, 8));
        }
    }
    
    private void loadTextures()
    {
        invader1Tex = new Texture(Gdx.files.internal("textures/Invader1.png"));
        invader2Tex = new Texture(Gdx.files.internal("textures/Invader2.png"));
        invader3Tex = new Texture(Gdx.files.internal("textures/Invader3.png"));
        explosionTex = new Texture(Gdx.files.internal("textures/Explosion.png"));
        enemyBulletTex = new Texture(Gdx.files.internal("textures/EnemyBullet.png"));
        
        
        invader1Reg = TextureRegion.split(invader1Tex, 11, 8);
        invader2Reg = TextureRegion.split(invader2Tex, 12, 8);
        invader3Reg = TextureRegion.split(invader3Tex, 8, 8);
        enemyBulletReg = TextureRegion.split(enemyBulletTex, EnemyBullet.WIDTH, EnemyBullet.HEIGHT);
    }
    
    public void update()
    {
        updateEnemyT = (enemies.size * UPDATE_TIME_MULTIPLIER) + UPDATE_TIME_OFFSET;
        
        Iterator<Explosion> iterExplosion = explosions.iterator();
        while(iterExplosion.hasNext())
        {
            Explosion e = iterExplosion.next();
            e.update();
            if(!e.isActive())
                iterExplosion.remove();
        }
        
        if(TimeUtils.millis() - lastUpdateEnemy > updateEnemyT)
        {
            lastUpdateEnemy = TimeUtils.millis();  
            Iterator<Enemy> iterEnemy = enemies.iterator();
            while(iterEnemy.hasNext())
            {
                Enemy e = iterEnemy.next();


                    e.update();
                    if(!e.isActive())
                        iterEnemy.remove();

                    frameIdEnemy++;
                    if(frameIdEnemy > 1)
                        frameIdEnemy = 0;
            }  
        }
        
        Iterator<Enemy> iterEnemy = enemies.iterator();
        while (iterEnemy.hasNext())
        {
            Enemy e = iterEnemy.next();     
            if(e.getBullet() != null)
            {
                e.getBullet().update();
                if(!e.getBullet().isActive())
                    e.removeBullet();
            }   
        }
        
        if(TimeUtils.millis() - lastUpdateEnemyBullet > updateEnemyBulletT)
        {
            frameIdEnemyBullet++;
            if(frameIdEnemyBullet > 1)
                frameIdEnemyBullet = 0;
            lastUpdateEnemyBullet = TimeUtils.millis();
        }
        
    }
    
    public void render(SpriteBatch batch)
    {
        Iterator<Enemy> iterEnemy = enemies.iterator();
        while(iterEnemy.hasNext())
        {
            Enemy e = iterEnemy.next();
            switch(e.getType())
            {
                case 0: batch.draw(invader1Reg[0][frameIdEnemy], e.getX(), e.getY()); break;
                case 1: batch.draw(invader2Reg[0][frameIdEnemy], e.getX(), e.getY()); break;
                case 2: batch.draw(invader3Reg[0][frameIdEnemy], e.getX(), e.getY()); break;
            }
            if(e.getBullet() != null)
                batch.draw(enemyBulletReg[0][frameIdEnemyBullet], e.getBullet().getX(), e.getBullet().getY());
        }
        
        Iterator<Explosion> iterExplosion = explosions.iterator();
        while(iterExplosion.hasNext())
        {
            Explosion explosion = iterExplosion.next();
            batch.draw(explosionTex, explosion.getX(), explosion.getY());
        }
    }
    
    public void dispose()
    {
        invader1Tex.dispose();
        invader2Tex.dispose();
        invader3Tex.dispose();
        explosionTex.dispose();
    }
    
    public Array<Enemy> getEnemies()
    {
        return enemies;
    }
    
    public void addExplosion(float x, float y)
    {
        explosions.add(new Explosion(x, y));
    }
}
