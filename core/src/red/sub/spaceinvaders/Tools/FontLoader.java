/** Copyright by Marlin Jurczik **/

package red.sub.spaceinvaders.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

/**
 * @author Marlin Jurczik
 */
public class FontLoader 
{
    //font files
    public static final String PIXEL_FONT_PATH = "fonts/pixelFJ8pt1.TTF";
    
    //different fonts
    public static BitmapFont bigMenuFont;
    public static BitmapFont smallMenuFont;
    public static BitmapFont scoreFont;
    
    public static void loadFonts()
    {
        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal(PIXEL_FONT_PATH));
        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        
        //parameter for font
        param.size = 90;
        param.color = Color.BLUE;
        param.flip = true;
        bigMenuFont = gen.generateFont(param);
        
        param.size = 5;
        param.color = Color.YELLOW;
        smallMenuFont = gen.generateFont(param);
        
        param.size = 10;
        param.color = Color.CYAN;
        scoreFont = gen.generateFont(param);
    }
}
