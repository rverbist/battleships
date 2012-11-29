package domain;

import rmi.client.ClientGameController;
import rmi.client.events.ClientGameEventListenerAdapter;

/**
 * a model for a chat application
 * @author rverbist
 */
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
                addChatMessage(message);
            }
        });
    }
    
    /* (non-Javadoc)
     * @see domain.ChatModel#sendChatMessage(java.lang.String)
     */
    public void sendChatMessage(final String message)
    {
        _controller.sendChatMessage(message);
    }
}
