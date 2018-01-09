/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameObject;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import java.awt.Point;
import java.util.Iterator;
import red.sub.spaceinvaders.GameState.LevelScreen;

/**
 *
 * @author tucura
 */
public class ShieldManager 
{
    private static final Color TRANSPARENT_COL = new Color(Color.BLACK);
    private static final int DAMAGE_RANGE = 3;
    
    private EnemyManager eMan;
    private Ship ship;
    private final int capacity = 4;
    private Array<Shield> shields;
    private boolean active;
    
    public ShieldManager(EnemyManager eMan, Ship ship)
    {
        this.eMan = eMan;
        this.ship = ship;
        shields = new Array<Shield>();
        initShields();
        active = true;
    }
    
    private void initShields()
    {
        int width_offset = LevelScreen.GAME_WIDTH / capacity;
        int x_offset = (LevelScreen.GAME_WIDTH / capacity) / 2 - Shield.WIDTH / 2;
        int y_offset = LevelScreen.GAME_HEIGHT - Shield.HEIGTH - 30;
        for(int i = 0; i < capacity; i++)
        {
            shields.add(new Shield(x_offset + (width_offset * i), y_offset));
        }
    }
    
    public void update()
    {
        //checkColission -> pixel col
        if(active)
            checkCollision();
    }
    
    public void checkCollision()
    {
        Iterator<EnemyBullet> iterEnemyBullets = eMan.getEnemyBullets().iterator();
        while(iterEnemyBullets.hasNext())
        {
            EnemyBullet b = iterEnemyBullets.next();
            Iterator<Shield> iterShield = shields.iterator();
            while(iterShield.hasNext())
            {
                Shield s = iterShield.next();
                if(b.getRectangle().overlaps(s.getRectangle()))
                {
                    Point colPointBullet = new Point((int)b.getX() + EnemyBullet.WIDTH / 2, (int)b.getY() + EnemyBullet.HEIGHT);
                    Pixmap updatedPixmap = isPixelColission(colPointBullet, s);
                    if(updatedPixmap != null)
                    {
                        b.setActive(false);
                        s.updateTexture(updatedPixmap);
                    }
                }
            }
        }
        
        Iterator<Bullet> iterShipBullets = ship.getBullets().iterator();
        while(iterShipBullets.hasNext())
        {
            Bullet b = iterShipBullets.next();
            Iterator<Shield> iterShield = shields.iterator();
            while(iterShield.hasNext())
            {
                Shield s = iterShield.next();
                if(b.getRectangle().overlaps(s.getRectangle()))
                {
                    Point colPointBullet = new Point(MathUtils.round(b.getX()), MathUtils.round(b.getY()));
                    Pixmap updatedPixmap = isPixelColission(colPointBullet, s);
                    if(updatedPixmap != null)
                    {
                        b.setActive(false);
                        s.updateTexture(updatedPixmap);
                    }
                }
            }
        }
    }
    
    
    private Pixmap isPixelColission(Point colP, Shield s) 
    {
        Pixmap sP = s.getPixmap();
        
        for(int x = 0; x < sP.getWidth(); x++)
            for(int y = 0; y < sP.getHeight(); y++)
            {
                if(colP.x == x + s.getX() && colP.y == y + s.getY() && sP.getPixel(x, y) == -1)
                {   
                    sP.setColor(TRANSPARENT_COL);
                    sP.fillCircle(x, y, DAMAGE_RANGE);
                    //System.out.println("Bullet: " + colP.x + "|" + colP.y + " Pixmap absolute " + (x+s.getX()) + "|" + (y+s.getY()) + " relative " + x+ "|" + y);
                    return sP;
                }
            }
        return null;
    }
    
    public void render(SpriteBatch batch)
    {
        Iterator<Shield> iterShield = shields.iterator();
        while(iterShield.hasNext())
            iterShield.next().render(batch);
    }
    
    public void dispose()
    {
        Iterator<Shield> iterShield = shields.iterator();
        while(iterShield.hasNext())
            iterShield.next().dispose();
    }
}
