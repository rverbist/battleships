package domain;

/**
 * a factory for ship objects
 * @author rverbist
 */
public final class ShipFactory
{
    private static final ShipFactory _instance = new ShipFactory();

    public static final ShipFactory getInstance()
    {
        return _instance;
    }

    private int _identifier;

    private ShipFactory()
    {
        _identifier = 0;
    }
    
    /**
     * creates an unique ship object of the given type and orientation
     * @param type the type of the ship
     * @param orientation the orientation of the ship
     * @return a ship
     */
    public synchronized Ship create(final ShipType type, final Orientation orientation)
    {
        return new Ship(_identifier++, type, orientation, type.getColor());
    }
}
