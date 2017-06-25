package controller.ui.helper;

import java.awt.*;

/**
 * Created by rkessler on 2017-06-24.
 */
public class FontHelper {

    protected static final int TITLE_FONT_SIZE = 24;
    protected static final String STANDARD_FONT = "Helvetica";

    static public Font boldStandardFont(){
        return new Font(STANDARD_FONT, Font.BOLD, TITLE_FONT_SIZE);
    }

    protected static final int TIME_FONT_SIZE = 50;
    protected static final int TIME_SUB_FONT_SIZE = 40;

    public static Font timeFont = new Font(STANDARD_FONT, Font.PLAIN,  TIME_FONT_SIZE);
    public static Font timeSubFont = new Font(STANDARD_FONT, Font.PLAIN,  TIME_SUB_FONT_SIZE);

}
