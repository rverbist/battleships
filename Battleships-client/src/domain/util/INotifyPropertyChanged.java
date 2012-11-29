package domain.util;

/**
 * @author rverbist
 */
public interface INotifyPropertyChanged
{
    /**
     * indicates that the model has changed.
     * @param model the model that raised the event
     */
    void changed(final Model model);
    /**
     * indicates that the model has changed.
     * @param model the model that raised the event
     * @param property the property that has changed
     */
    void changed(final Model model, String property);
    /**
     * indicates that the model has changed.
     * @param model the model that raised the event
     * @param property the property that has changed
     * @param oldValue the old value of the property
     * @param newValue the new value of the property
     */
    void changed(final Model model, final String property, final Object oldValue, final Object newValue);
}
