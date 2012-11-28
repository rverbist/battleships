package domain;

import java.awt.Color;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("serial")
public final class Ship implements Comparable<Ship>, Serializable
{
    private final int _id;
    private final ShipType _type;
    private final Orientation _orientation;
    private final Color _color;

    public Ship(final int id, final ShipType type, final Orientation orientation, final Color color)
    {
        _id = id;
        _type = type;
        _orientation = orientation;
        _color = color;
    }

    public int getId() 
    {
        return _id;
    }
    
    public ShipType getType()
    {
        return _type;
    }

    public Orientation getOrientation()
    {
        return _orientation;
    }
    
    public Color getColor()
    {
        return _color;
    }
    
    public Set<Location> map(final Location location) 
    {
        final Set<Location> locations = new HashSet<Location>();
        final int length = getType().getLength();
        Location last = location;
        for(int i = 0; i < length; i++)
        {
            locations.add(last);
            last = _orientation.translate(last);
        }
        return locations;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + _id;
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
        Ship other = (Ship) obj;
        if (_id != other._id)
            return false;
        return true;
    }
    
    @Override
    public String toString()
    {
        return getType().toString();
    }

    @Override
    public int compareTo(Ship o)
    {
        return getId() - o.getId();
    }
}