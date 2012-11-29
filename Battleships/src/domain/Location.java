package domain;

import java.io.Serializable;

/**
 * an immutable location structure
 * @author rverbist
 */
@SuppressWarnings("serial")
public final class Location implements Comparable<Location>, Serializable
{
    private final int _row;
    private final int _column;

    /**
     * copy constructor
     */
    public Location(final Location other)
    {
        _row = other.getRow();
        _column = other.getColumn();
    }

    
    /**
     * creates a new {@link Location} with the given row and column
     * @param row row value
     * @param column column value
     */
    public Location(final int row, final int column)
    {
        _row = row;
        _column = column;
    }

    /**
     * @return the row value of this instance
     */
    public int getRow()
    {
        return _row;
    }

    /**
     * @return the column value of this instance
     */
    public int getColumn()
    {
        return _column;
    }

    /**
     * generates the battleships row value of this location by calculating the 1-based index
     * and converting the number to its string equivalent (1, 2, 3...)
     * @return the row value as text
     */
    public String getRowText()
    {
        return String.format("%2d", rebase(_row));
    }

    /**
     * generates the battleships column value of this location by calculating the 1-based index
     * and converting the number to its string equivalent (A, B, C,...)
     * @return
     */
    public String getColumnText()
    {
        return String.valueOf((char) ('A' + (rebase(_column) - 1)));
    }

    private static int rebase(int value)
    {
        return value + 1;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + _column;
        result = prime * result + _row;
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Location other = (Location) obj;
        if (_column != other._column)
            return false;
        if (_row != other._row)
            return false;
        return true;
    }
    
    @Override
    public int compareTo(Location o)
    {
        int result = getRow() - o.getRow();
        if (result != 0)
        {
            return result;
        }
        return getColumn() - o.getColumn();
    }
}
