/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameObject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 *
 * @author tucura
 */
public class Shield 
{
    public static final int WIDTH = 22;
    public static final int HEIGTH = 16;
    private int x;
    private int y;
    
    private Rectangle collisionRec;
    private Texture shieldTex;
    
    public Shield(int x, int y)
    {
        this.x = x;
        this.y = y;
        collisionRec = new Rectangle(x, y, WIDTH, HEIGTH);
        
        shieldTex = new Texture(Gdx.files.internal("textures/Shield.png"));
    }
    
    public void render(SpriteBatch batch)
    {
        batch.draw(shieldTex, x, y);
    }
    
    public void dispose()
    {
        shieldTex.dispose();
    }
    
    public void updateTexture(Pixmap pixmap)
    {
        shieldTex = new Texture(pixmap);
    }

    public int getX() 
    {
        return x;
    }

    public int getY() 
    {
        return y;
    }
    
    public Pixmap getPixmap()
    {
        if (!shieldTex.getTextureData().isPrepared())
            shieldTex.getTextureData().prepare();
        
        return shieldTex.getTextureData().consumePixmap();
    }
    
    public Rectangle getRectangle()
    {
        return collisionRec;
    }
}
