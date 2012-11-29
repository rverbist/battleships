package domain;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import exceptions.LocationAlreadyTaken;
import exceptions.LocationOutOfBounds;

/**
 * the {@link Board} class represents a single battleship game board. The board
 * keeps track of the condition of each slot as well as any placed ships
 * @see {@link MapSlot}
 * @see {@link PlacedShip}
 * @author rverbist
 */
@SuppressWarnings("serial")
public final class Board implements Serializable
{
    private final int _dimension;
    private final Set<PlacedShip> _ships;
    private final MapSlot[][] _layout;

    /**
     * creates a new battleship game board of the given dimension
     * @param dimension board size
     */
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

    /**
     * gets the board dimension of this instance
     * @return board dimension
     */
    public int getDimension()
    {
        return _dimension;
    }

    /**
     * gets the ships that are owned by this instance
     * @return unmodifiable set of ships
     */
    public Set<PlacedShip> getShips()
    {
        return Collections.unmodifiableSet(_ships);
    }

    /**
     * adds a {@link Ship} to this board on the specified location
     * @param ship a {@link Ship} instance
     * @param location the {@link Location} on which to place the ship
     * @return true if the {@link Ship} is added, false otherwise
     * @throws {@link LocationOutOfBounds} if the specified {@link Location} is outside of the bounds of the board
     * @throws {@link LocationAlreadyTaken} if the specified {@link Location} is already taken by another ship
     */
    public boolean addShip(final Ship ship, final Location location)
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
        return _ships.add(placed);
    }

    
    /**
     * removes a {@link PlacedShip} from this board
     * @param ship a {@link PlacedShip} instance
     * @return true if the {@link PlacedShip} is removed, false other wise
     */
    public boolean removeShip(final PlacedShip ship)
    {
        return _ships.remove(ship);
    }

    /**
     * removes all ships from this board
     */
    public void clearShips()
    {
        _ships.clear();
    }

    /**
     * fires a missile at the given {@link Location} and returns the new {@link MapSlot} status
     * @param location the {@link Location} at which to fire a missile
     * @return the status at the given {@link Location}
     * @throws {@link LocationOutOfBounds} if the specified location is out of bounds
     */
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

    /**
     * determines if the specified {@link Location} is within the bounds of the board
     * @param location the {@link Location} to validate
     * @return true if the {@link Location} is within bounds of the board, false otherwise
     */
    private boolean isValidLocation(final Location location)
    {
        return 0 <= location.getRow() && location.getRow() < _dimension && 0 <= location.getColumn() && location.getColumn() < _dimension;
    }

    /**
     * returns the {@link PlacedShip} at the specified {@link Location}
     * @param location the {@link Location} to check for the existence of a {@link PlacedShip}
     * @return the {@link PlacedShip} at the specified {@link Location}, null if none
     */
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