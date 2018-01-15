/** Copyright by Marlin Jurczik **/

package red.sub.spaceinvaders.GameObject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
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
    private Texture explosionTex;
    private TextureRegion[][] explosionReg;
    private Array<Bullet> bullets;
    private Rectangle collisionRec;
    
    private float x;
    private float y;
    private int speed = 100;
    private long shootCooldown = 500;
    private long lastShotTime= 0;
    private boolean active;
    
    private boolean moveRight = false;
    private boolean moveLeft = false;
    private boolean shoot = false;
    
    private long shipDeathTime = 2000;
    private long lastShipDeathTime;
    
    private int frameIdExplosion = 0;
    private long explosionTime = 200;
    private long lastExplostionTime;
    
    
    public Ship()
    {
        active = true;
        bullets = new Array<Bullet>();
        loadTextures();
        
        x = LevelScreen.GAME_WIDTH / 2 - shipTex.getWidth() / 2;
        y = LevelScreen.GAME_HEIGHT - (shipTex.getHeight() * 3);
        
        collisionRec = new Rectangle(x, y, shipTex.getWidth(), shipTex.getHeight());
    }
    
    private void loadTextures()
    {
        shipTex = new Texture(Gdx.files.internal("textures/Ship.png"));
        explosionTex = new Texture(Gdx.files.internal("textures/ExplosionShip.png"));
        explosionReg = TextureRegion.split(explosionTex, explosionTex.getWidth() / 2, explosionTex.getHeight());
        
        Pixmap pixmap = new Pixmap(Bullet.WIDTH, Bullet.HEIGHT, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fillRectangle(0, 0, Bullet.WIDTH, Bullet.HEIGHT);
        bulletTex = new Texture(pixmap);
        pixmap.dispose();
    }
    
    public void update()
    {
        if(active)
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

            collisionRec.x = x;
        }
        else
        {
            if(TimeUtils.millis() - lastShipDeathTime > shipDeathTime && (moveLeft || moveRight || shoot))
                active = true;
            
            if(TimeUtils.millis() - lastExplostionTime >  explosionTime)
            {
                lastExplostionTime = TimeUtils.millis();
                frameIdExplosion++;
                if(frameIdExplosion > 1)
                    frameIdExplosion = 0;
            }
        }
        
    }
    
    public void render(SpriteBatch batch)
    {
        if(active)
            batch.draw(shipTex, x,y);
        else
        {
            switch(frameIdExplosion)
            {
                case 0: batch.draw(explosionReg[0][0], x, y); break;
                case 1: batch.draw(explosionReg[0][1], x, y); break;
            }
        }
        
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
    
    public void setActive(boolean active)
    {
        this.active = active;
        if(!active)
            lastShipDeathTime = TimeUtils.millis();
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
    
    public Rectangle getRectangle()
    {
        return collisionRec;
    }
    
    public float getY()
    {
        return y;
    }
    
    public boolean isActive()
    {
        return active;
    }
     
    public Texture getShipTexture()
    {
        return shipTex;
    }
}
