package gui.components;

import gui.components.main.GameMainPane;
import gui.components.main.LobbyMainPane;
import gui.components.main.LoginMainPane;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout.Alignment;

import domain.Player;
import domain.Board;

import rmi.client.ClientGameController;
import rmi.client.events.ClientGameEventListenerAdapter;

/**
 * @author rverbist
 */
@SuppressWarnings("serial")
public final class MainPane extends JPanel
{
    public final GroupLayout _layout;
    public final JPanel _loginPaneContainer;
    public final JPanel _lobbyPaneContainer;
    public final JPanel _gamePaneContainer;
    
    public final LoginMainPane _loginPane;
    public final LobbyMainPane _lobbyPane;
    public final GameMainPane _gamePane;
    public final ChatPane _chatPane;
    public final StatusPane _namePane;
    public final SidePane _sidePane;

    public MainPane(final ClientGameController controller)
    {
        _loginPane = new LoginMainPane(controller);
        _lobbyPane = new LobbyMainPane(controller);
        _gamePane = new GameMainPane(controller);
        _chatPane = new ChatPane(controller);
        _chatPane.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.darkGray));
        _namePane = new StatusPane(controller);
        _namePane.setBorder(BorderFactory.createMatteBorder(0, 2, 2, 0, Color.darkGray));
        _sidePane = new SidePane(controller);
        _sidePane.setBorder(BorderFactory.createMatteBorder(0, 2, 0, 0, Color.darkGray));

        _loginPaneContainer = new JPanel();
        _loginPaneContainer.add(_loginPane);
        _lobbyPaneContainer = new JPanel();
        _lobbyPaneContainer.add(_lobbyPane);
        _gamePaneContainer = new JPanel();
        _gamePaneContainer.add(_gamePane);
        
        _layout = new GroupLayout(this);
        _layout.setHorizontalGroup(_layout.createSequentialGroup()
                .addGroup(_layout.createParallelGroup(Alignment.CENTER)
                        .addComponent(_loginPaneContainer, 0, 1024, Short.MAX_VALUE)
                        .addComponent(_chatPane))
                .addGroup(_layout.createParallelGroup()
                        .addComponent(_namePane)
                        .addComponent(_sidePane, 250, 250, 250)));
        _layout.setVerticalGroup(_layout.createParallelGroup()
                .addGroup(_layout.createSequentialGroup()
                        .addComponent(_loginPaneContainer, 0, 575, Short.MAX_VALUE)
                        .addComponent(_chatPane, 250, 250, 250))
                .addGroup(_layout.createSequentialGroup()
                        .addComponent(_namePane, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(_sidePane)));
        _layout.linkSize(SwingConstants.HORIZONTAL, _namePane, _sidePane);
        this.setLayout(_layout);

        controller.getEventController().addListener(new ClientGameEventListenerAdapter()
        {
            @Override
            public void onDisconnected(final Player player)
            {
                try { _layout.replace(_lobbyPaneContainer, _loginPaneContainer); }
                catch(IllegalArgumentException e) {}
                try { _layout.replace(_gamePaneContainer, _loginPaneContainer); }
                catch(IllegalArgumentException e) {}
            }

            @Override
            public void onConnected(final Player player)
            {
                try { _layout.replace(_loginPaneContainer, _lobbyPaneContainer); }
                catch(IllegalArgumentException e) {}
                try { _layout.replace(_gamePaneContainer, _lobbyPaneContainer); }
                catch(IllegalArgumentException e) {}
            }

            @Override
            public void onGameStart(final int team, final Board board)
            {
                try { _layout.replace(_loginPaneContainer, _gamePaneContainer); }
                catch(IllegalArgumentException e) {}
                try { _layout.replace(_lobbyPaneContainer, _gamePaneContainer); }
                catch(IllegalArgumentException e) {}
            }
            
            @Override
            public void onGameEnd(final int winner)
            {
                // just return to lobby
                try { _layout.replace(_loginPaneContainer, _lobbyPaneContainer); }
                catch(IllegalArgumentException e) {}
                try { _layout.replace(_gamePaneContainer, _lobbyPaneContainer); }
                catch(IllegalArgumentException e) {}
            }
        });
    }
}
