package domain.util;

public interface INotifyPropertyChanged
{
    void changed(final Model model);
    void changed(final Model model, String property);
    void changed(final Model model, final String property, final Object oldValue, final Object newValue);
}
