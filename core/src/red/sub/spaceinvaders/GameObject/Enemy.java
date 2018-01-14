/** Copyright by Marlin Jurczik **/

package red.sub.spaceinvaders.GameObject;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

/**
 * @author Marlin Jurczik
 */
public class Enemy implements Comparable<Enemy>
{
    private static final int RIGHT = 0;
    private static final int LEFT = 1;
    
    private float x;
    private float y;
    private int width;
    private int height;
    private int type;
    
    private int xStepSize = 2;
    private int yStepSize = 5;
    private int moveStep = 20;
    private int moveDirection = RIGHT;
    private boolean active;
    
    private Rectangle collisionRec;
    
    public Enemy(int type, float x, float y, int width, int height)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.type = type;
        collisionRec = new Rectangle(x, y, width, height);
        active = true;
    }

    public void update()
    {
        if(moveStep > 40)
        {
            if(moveDirection == RIGHT)
                moveDirection = LEFT;
            else if(moveDirection == LEFT)
                moveDirection = RIGHT;
            
            y += yStepSize;
            moveStep = 0;
        }
        
        if(moveDirection == RIGHT)
        {
            moveStep++;
            x += xStepSize;
        }
        else if(moveDirection == LEFT)
        {
            moveStep++;
            x -= xStepSize;
        }
        
        collisionRec.x = x;
        collisionRec.y = y;
    }
    
    @Override
    public int compareTo(Enemy o)
    {
        if(this.y == o.y)
            return 0;
        else if(this.y < o.y)
            return 1;
        else
            return -1;
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

    public int getType()
    {
        return type;
    }
    
    public boolean isActive()
    {
        return active;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }
    
    public void setActive(boolean active)
    {
        this.active = active;
    }
}
