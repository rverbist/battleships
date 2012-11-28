package domain;

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
    
    public synchronized Ship create(final ShipType type, final Orientation orientation)
    {
        return new Ship(_identifier++, type, orientation, type.getColor());
    }
}
