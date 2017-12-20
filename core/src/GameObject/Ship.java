/** Copyright by Marlin Jurczik **/

package GameObject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import java.util.Iterator;
import red.sub.spaceinvaders.GameState.LevelScreen;

/**
 * @author Marlin Jurczik
 */
public class Ship 
{
    private Texture shipTex;
    private Texture bulletTex;
    private Array<Bullet> bullets;
    
    private float x;
    private float y;
    private int speed = 100;
    private long shootCooldown = 500;
    private long lastShotTime= 0;
    
    private boolean moveRight = false;
    private boolean moveLeft = false;
    private boolean shoot = false;
    
    public Ship()
    {
        bullets = new Array<Bullet>();
        shipTex = new Texture(Gdx.files.internal("textures/Ship.png"));
        
        Pixmap pixmap = new Pixmap(Bullet.WIDTH, Bullet.HEIGHT, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fillRectangle(0, 0, Bullet.WIDTH, Bullet.HEIGHT);
        bulletTex = new Texture(pixmap);
        pixmap.dispose();
        
        x = LevelScreen.GAME_WIDTH / 2 - shipTex.getWidth() / 2;
        y = LevelScreen.GAME_HEIGHT - shipTex.getHeight() - 10;
    }
    
    public void update()
    {
        if(moveLeft && x >= 0)
            x -= speed * Gdx.graphics.getDeltaTime();
        
        if(x < 0)
            x = 0;
        
        if(moveRight && x <= LevelScreen.GAME_WIDTH - shipTex.getWidth())
            x += speed * Gdx.graphics.getDeltaTime();
        
        if(x > LevelScreen.GAME_WIDTH - shipTex.getWidth())
            x = LevelScreen.GAME_WIDTH - shipTex.getWidth();
        
        Iterator<Bullet> iter = bullets.iterator();
        while(iter.hasNext())
        {
            Bullet b = iter.next();
            b.update();
            
            if(!b.isActive())
                iter.remove();
        }
        
        if(shoot && TimeUtils.millis() - lastShotTime > shootCooldown)
        {
            bullets.add(new Bullet(x + shipTex.getWidth()/2, y - bulletTex.getHeight()));
            lastShotTime = TimeUtils.millis();
        }
    }
    
    public void render(SpriteBatch batch)
    {
        batch.draw(shipTex, x,y);
        
        Iterator<Bullet> iter = bullets.iterator();
        while(iter.hasNext())
        {
            Bullet b = iter.next();
            batch.draw(bulletTex, b.getX(), b.getY());
        }
            
    }

    public void setMoveRight(boolean moveRight)
    {
        if(moveLeft)
            moveLeft = false;
        this.moveRight = moveRight;
    }

    public void setMoveLeft(boolean moveLeft)
    {
        if(moveRight)
            moveRight = false;
        this.moveLeft = moveLeft;
    }

    public void setShoot(boolean shoot)
    {
        this.shoot = shoot;
    }
    
    public void dispose()
    {
        shipTex.dispose();
        bulletTex.dispose();
    }
    
    public Array<Bullet> getBullets()
    {
        return bullets;
    }
}
