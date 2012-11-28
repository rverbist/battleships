package domain;

import java.awt.Color;
import java.io.Serializable;
import java.util.Collections;
import java.util.Set;

@SuppressWarnings("serial")
public final class PlacedShip implements Serializable
{
    public final Ship _ship;
    public final Set<Location> _layout;
    public final Set<Location> _health;

    public PlacedShip(final Ship ship, final Location location)
    {
        _ship = ship;
        _layout = Collections.unmodifiableSet(_ship.map(location));
        _health = _ship.map(location);
    }

    public int getHealth()
    {
        return _health.size();
    }

    public int getMaximumHealth()
    {
        return _layout.size();
    }
    
    public Color getColor()
    {
        return _ship.getColor();
    }

    public Set<Location> getLayoutMap()
    {
        return _layout;
    }

    public Set<Location> getHealthMap()
    {
        return Collections.unmodifiableSet(_health);
    }
    
    public boolean ishit(final Location location)
    {
        return _layout.contains(location);
    }
    
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
