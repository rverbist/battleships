package domain;

public final class PlayerFactory
{
    private static final PlayerFactory _instance = new PlayerFactory();

    public static final PlayerFactory getInstance()
    {
        return _instance;
    }

    private int _identifier;
    private GoldenRatioColorGenerator _colors;

    private PlayerFactory()
    {
        _identifier = 0;
        _colors = new GoldenRatioColorGenerator(0.5f, 0.95f);
    }

    public synchronized Player create(final String name)
    {
        return new Player(_identifier++, name, _colors.next());
    }
}
