package domain;

import rmi.client.ClientGameController;
import rmi.client.events.ClientGameEventListenerAdapter;

/**
 * a model for a chat application
 * @author rverbist
 */
public class TeamChatModel extends ChatModel
{
    public TeamChatModel(final ClientGameController controller)
    {
        super(controller);
        _controller.getEventController().addListener(new ClientGameEventListenerAdapter()
        {
            @Override
            public void onTeamChatMessage(final String message)
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
        _controller.sendTeamChatMessage(message);
    }
}
