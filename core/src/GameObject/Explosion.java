/** Copyright by Marlin Jurczik **/

package GameObject;

import com.badlogic.gdx.utils.TimeUtils;

/**
 * @author Marlin Jurczik
 */
class Explosion 
{
    private float x;
    private float y;
    
    private long spawnTime = 0;
    private long deathTime = 200;
    private boolean active;
    
    public Explosion(float x, float y)
    {
        this.x = x;
        this.y = y;
        active = true;
        spawnTime = TimeUtils.millis();
    }
    
    public void update()
    {
        if(TimeUtils.millis() - spawnTime > deathTime)
            active = false;
    }
    
    public boolean isActive()
    {
        return active;
    }

    public float getX()
    {
        return x;
    }

    public float getY()
    {
        return y;
    }
}
