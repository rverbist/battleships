package exceptions;

import domain.Location;

/**
 * represents an error where a location is outside of bounds
 * @author rverbist
 */
@SuppressWarnings("serial")
public final class LocationOutOfBounds extends LocationException
{
    public LocationOutOfBounds(final Location location)
    {
        super(location, "The location is outside of the bounds of the board.");
    }
}