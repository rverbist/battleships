package domain;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import exceptions.LocationAlreadyTaken;
import exceptions.LocationOutOfBounds;

@SuppressWarnings("serial")
public final class Board implements Serializable
{
    private final int _dimension;
    private final Set<PlacedShip> _ships;
    private final MapSlot[][] _layout;

    public Board(int dimension)
    {
        _dimension = dimension;
        _ships = new HashSet<PlacedShip>();
        _layout = new MapSlot[_dimension][_dimension];
        for (int row = 0; row < _dimension; row++)
        {
            for (int column = 0; column < _dimension; column++)
            {
                _layout[row][column] = MapSlot.None;
            }
        }
    }

    public int getDimension()
    {
        return _dimension;
    }

    public Set<PlacedShip> getShips()
    {
        return Collections.unmodifiableSet(_ships);
    }

    public void addShip(final Ship ship, final Location location)
    {
        if (!isValidLocation(location))
        {
            throw new LocationOutOfBounds(location);
        }
        final PlacedShip placed = new PlacedShip(ship, location);
        for (final Location place : placed.getLayoutMap())
        {
            if (!isValidLocation(place))
            {
                throw new LocationOutOfBounds(place);
            }
            if (getShipAtLocation(place) != null)
            {
                throw new LocationAlreadyTaken(place);
            }
        }
        _ships.add(placed);
    }

    public void removeShip(final PlacedShip ship)
    {
        _ships.remove(ship);
    }

    public void clearShips()
    {
        _ships.clear();
    }

    public MapSlot fire(final Location location)
    {
        if (!isValidLocation(location))
        {
            throw new LocationOutOfBounds(location);
        }
        final MapSlot slot = _layout[location.getRow()][location.getColumn()];
        if (slot == MapSlot.None)
        {
            for (final PlacedShip ship : _ships)
            {
                if (ship.ishit(location) && ship.hit(location))
                {
                    return _layout[location.getRow()][location.getColumn()] = MapSlot.Hit;
                }
            }
            return _layout[location.getRow()][location.getColumn()] = MapSlot.Miss;
        }
        return slot;
    }

    private boolean isValidLocation(final Location location)
    {
        return 0 <= location.getRow() && location.getRow() < _dimension && 0 <= location.getColumn() && location.getColumn() < _dimension;
    }

    private PlacedShip getShipAtLocation(final Location location)
    {
        for (final PlacedShip ship : _ships)
        {
            if (ship.ishit(location))
            {
                return ship;
            }
        }
        return null;
    }

    @Override
    public String toString()
    {
        final char[][] layout = new char[_dimension][_dimension];
        // initialize the layout grid
        for (int row = 0; row < _dimension; row++)
        {
            for (int column = 0; column < _dimension; column++)
            {
                layout[row][column] = ' ';
            }
        }
        // draw ships
        for (final PlacedShip ship : _ships)
        {
            for (final Location location : ship.getLayoutMap())
            {
                layout[location.getRow()][location.getColumn()] = 'S';
            }
        }
        // draw missiles
        for (int row = 0; row < _dimension; row++)
        {
            for (int column = 0; column < _dimension; column++)
            {
                final MapSlot type = _layout[row][column];
                switch (type)
                {
                    case None: 
                        break;
                    case Hit:
                        layout[row][column] = 'X';
                        break;
                    case Miss:
                        layout[row][column] = 'M';
                        break;
                }
            }
        }
        // create string representation of the layout
        final StringBuilder sb = new StringBuilder();
        sb.append("  ");
        for (int column = 0; column < _dimension; column++)
        {
            final Location column_header = new Location(0, column);
            sb.append(column_header.getColumnText());
            sb.append('|');
        }
        sb.append('\n');
        for (int row = 0; row < _dimension; row++)
        {
            final Location row_header = new Location(row, 0);
            sb.append(row_header.getRowText());
            for (int column = 0; column < _dimension; column++)
            {
                sb.append(layout[row][column]);
                sb.append('|');
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}