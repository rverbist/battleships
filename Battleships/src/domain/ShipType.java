package domain;

import java.awt.Color;

/**
 * ship types used in most battleship games
 * @author rverbist
 */
public enum ShipType
{
    Carrier(5, new Color(Color.HSBtoRGB(0.618033988749895f * 1, 0.3f, 0.99f))),
    Battleship(4, new Color(Color.HSBtoRGB(0.618033988749895f * 2, 0.3f, 0.99f))),
    Submarine(3, new Color(Color.HSBtoRGB(0.618033988749895f * 3, 0.3f, 0.99f))),
    Destroyer(3, new Color(Color.HSBtoRGB(0.618033988749895f * 4, 0.3f, 0.99f))),
    PatrolBoat(2, new Color(Color.HSBtoRGB(0.618033988749895f * 5, 0.3f, 0.99f)));
    
    private final int _length;
    private final Color _color;
    
    /**
     * creates a new ship type of given length and color
     * @param length the length of the ship type
     * @param color the color of the ship type
     */
    private ShipType(final int length, final Color color)
    {
        _length = length;
        _color = color;
    }
    
    /**
     * gets the length of this type
     * @return the length
     */
    public int getLength()
    {
        return _length;
    }
    
    /**
     * gets the color of this type
     * @return the color
     */
    public Color getColor()
    {
        return _color;
    }
}