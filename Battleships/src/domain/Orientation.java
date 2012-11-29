package domain;

import java.util.Random;

/**
 * represents an orientation vector in a 2 dimensional plane
 * @author rverbist
 */
public enum Orientation
{
    North(-1, 0), East(0, 1), South(1, 0), West(0, -1);

    private static final Random random = new Random();
    private final int _rowOffset;
    private final int _columnOffset;

    /**
     * creates a new orientation vector
     * @param rowOffset the row offset
     * @param columnOffset the column offset
     */
    private Orientation(final int rowOffset, final int columnOffset)
    {
        _rowOffset = rowOffset;
        _columnOffset = columnOffset;
    }

    /**
     * gets the row offset value
     * @return the row offset value of this orientation vector
     */
    public int getRowOffset()
    {
        return _rowOffset;
    }

    /**
     * gets the column offset value
     * @return the column offset value of this orientation vector
     */
    public int getColumnOffset()
    {
        return _columnOffset;
    }

    /**
     * translates the given {@link Location} based on this orientation vector
     * @param location the {@link Location} to translate
     * @return the translated {@link Location}
     */
    public Location translate(final Location location)
    {
        return new Location(location.getRow() + _rowOffset, location.getColumn() + _columnOffset);
    }
    
    /**
     * @return a random orientation vector of this enumeration
     */
    public static Orientation random()
    {
        final Orientation[] orientations = values();
        return orientations[random.nextInt(orientations.length)];
    }
}