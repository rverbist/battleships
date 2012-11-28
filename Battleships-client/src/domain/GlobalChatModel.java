package domain;

import java.util.LinkedList;
import java.util.List;

import rmi.client.ClientGameController;
import rmi.client.events.ClientGameEventListenerAdapter;

public class GlobalChatModel extends ChatModel
{
    public GlobalChatModel(final ClientGameController controller)
    {
        super(controller);
        _controller.getEventController().addListener(new ClientGameEventListenerAdapter()
        {
            @Override
            public void onGlobalChatMessage(final String message)
            {
                // note
                // this is very far from optimal, performance and memory consumption
                // might get way out of hand with huge chat buffers! 
                // TODO: use an immutable linked list
                final List<String> messages = new LinkedList<String>(getMessages());
                messages.add(message);
                setMessages(messages);
            }
        });
    }
    
    public void sendChatMessage(final String message)
    {
        _controller.sendChatMessage(message);
    }
}
