package domain;

import java.awt.Color;
import java.io.Serializable;

@SuppressWarnings("serial")
public final class Player implements Comparable<Player>, Serializable
{
    private final int _id;
    private final String _name;
    private final Color _color;
    private boolean _isReady;

    public Player(final int id, final String name, final Color color)
    {
        _id = id;
        _name = name;
        _color = color;
        _isReady = false;
    }

    public int getId()
    {
        return _id;
    }

    public String getName()
    {
        return _name;
    }

    public Color getColor()
    {
        return _color;
    }

    public boolean isReady()
    {
        return _isReady;
    }

    public void setReady(final boolean isReady)
    {
        _isReady = isReady;
    }

    @Override
    public int compareTo(Player other)
    {
        if (_name == null || other._name == null)
        {
            System.err.println("It's the names");
        }
        int result = _name.compareTo(other._name);
        if (result != 0)
        {
            return result;
        }
        return _id - other._id;
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
        Player other = (Player) obj;
        if (_id != other._id)
            return false;
        return true;
    }
}
