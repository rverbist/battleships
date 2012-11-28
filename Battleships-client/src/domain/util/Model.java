package domain.util;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public abstract class Model
{
    public final Set<INotifyPropertyChanged> _observers;
    public final Map<String, Object> _properties;
    
    protected Model()
    {
        _observers = new HashSet<INotifyPropertyChanged>();
        _properties = new TreeMap<String, Object>();
    }
    
    public void addObserver(final INotifyPropertyChanged observer)
    {
        _observers.add(observer);
    }
    
    public void removeObserver(final INotifyPropertyChanged observer)
    {
        _observers.remove(observer);
    }
    
    public void clearObservers()
    {
        _observers.clear();
    }
    
    protected <T> void addProperty(final String property, final T value)
    {
        if (_properties.containsKey(property))
        {
            throw new IllegalArgumentException(String.format("The property '%s' is already defined", property));
        }
        _properties.put(property, value);
    }
    
    @SuppressWarnings("unchecked")
    protected <T> void setProperty(final String property, final T value)
    {
        if (!_properties.containsKey(property))
        {
            throw new IllegalArgumentException(String.format("The property '%s' is not defined", property));
        }
        final T oldValue = (T) _properties.get(property);
        if (oldValue != value)
        {
            _properties.put(property, value);
            notifyPropertyChanged(property, oldValue, value);
        }
    }
    
    @SuppressWarnings("unchecked")
    protected <T> T getProperty(final String property)
    {
        if (!_properties.containsKey(property))
        {
            throw new IllegalArgumentException(String.format("The property '%s' is not defined", property));
        }
        return (T) _properties.get(property);
    }
    
    protected void notifyPropertyChanged(final String property, final Object oldValue, final Object newValue)
    {
        for(final INotifyPropertyChanged observer : _observers)
        {
            observer.changed(this);
            observer.changed(this, property);
            observer.changed(this, property, oldValue, newValue);
        }
    }
}
