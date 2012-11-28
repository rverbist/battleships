package domain;

import java.util.LinkedList;
import java.util.List;

import rmi.client.ClientGameController;
import domain.util.Model;

public abstract class ChatModel extends Model
{
    protected final ClientGameController _controller;

    protected ChatModel(final ClientGameController controller)
    {
        _controller = controller;
        addProperty("messages", new LinkedList<String>());
    }

    public void sendChatMessage(final String format, final String... args)
    {
        sendChatMessage(String.format(format, (Object[]) args));
    }

    public abstract void sendChatMessage(final String message);
    
    public List<String> getMessages()
    {
        return getProperty("messages");
    }
    
    protected void setMessages(final List<String> messages)
    {
        setProperty("messages", messages);
    }
}
