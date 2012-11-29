package domain;

import java.util.Random;

import exceptions.LocationAlreadyTaken;
import exceptions.LocationOutOfBounds;

/**
 * a factory to create Board objects that are already populated with ships
 * @author rverbist
 */
public final class BoardFactory
{
    private static final BoardFactory _instance = new BoardFactory();

    public static final BoardFactory getInstance()
    {
        return _instance;
    }
    
    private final Random _random = new Random();
    private final ShipFactory _shipFactory = ShipFactory.getInstance();
    
    private BoardFactory()
    {
    }
    
    /**
     * creates a new {@link Board} of the given dimension with the standard amount of ships
     * randomly placed on the {@link Board}.
     * @param dimension the dimension of the {@link Board}
     * @return a {@link Board} of the given dimension
     */
    public synchronized Board create(int dimension)
    {
        final Board board = new Board(dimension);
        for(final ShipType type : ShipType.values())
        {
            boolean added = false;
            while (!added)
            {
                final Location location = new Location(_random.nextInt(board.getDimension()), _random.nextInt(board.getDimension()));
                final Ship ship = _shipFactory.create(type, Orientation.random());
                try
                {
                    board.addShip(ship, location);
                    added = true;
                }
                catch(LocationOutOfBounds e) { } // this will occur when part of the ship is outside of the board
                catch(LocationAlreadyTaken f) { } // this will occur when part of the ship overlaps with another ship
            }
        }
        return board;
    }
}
