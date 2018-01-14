/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package red.sub.spaceinvaders.GameObject;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import java.util.Iterator;
import static red.sub.spaceinvaders.GameState.LevelScreen.GAME_HEIGHT;
import red.sub.spaceinvaders.Tools.FontLoader;

/**
 *
 * @author tucura
 */
public class StageManager 
{
    private static final int Y_OFFSET = 2;
    private int currentStage;
    private int lifes = 3;
    private int score = 0;
    
    private Ship ship;
    private EnemyManager enemyManager;
    private ShieldManager shieldManager;
    
    public StageManager()
    {
        currentStage = 1;
        
        ship = new Ship();
        enemyManager = new EnemyManager();
        shieldManager = new ShieldManager(enemyManager, ship);
    }
    
    public void update()
    {
        checkCollision();
        checkGameStatus();

        ship.update();
        enemyManager.update();
        shieldManager.update();
        enemyManager.setActive(ship.isActive());
    }
    
    public void render(SpriteBatch batch)
    {
        FontLoader.scoreFont.draw(batch, "Score: " + score, 2, Y_OFFSET);
        drawLifes(batch);
        shieldManager.render(batch);
        ship.render(batch);
        enemyManager.render(batch);
    }
    
    private void drawLifes(SpriteBatch batch)
    {
        batch.setColor(Color.RED);
        //int width_offset = GAME_WIDTH / 2 - ((ship.getShipTexture().getWidth() * lifes) / 2);
        int width_offset = 5;
        int life_offset = 5;
        for(int i = 0; i < lifes; i++)
        {
            batch.draw(ship.getShipTexture(), width_offset + ship.getShipTexture().getWidth() * i + (life_offset* i), GAME_HEIGHT - ship.getShipTexture().getHeight() - Y_OFFSET);
        }
        
        batch.setColor(Color.WHITE);
    }
    
    private void checkGameStatus()
    {
        if(enemyManager.allEnemiesDead())
        {
            //load next level
        }
        else if(lifes == 0 || isEnemyOutBounds())
        {
            //game over
        }
    }
        
    private boolean isEnemyOutBounds()
    {
        for(int x = 0; x < enemyManager.getEnemies().length; x++)
            for(int y = 0; y < enemyManager.getEnemies()[0].length; y++)
            {
                Enemy e = enemyManager.getEnemies()[x][y];
                if(e.getY() + e.getHeight() > ship.getY())
                    return true;
            }
        return false;
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
        
        Iterator<EnemyBullet> iterEnemyBullet = enemyManager.getEnemyBullets().iterator();
        
        while(iterEnemyBullet.hasNext())
        {
            EnemyBullet e = iterEnemyBullet.next();
            if(e.isActive() && e.getRectangle().overlaps(ship.getRectangle()))
            {
                e.setActive(false);
                ship.setActive(false);
                lifes--;
            }
        }
    }
    
    public Ship getShip()
    {
        return ship;
    }
    
    public void dispose()
    {
        ship.dispose();
        shieldManager.dispose();
        enemyManager.dispose();
    }
}
