/*
 * Centralized Unicode symbols used in the Sea Battle game.
 *
 * Purpose:
 *  - Avoid hard-coded symbols in print statements
 *  - Improve readability and consistency
 *  - Make future UI changes easier
 *
 * All symbols are represented using Unicode escape sequences
 * so they can be typed using only a keyboard.
 */

package console;

public class ConsoleSymbols {

    // ===================== BLOCKS =====================

    public static final String FULL_BLOCK       = "\u2588"; // █
    public static final String SOLID_SQUARE     = "\u25A0"; // ■

    // ===================== GAME STATUS ICONS =====================

    public static final String HIT_CIRCLE       = "\u25CF"; // ●
    public static final String MISS_X            = "X";      // X
    public static final String SEA_WAVE          = "~";      // ~

    // ===================== BANNERS & DECOR =====================

    public static final String TROPHY            = "\uD83C\uDFC6"; // 🏆
    public static final String PARTY_POPPER      = "\uD83C\uDF89"; // 🎉
    public static final String FIRE              = "\uD83D\uDD25"; // 🔥
    public static final String EXPLOSION         = "\uD83D\uDCA5"; // 💥
    public static final String CROSS_MARK        = "\u274C";       // ❌

    // ===================== BOX DRAWING (BOARD) =====================

    public static final String TOP_LEFT          = "\u250C"; // ┌
    public static final String TOP_RIGHT         = "\u2510"; // ┐
    public static final String BOTTOM_LEFT       = "\u2514"; // └
    public static final String BOTTOM_RIGHT      = "\u2518"; // ┘

    public static final String HORIZONTAL        = "\u2500"; // ─
    public static final String VERTICAL          = "\u2502"; // │

    public static final String T_TOP             = "\u252C"; // ┬
    public static final String T_BOTTOM          = "\u2534"; // ┴
    public static final String T_LEFT            = "\u251C"; // ├
    public static final String T_RIGHT           = "\u2524"; // ┤
    public static final String CROSS             = "\u253C"; // ┼

}

