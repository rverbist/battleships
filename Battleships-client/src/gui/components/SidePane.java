package gui.components;

import java.awt.Component;
import java.awt.Font;
import java.awt.Label;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.ListCellRenderer;

import domain.Board;
import domain.Player;

import rmi.client.ClientGameController;
import rmi.client.events.ClientGameEventListenerAdapter;

@SuppressWarnings("serial")
public final class SidePane extends JPanel
{
    private final static int TEAM_COUNT = 2;
    
    private final Label[] _lblTitle;
    private final JProgressBar[] _pbHealth;
    private final DefaultListModel<Player>[] _lsPlayersModel;
    private final JList<Player>[] _lsPlayers;
    
    @SuppressWarnings("unchecked")
    public SidePane(final ClientGameController controller)
    {
        _lblTitle = new Label[TEAM_COUNT];
        _pbHealth = new JProgressBar[TEAM_COUNT];
        _lsPlayersModel = new DefaultListModel[TEAM_COUNT];
        _lsPlayers = new JList[TEAM_COUNT];
        
        for(int team = 0; team < TEAM_COUNT; team++)
        {
            _lblTitle[team] = new Label("Team Name");
            _lblTitle[team].setFont(getFont().deriveFont(22f).deriveFont(Font.BOLD));
            _lblTitle[team].setAlignment(Label.CENTER);
            _lblTitle[team].setVisible(false);
            _pbHealth[team] = new JProgressBar();
            _pbHealth[team].setMinimum(0);
            _pbHealth[team].setValue(100);
            _pbHealth[team].setMaximum(100);
            _pbHealth[team].setVisible(false);
            _lsPlayersModel[team] = new DefaultListModel<Player>();
            _lsPlayers[team] = new JList<Player>(_lsPlayersModel[team]);
            _lsPlayers[team].setOpaque(false);
            _lsPlayers[team].setCellRenderer(new PlayerCellRenderer());
            _lsPlayers[team].setVisible(false);
        }
        
        final Component glue = Box.createVerticalGlue();
        final GroupLayout layout = new GroupLayout(this);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(layout.createParallelGroup(Alignment.CENTER)
                 .addComponent(_lblTitle[0])
                 .addComponent(_pbHealth[0])
                 .addComponent(_lsPlayers[0])
                 .addComponent(_lblTitle[1])
                 .addComponent(_pbHealth[1])
                 .addComponent(_lsPlayers[1])
                 .addComponent(glue));
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(_lblTitle[0], GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(_pbHealth[0], GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(_lsPlayers[0], 75, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(_lblTitle[1], GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(_pbHealth[1], GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(_lsPlayers[1], 75, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(glue));
        this.setLayout(layout);

        controller.getEventController().addListener(new ClientGameEventListenerAdapter() 
        {
            @Override
            public void onConnected(final Player player)
            {
                for(int team = 0; team < TEAM_COUNT; team++)
                {
                    _lblTitle[team].setText(controller.getTeamName(team));
                    _lsPlayersModel[team].clear();
                    for(final Player p : controller.getTeamPlayers(team))
                    {
                        _lsPlayersModel[team].addElement(p);
                    }
                    _lblTitle[team].setVisible(true);
                    _lsPlayers[team].setVisible(true);
                }
            }
            
            @Override
            public void onDisconnected(final Player player)
            {
                for(int team = 0; team < TEAM_COUNT; team++)
                {
                    _pbHealth[team].setVisible(false);
                    _lblTitle[team].setVisible(false);
                    _lsPlayers[team].setVisible(false);
                }
            }

            @Override
            public void onPlayerJoinedTeam(final Player player, final int team)
            {
                int index = _lsPlayersModel[team].indexOf(player);
                if (index == -1)
                {
                    _lsPlayersModel[team].addElement(player);
                }
            }

            @Override
            public void onPlayerLeftTeam(final Player player)
            {
                for(int team = 0; team < TEAM_COUNT; team++)
                {
                    int index = _lsPlayersModel[team].indexOf(player);
                    if (index != -1)
                    {
                        _lsPlayersModel[team].removeElementAt(index);
                    }
                }
            }
            
            @Override
            public void onGameStart(final int team, final Board board)
            {
                for(int id = 0; id < TEAM_COUNT; id++)
                {
                    _pbHealth[id].setVisible(true);
                }
            }

            @Override
            public void onTeamHit(final int team, final int health, final int maximumHealth)
            {
                _pbHealth[team].setValue(health);
                _pbHealth[team].setMaximum(maximumHealth);
            }
        });
    }
    
    private final class PlayerCellRenderer implements ListCellRenderer<Player>
    {
        @Override
        public Component getListCellRendererComponent(JList<? extends Player> list, Player value, int index, boolean isSelected, boolean cellHasFocus)
        {
            final JLabel label = new JLabel();
            label.setFont(label.getFont().deriveFont(18f));
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setText(value.getName());
            label.setForeground(value.getColor());
            label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            return label;
        }
    }
}