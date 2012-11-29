package domain.util;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * an abstract model implementation
 * @author rverbist
 */
public abstract class Model
{
    public final Set<INotifyPropertyChanged> _observers;
    public final Map<String, Object> _properties;
    
    /**
     * creates a new model without any observers and properties
     */
    protected Model()
    {
        _observers = new HashSet<INotifyPropertyChanged>();
        _properties = new TreeMap<String, Object>();
    }
    
    /**
     * adds an observer
     * @param observer the observer
     */
    public void addObserver(final INotifyPropertyChanged observer)
    {
        _observers.add(observer);
    }
    
    /**
     * removes an observer
     * @param observer the observer
     */
    public void removeObserver(final INotifyPropertyChanged observer)
    {
        _observers.remove(observer);
    }
    
    /**
     * clears all observers from the model
     */
    public void clearObservers()
    {
        _observers.clear();
    }
    
    /**
     * adds a property to the model
     * @param property the name of the property
     * @param value the initial value of the property
     */
    protected <T> void addProperty(final String property, final T value)
    {
        if (_properties.containsKey(property))
        {
            throw new IllegalArgumentException(String.format("The property '%s' is already defined", property));
        }
        _properties.put(property, value);
    }
    
    /**
     * sets a property in the model
     * @param property the name of the property
     * @param value the new value of the property
     */
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
    
    /**
     * gets a property
     * @param property the name of the property
     * @return the value of the property
     */
    @SuppressWarnings("unchecked")
    protected <T> T getProperty(final String property)
    {
        if (!_properties.containsKey(property))
        {
            throw new IllegalArgumentException(String.format("The property '%s' is not defined", property));
        }
        return (T) _properties.get(property);
    }
    
    /**
     * raises the {@link INotifyPropertyChanged#changed(Model)} method of all registered observers
     * @param property the name of the property that has changed
     * @param oldValue the old value of the property
     * @param newValue the new value of the property
     */
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
