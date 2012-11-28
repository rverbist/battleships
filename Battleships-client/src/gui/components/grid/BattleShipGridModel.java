package gui.components.grid;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import domain.Player;
import domain.Location;
import domain.PlacedShip;

public final class BattleShipGridModel extends domain.util.Model
{
    public BattleShipGridModel()
    {
        super();
        addProperty("width", 10);
        addProperty("height", 10);
        addProperty("ships", new HashSet<PlacedShip>());
        addProperty("suggestions", new HashMap<Player, Location>());
        addProperty("hits", new HashSet<Location>());
        addProperty("misses", new HashSet<Location>());
    }

    public int getWidth()
    {
        return getProperty("width");
    }
    
    public void setWidth(final int value)
    {
        setProperty("width", value);
    }

    public int getHeight()
    {
        return getProperty("height");
    }
    
    public void setHeight(final int value)
    {
        setProperty("height", value);
    }
    
    public Set<PlacedShip> getShips()
    {
        return getProperty("ships");
    }
    
    public void setShips(final Set<PlacedShip> value)
    {
        setProperty("ships", value);
    }
    
    public Map<Player, Location> getSuggestions()
    {
        return getProperty("suggestions");
    }
    public void addSuggestion(final Player player, final Location location)
    {
        final Map<Player, Location> suggestions = getSuggestions();
        suggestions.put(player, location);
        notifyPropertyChanged("suggestions", suggestions, suggestions);
    }
    public void removeSuggestion(final Player player)
    {
        final Map<Player, Location> suggestions = getSuggestions();
        suggestions.remove(player);
        notifyPropertyChanged("suggestions", suggestions, suggestions);
    }
    public void clearSuggestions()
    {
        final Map<Player, Location> suggestions = getSuggestions();
        suggestions.clear();
        notifyPropertyChanged("suggestions", suggestions, suggestions);
    }
    
    public Set<Location> getHits()
    {
        return getProperty("hits");
    }
    public void addHit(final Location location)
    {
        final Set<Location> hits = getHits();
        hits.add(location);
        notifyPropertyChanged("hits", hits, hits);
    }
    public void clearHits()
    {
        final Set<Location> hits = getHits();
        hits.clear();
        notifyPropertyChanged("hits", hits, hits);
    }
    
    public Set<Location> getMisses()
    {
        return getProperty("misses");
    }
    public void addMiss(final Location location)
    {
        final Set<Location> misses = getMisses();
        misses.add(location);
        notifyPropertyChanged("misses", misses, misses);
    }
    public void clearMisses()
    {
        final Set<Location> misses = getMisses();
        misses.clear();
        notifyPropertyChanged("misses", misses, misses);
    }
}
