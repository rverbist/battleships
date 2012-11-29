package domain;

import java.awt.Color;
import java.io.Serializable;

/**
 * represents a player of the battleships game
 * @author rverbist
 */
@SuppressWarnings("serial")
public final class Player implements Comparable<Player>, Serializable
{
    private final int _id;
    private final String _name;
    private final Color _color;
    private boolean _isReady;

    /**
     * creates a new player with the given id, name and color
     * @param id the unique identifier of the player
     * @param name the name of the player
     * @param color the color of the player
     */
    public Player(final int id, final String name, final Color color)
    {
        _id = id;
        _name = name;
        _color = color;
        _isReady = false;
    }

    /**
     * gets the id
     * @return the unique identifier of this player
     */
    public int getId()
    {
        return _id;
    }

    /**
     * gets the name
     * @return the name of the player
     */
    public String getName()
    {
        return _name;
    }

    /**
     * gets the color
     * @return the color of the player
     */
    public Color getColor()
    {
        return _color;
    }

    /**
     * gets the ready status
     * @return if this player is ready to start a game
     */
    public boolean isReady()
    {
        return _isReady;
    }

    /**
     * sets the ready status
     * @param isReady the status
     */
    public void setReady(final boolean isReady)
    {
        _isReady = isReady;
    }

    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
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
        Player other = (Player) obj;
        if (_id != other._id)
            return false;
        return true;
    }
}
