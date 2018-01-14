/** Copyright by Marlin Jurczik **/

package red.sub.spaceinvaders.GameObject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

/**
 * @author Marlin Jurczik
 */
public class Bullet 
{
    public static final int WIDTH = 1;
    public static final int HEIGHT = 5;
    private float x;
    private float y;
    private int speed = 200;
    private boolean active; 
    
    private Rectangle collisionRec;
    
    public Bullet(float x, float y)
    {
        this.x = x;
        this.y = y;
        active = true;
        collisionRec = new Rectangle(x, y, WIDTH, HEIGHT);
    }
    
    public void update()
    {
        y -= speed * Gdx.graphics.getDeltaTime();
        collisionRec.y = y;
        
        if(y < 0)
            active = false;
    }
    
    public Rectangle getRectangle()
    {
        return collisionRec;
    }
    
    public float getX()
    {
        return x;
    }

    public float getY()
    {
        return y;
    }
    
    public boolean isActive()
    {
        return active;
    }
    
    public void setActive(boolean active)
    {
        this.active = active;
    }
}
