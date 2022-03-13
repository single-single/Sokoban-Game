package sample.objects;

/**
 * GameObject class defines all the objects of this game.
 * The 8 characters represent 8 different objects.
 *
 * @author Shiqi Xin-modified
 */
public enum GameObject {
    WALL('W'),
    FLOOR(' '),
    CRATE('C'),
    DIAMOND('D'),
    KEEPER('S'),
    CRATE_ON_DIAMOND('O'),
    KEEPER_ON_DIAMOND('K'),
    DEBUG_OBJECT('=');

    private final char SYMBOL;

    /**
     * This is the only constructor of the GameObject class.
     * It sets the SYMBOL to parameter symbol.
     * @param symbol {@code char} representing objects.
     */
    GameObject(final char symbol) {
        SYMBOL = symbol;
    }

    /**
     * This method finds the GameObject element that matches the character c.
     * @param c {@code char} variable.
     * @return the GameObject t if the character c matches the symbol of t.
     * Otherwise, return {@code WALL}.
     */
    public static GameObject FromChar(char c) {
        for (GameObject t : GameObject.values()) {
            if (Character.toUpperCase(c) == t.SYMBOL) {
                return t;
            }
        }

        return WALL;
    }

    /**
     * * Get the symbol of the GameObject.
     * @return {@code char SYMBOL}.
     */
    public char GetCharSymbol() {
        return SYMBOL;
    }
}