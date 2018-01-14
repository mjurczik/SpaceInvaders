/** Copyright by Marlin Jurczik **/

package red.sub.spaceinvaders.Listener;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import red.sub.spaceinvaders.GameState.LevelScreen;

/**
 * @author Marlin Jurczik
 */
public class LevelKeyListener extends InputAdapter
{
    private LevelScreen screen;
    
    public LevelKeyListener(LevelScreen screen)
    {
        this.screen = screen;
    }

    @Override
    public boolean keyUp(int keycode)
    {
        switch(keycode)
        {
            case Keys.LEFT: screen.getShip().setMoveLeft(false); break;
            case Keys.RIGHT: screen.getShip().setMoveRight(false); break;
            case Keys.SPACE: screen.getShip().setShoot(false); break;
            default: break;
        }
        
        return false;
    }

    @Override
    public boolean keyDown(int keycode)
    {
        switch(keycode)
        {
            case Keys.LEFT: screen.getShip().setMoveLeft(true);break;
            case Keys.RIGHT: screen.getShip().setMoveRight(true);break;
            case Keys.SPACE: screen.getShip().setShoot(true);break;
            default: break;
        }
        
        return false;
    }

}
