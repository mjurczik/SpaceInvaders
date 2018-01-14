/** Copyright by Marlin Jurczik **/

package red.sub.spaceinvaders.Tools;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

/**
 * @author Marlin Jurczik
 */
public class BitmapFontTools 
{
    public static int getStringWidth(BitmapFont font,  String text)
    {
        GlyphLayout layout = new GlyphLayout();
        int width = 0;
        layout.setText(font, text);
        width = (int)layout.width;
        
        return width;
    }
    
    public static int getStringHeight(BitmapFont font,  String text)
    {
        GlyphLayout layout = new GlyphLayout();
        int height = 0;
        layout.setText(font, text);
        height = (int)layout.height;
        
        return height;
    }
}
