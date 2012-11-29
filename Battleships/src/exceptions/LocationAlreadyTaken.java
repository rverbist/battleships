package exceptions;

import domain.Location;

/**
 * represents an error where a location has already been taken
 * @author rverbist
 */
@SuppressWarnings("serial")
public final class LocationAlreadyTaken extends LocationException
{
    public LocationAlreadyTaken(final Location location)
    {
        super(location, "The location is already taken by another object on the board.");
    }
}