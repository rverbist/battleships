package exceptions;

import domain.Location;

/**
 * represents a location related error
 * @author rverbist
 */
@SuppressWarnings("serial")
public abstract class LocationException extends RuntimeException
{
    private final Location _location;

    protected LocationException(final Location location, final String message)
    {
        super(message);
        _location = location;
    }

    public Location getLocation()
    {
        return _location;
    }
}