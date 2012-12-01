package domain;

import java.awt.Color;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * represents a ship of the battleships game
 * @author rverbist
 */
@SuppressWarnings("serial")
public final class Ship implements Comparable<Ship>, Serializable
{
    private final int _id;
    private final ShipType _type;
    private final Orientation _orientation;
    private final Color _color;

    /**
     * creates a new ship with the given id, type, orientation and color
     * @param id the unique identifier of the ship
     * @param type the {@link ShipType} of the ship
     * @param orientation the {@link Orientation} of the ship
     * @param color the {@link Color} of the ship
     */
    public Ship(final int id, final ShipType type, final Orientation orientation, final Color color)
    {
        _id = id;
        _type = type;
        _orientation = orientation;
        _color = color;
    }

    /**
     * gets the id
     * @return the unique identifier of this ship
     */
    public int getId() 
    {
        return _id;
    }
    
    /**
     * gets the type
     * @return the type of this ship
     */
    public ShipType getType()
    {
        return _type;
    }

    /**
     * gets the orientation
     * @return the orientation of this ship
     */
    public Orientation getOrientation()
    {
        return _orientation;
    }
    
    /**
     * gets the color
     * @return the color of this ship
     */
    public Color getColor()
    {
        return _color;
    }
    
    /**
     * maps the layout of this ship relative to the given {@link Location}
     * @param location the {@link Location} of the ship's head
     * @return a set of {@link Location} that represent the layout of this ship
     */
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

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + _id;
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
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
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return getType().toString();
    }

    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(Ship o)
    {
        return getId() - o.getId();
    }
}