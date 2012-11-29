package gui.components.grid;

import java.awt.Point;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import domain.util.Model;

/**
 * @author rverbist
 */
public final class BattleShipGridSelectionModel extends Model
{
    public interface Listener
    {
        void selected(final BattleShipGridSelectionModel model, final Point point);
        void unselected(final BattleShipGridSelectionModel model, final Point point);
    }

    private final Set<Point> _selection;
    private final Set<Listener> _listeners;

    public BattleShipGridSelectionModel()
    {
        _selection = Collections.synchronizedSet(new HashSet<Point>());
        _listeners = Collections.synchronizedSet(new HashSet<Listener>());
        addProperty("AllowSelection", false);
        addProperty("AllowMultipleSelection", false);
    }

    public Boolean getAllowMultipleSelection()
    {
        return getProperty("AllowMultipleSelection");
    }

    public void setAllowMultipleSelection(final boolean value)
    {
        setProperty("AllowMultipleSelection", value);
    }

    public Boolean getAllowSelection()
    {
        return getProperty("AllowSelection");
    }

    public void setAllowSelection(final boolean value)
    {
        setProperty("AllowSelection", value);
    }

    public Set<Point> getSelection()
    {
        return Collections.unmodifiableSet(_selection);
    }

    public void select(final Point point)
    {
        if (getAllowSelection())
        {
            if (!_selection.contains(point))
            {
                if (!getAllowMultipleSelection())
                {
                    _selection.clear();
                }
                _selection.add(point);
                notifyAdded(point);
            }
        }
    }

    public void unselect(final Point point)
    {
        if (_selection.remove(point))
        {
            notifyRemoved(point);
        }
    }

    public void clear()
    {
        _selection.clear();
    }

    public void addListener(final Listener listener)
    {
        _listeners.add(listener);
    }

    public void removeListener(final Listener listener)
    {
        _listeners.remove(listener);
    }
    
    public void clearListeners()
    {
        _listeners.clear();
    }

    protected void notifyAdded(final Point point)
    {
        for (final Listener listener : new HashSet<Listener>(_listeners))
        {
            listener.selected(this, point);
        }
    }

    protected void notifyRemoved(final Point point)
    {
        for (final Listener listener : new HashSet<Listener>(_listeners))
        {
            listener.unselected(this, point);
        }
    }
}
