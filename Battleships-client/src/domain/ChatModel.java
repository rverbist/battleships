package domain;

import java.util.LinkedList;
import java.util.List;

import rmi.client.ClientGameController;
import domain.util.Model;

/**
 * a model for a chat application
 * @author rverbist
 */
public abstract class ChatModel extends Model
{
    protected final ClientGameController _controller;

    protected ChatModel(final ClientGameController controller)
    {
        _controller = controller;
        addProperty("messages", new LinkedList<String>());
    }

    /**
     * sends a formatted chat message
     * @param format the format string
     * @param args the arguments
     */
    public void sendChatMessage(final String format, final String... args)
    {
        sendChatMessage(String.format(format, (Object[]) args));
    }

    /**
     * sends a chat message
     * @param message the message to send
     */
    public abstract void sendChatMessage(final String message);
    
    /**
     * gets all messages
     * @return a mutable list of all messages in the model
     */
    public List<String> getMessages()
    {
        return getProperty("messages");
    }
    
    protected void setMessages(final List<String> messages)
    {
        setProperty("messages", messages);
    }
    
    protected void addChatMessage(final String message)
    {
        final List<String> messages = getMessages();
        messages.add(message);
        notifyPropertyChanged("messages", messages, messages);
    }
}
