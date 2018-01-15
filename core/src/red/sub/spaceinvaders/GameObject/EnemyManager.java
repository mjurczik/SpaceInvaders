/** Copyright by Marlin Jurczik **/

package red.sub.spaceinvaders.GameObject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
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
    
    private Enemy[][] enemies;
    private Array<EnemyBullet> enemyBullets;
    private Array<Explosion> explosions;
    private static final int E_COUNT_X = 10;
    private static final int E_COUNT_Y = 5;
    private float bulletProbability = 5;
    
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
    private final long updateEnemyBulletT = 100;
    
    private long lastEnemyBulletDrop = 0;
    private long updateLastEBulletDrop = 500;
    
    private boolean active;
    
    public EnemyManager()
    {
        active = true;
        explosions = new Array<Explosion>();
        enemyBullets = new Array<EnemyBullet>();
        loadTextures();
        initEnemies();
    }
    
    public void initEnemies()
    {
        enemies = new Enemy[E_COUNT_X][E_COUNT_Y];
        int enemySize = 15;
        int xOffset = (LevelScreen.GAME_WIDTH / 2) - (enemySize * E_COUNT_X) / 2;
        int yOffset = (LevelScreen.GAME_HEIGHT / 2 ) - (enemySize * E_COUNT_Y) /2;
       
        for(int x = 0; x < E_COUNT_X; x++)
        {
            enemies[x][0] = new Enemy(2, (x * enemySize) + xOffset + 2, yOffset, 8, 8);
            enemies[x][1] = new Enemy(0, (x * enemySize) + xOffset, enemySize + yOffset, 11, 8);
            enemies[x][2] = new Enemy(0, (x * enemySize) + xOffset, (enemySize * 2) + yOffset, 11, 8);
            enemies[x][3] = new Enemy(1, (x * enemySize) + xOffset, (enemySize * 3) + yOffset, 12, 8);
            enemies[x][4] = new Enemy(1, (x * enemySize) + xOffset, (enemySize * 4) + yOffset, 12, 8);
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
        if(active)
        {
            updateExplosions();
            updateEnemies();
            updateBullets();
            spawnBullets();
        }
        //checkProgress();
    }
    
    public boolean allEnemiesDead()
    {
        if(enemiesAlive() == 0)
            return true;
        else
            return false;
    }
    
    private int enemiesAlive()
    {
        int counter = 0;
        for(int x = 0; x < E_COUNT_X; x++)
            for(int y = 0; y < E_COUNT_Y; y++)
            {
                if(enemies[x][y].isActive())
                    counter++;
            }
        
        return counter;
    }
    
    private void updateExplosions()
    {
        Iterator<Explosion> iterExplosion = explosions.iterator();
        while(iterExplosion.hasNext())
        {
            Explosion e = iterExplosion.next();
            e.update();
            if(!e.isActive())
                iterExplosion.remove();
        }
    }
    
    private void updateEnemies()
    {
        updateEnemyT = (enemiesAlive() * UPDATE_TIME_MULTIPLIER) + UPDATE_TIME_OFFSET;
        //updateEnemyT = 100;
        
        if(TimeUtils.millis() - lastUpdateEnemy > updateEnemyT)
        {
            lastUpdateEnemy = TimeUtils.millis();
            for(int x = 0; x < E_COUNT_X; x++)
                for(int y = 0; y < E_COUNT_Y; y++)
                {
                    if(enemies[x][y].isActive())
                        enemies[x][y].update();
                }
            
            frameIdEnemy++;
            if(frameIdEnemy > 1)
                frameIdEnemy = 0;
        }
    }
    
    private void updateBullets()
    {
        Iterator<EnemyBullet> iterEnemyBullet = enemyBullets.iterator();
        while (iterEnemyBullet.hasNext())
        {
            EnemyBullet b = iterEnemyBullet.next();     
                b.update();
                if(!b.isActive())
                    iterEnemyBullet.remove();
        }
        
        if(TimeUtils.millis() - lastUpdateEnemyBullet > updateEnemyBulletT)
        {
            frameIdEnemyBullet++;
            if(frameIdEnemyBullet > 1)
                frameIdEnemyBullet = 0;
            lastUpdateEnemyBullet = TimeUtils.millis();
        }
    }
    
    private void spawnBullets()
    {
        Array<Enemy> tmp = new Array<Enemy>();
        if(TimeUtils.millis() - lastEnemyBulletDrop > updateLastEBulletDrop)
        {
            lastEnemyBulletDrop = TimeUtils.millis();
            
            for(int x = 0; x < E_COUNT_X; x++)
                for(int y = 0; y < E_COUNT_Y; y++)
                {
                    if(enemies[x][y].isActive())
                    {
                        if(y < E_COUNT_Y - 1 && !enemies[x][y + 1].isActive())
                            tmp.add(enemies[x][y]);
                        else if(y == E_COUNT_Y -1)
                            tmp.add(enemies[x][y]);        
                    }
                }
            
            Iterator<Enemy> iterTmp = tmp.iterator();
            while(iterTmp.hasNext())
            {
                Enemy tmpE = iterTmp.next();
                if(MathUtils.random(100) < bulletProbability)  
                    enemyBullets.add(new EnemyBullet(tmpE.getX() + tmpE.getWidth()/2 - EnemyBullet.WIDTH /2, tmpE.getY() + tmpE.getHeight()));
            }
        }
    }
    
    public void render(SpriteBatch batch)
    {        
        for(int x = 0; x < E_COUNT_X; x++)
            for(int y = 0; y < E_COUNT_Y; y++)
            {
                if(enemies[x][y].isActive())
                {
                    Enemy e = enemies[x][y];
                    switch(e.getType())
                    {
                        case 0: batch.draw(invader1Reg[0][frameIdEnemy], e.getX(), e.getY()); break;
                        case 1: batch.draw(invader2Reg[0][frameIdEnemy], e.getX(), e.getY()); break;
                        case 2: batch.draw(invader3Reg[0][frameIdEnemy], e.getX(), e.getY()); break;
                    }
                }
            }
        
        Iterator<Explosion> iterExplosion = explosions.iterator();
        while(iterExplosion.hasNext())
        {
            Explosion explosion = iterExplosion.next();
            batch.draw(explosionTex, explosion.getX(), explosion.getY());
        }
        
        Iterator<EnemyBullet> iterEnemyBullet = enemyBullets.iterator();
        while(iterEnemyBullet.hasNext())
        {
            EnemyBullet b = iterEnemyBullet.next();
            batch.draw(enemyBulletReg[0][frameIdEnemyBullet], b.getX(), b.getY());
        }
    }
    
    public void dispose()
    {
        invader1Tex.dispose();
        invader2Tex.dispose();
        invader3Tex.dispose();
        explosionTex.dispose();
    }
    
    public Enemy[][] getEnemies()
    {
        return enemies;
    }
    
    public Array<EnemyBullet> getEnemyBullets()
    {
        return enemyBullets;
    }
    
    public void setActive(boolean active)
    {
        this.active = active;
    }
    
    public void addExplosion(float x, float y)
    {
        explosions.add(new Explosion(x, y));
    }
}
