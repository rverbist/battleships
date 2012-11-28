package domain;

import java.awt.Color;

public enum ShipType
{
    Carrier(5, new Color(Color.HSBtoRGB(0.618033988749895f * 1, 0.3f, 0.99f))),
    Battleship(4, new Color(Color.HSBtoRGB(0.618033988749895f * 2, 0.3f, 0.99f))),
    Submarine(3, new Color(Color.HSBtoRGB(0.618033988749895f * 3, 0.3f, 0.99f))),
    Destroyer(3, new Color(Color.HSBtoRGB(0.618033988749895f * 4, 0.3f, 0.99f))),
    PatrolBoat(2, new Color(Color.HSBtoRGB(0.618033988749895f * 5, 0.3f, 0.99f)));
    
    private final int _length;
    private final Color _color;
    
    private ShipType(final int length, final Color color)
    {
        _length = length;
        _color = color;
    }
    
    public int getLength()
    {
        return _length;
    }
    
    public Color getColor()
    {
        return _color;
    }
}