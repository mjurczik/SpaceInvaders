/** Copyright by Marlin Jurczik **/

package GameObject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import red.sub.spaceinvaders.GameState.LevelScreen;

/**
 * @author Marlin Jurczik
 */
public class EnemyBullet
{
    public static final int WIDTH = 3;
    public static final int HEIGHT = 7;
    
    private float x;
    private float y;
    private int speed = 50;
    private Rectangle collsionRec;
    
    private boolean active;
    
    public EnemyBullet(float x,  float y)
    {
        this.x = x;
        this.y = y;
        collsionRec = new Rectangle(x, y, WIDTH, HEIGHT);
        active = true;
    }
    
    public void update()
    {
        y+= speed * Gdx.graphics.getDeltaTime();
        collsionRec.y = y;
        
        if(y > LevelScreen.GAME_HEIGHT - HEIGHT)
            active = false;
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

    public Rectangle getRectangle()
    {
        return collsionRec;
    }
}
