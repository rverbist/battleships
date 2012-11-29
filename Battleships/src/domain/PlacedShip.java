package domain;

import java.awt.Color;
import java.io.Serializable;
import java.util.Collections;
import java.util.Set;

/**
 * represents a ship that has been assigned a location on a board
 * @author rverbist
 */
@SuppressWarnings("serial")
public final class PlacedShip implements Serializable
{
    public final Ship _ship;
    public final Set<Location> _layout;
    public final Set<Location> _health;

    /**
     * creates a new representation of a ship that has been assigned a location on a board
     * @param ship the ship to assign a location
     * @param location the location of the ship
     */
    public PlacedShip(final Ship ship, final Location location)
    {
        _ship = ship;
        _layout = Collections.unmodifiableSet(_ship.map(location));
        _health = _ship.map(location);
    }

    /**
     * gets the health of the ship
     * @return the remaining health of this ship
     */
    public int getHealth()
    {
        return _health.size();
    }

    /**
     * gets the maximum health of the ship
     * @return the maximum health of this ship
     */
    public int getMaximumHealth()
    {
        return _layout.size();
    }
    
    /**
     * gets the color assigned to this ship
     * @return the color assigned to this ship
     */
    public Color getColor()
    {
        return _ship.getColor();
    }

    /**
     * gets a set of locations that represent this ship
     * @return an unmodifiable set of locations that represent this ship
     */
    public Set<Location> getLayoutMap()
    {
        return _layout;
    }

    /**
     * gets a set of {@link Location} that represent this ship's health
     * @return an unmodifiable set of {@link Location} that represent this ship's health
     */
    public Set<Location> getHealthMap()
    {
        return Collections.unmodifiableSet(_health);
    }
    
    /**
     * determines if the given {@link Location} is part of this ship
     * @param location the {@link Location} to intersect with
     * @return true if the given {@link Location} intersects with this ship, false otherwise
     */
    public boolean ishit(final Location location)
    {
        return _layout.contains(location);
    }
    
    /**
     * hits the ship at the given {@link Location}
     * @param location the given {@link Location} to hit the ship at
     * @return true if the given {@link Location} hits the ship, false otherwise
     */
    public boolean hit(final Location location)
    {
        return _health.remove(location);
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + _ship.hashCode();
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
        PlacedShip other = (PlacedShip) obj;
        if (!_ship.equals(other._ship))
            return false;
        return true;
    }
    
    @Override
    public String toString()
    {
        return String.format("%s - %d / %d", _ship, getHealth(), getMaximumHealth());
    }
}
