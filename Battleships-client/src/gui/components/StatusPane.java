package gui.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;

import javax.swing.JPanel;

import rmi.client.ClientGameController;
import rmi.client.events.ClientGameEventListenerAdapter;
import domain.Player;

/**
 * @author rverbist
 */
@SuppressWarnings("serial")
public final class StatusPane extends JPanel
{
    public final Label _lblName;
    public final Label _lblHost;
    
    public StatusPane(final ClientGameController controller)
    {
        _lblName = new Label("OFFLINE");
        _lblName.setAlignment(Label.CENTER);
        _lblName.setFont(getFont().deriveFont(28f).deriveFont(Font.BOLD));
        _lblHost = new Label("server is not available");
        _lblHost.setAlignment(Label.CENTER);
        
        add(_lblName);
        add(_lblHost);
        setLayout(new GridLayout(0, 1, 0, 0));
        
        controller.getEventController().addListener(new ClientGameEventListenerAdapter() 
        {
            @Override
            public void onConnected(final Player player)
            {
                _lblName.setText(player.getName().toUpperCase());
                _lblName.setForeground(player.getColor());
                _lblHost.setText(String.format("%s:%s", controller.getServer().getHost(), controller.getServer().getPort()));
            }
            
            @Override
            public void onDisconnected(final Player player)
            {
                _lblName.setText("OFFLINE");
                _lblName.setForeground(Color.black);
                _lblHost.setText("server is not available");
            }
        });
    }
}