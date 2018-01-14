/** Copyright by Marlin Jurczik **/

package red.sub.spaceinvaders.Listener;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import red.sub.spaceinvaders.GameObject.StageManager;

/**
 * @author Marlin Jurczik
 */
public class LevelKeyListener extends InputAdapter
{
    private StageManager stageMan;
    
    public LevelKeyListener(StageManager screen)
    {
        this.stageMan = screen;
    }

    @Override
    public boolean keyUp(int keycode)
    {
        switch(keycode)
        {
            case Keys.LEFT: stageMan.getShip().setMoveLeft(false); break;
            case Keys.RIGHT: stageMan.getShip().setMoveRight(false); break;
            case Keys.SPACE: stageMan.getShip().setShoot(false); break;
            default: break;
        }
        
        return false;
    }

    @Override
    public boolean keyDown(int keycode)
    {
        switch(keycode)
        {
            case Keys.LEFT: stageMan.getShip().setMoveLeft(true);break;
            case Keys.RIGHT: stageMan.getShip().setMoveRight(true);break;
            case Keys.SPACE: stageMan.getShip().setShoot(true);break;
            default: break;
        }
        
        return false;
    }

}
