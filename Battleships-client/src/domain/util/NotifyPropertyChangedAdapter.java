package domain.util;

/**
 * an adapter for {@link INotifyPropertyChanged}
 * @author rverbist
 */
public class NotifyPropertyChangedAdapter implements INotifyPropertyChanged
{
    @Override
    public void changed(Model model)
    {
    }

    @Override
    public void changed(Model model, String property)
    { 
    }

    @Override
    public void changed(Model model, String property, Object oldValue, Object newValue)
    {
        
    }
}
