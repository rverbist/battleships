package domain;

import java.util.Random;

public enum Orientation
{
    North(-1, 0), East(0, 1), South(1, 0), West(0, -1);

    private static final Random random = new Random();
    private final int _rowOffset;
    private final int _columnOffset;

    private Orientation(final int rowOffset, final int columnOffset)
    {
        _rowOffset = rowOffset;
        _columnOffset = columnOffset;
    }

    public int getRowOffset()
    {
        return _rowOffset;
    }

    public int getColumnOffset()
    {
        return _columnOffset;
    }

    public Location translate(final Location location)
    {
        return new Location(location.getRow() + _rowOffset, location.getColumn() + _columnOffset);
    }
    
    public static Orientation random()
    {
        final Orientation[] orientations = values();
        return orientations[random.nextInt(orientations.length)];
    }
}