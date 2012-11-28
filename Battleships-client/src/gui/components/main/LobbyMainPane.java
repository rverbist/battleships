package gui.components.main;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ListCellRenderer;

import domain.Player;

import rmi.client.ClientGameController;
import rmi.client.events.ClientGameEventListenerAdapter;

@SuppressWarnings("serial")
public class LobbyMainPane extends JPanel
{
    private final Label _lblTitle;
    private final Label _lblSubTitle;
    private final Label _lblTeam1;
    private final Label _lblUnassigned;
    private final Label _lblTeam2;
    private final JList<Player> _lsTeam1;
    private final JList<Player> _lsUnassigned;
    private final JList<Player> _lsTeam2;
    private final DefaultListModel<Player> _lsTeam1Model;
    private final DefaultListModel<Player> _lsTeam2Model;
    private final DefaultListModel<Player> _lsUnassignedModel;
    private final JButton _joinTeam1;
    private final JButton _joinTeam2;
    private final JButton _leaveTeam;
    private final JButton _start;
    
    public LobbyMainPane(final ClientGameController controller)
    {
        _lblTitle = new Label("LOBBY");
        _lblTitle.setAlignment(Label.CENTER);
        _lblTitle.setFont(getFont().deriveFont(36f).deriveFont(Font.BOLD));
        
        _lblSubTitle = new Label("select a team");
        _lblSubTitle.setAlignment(Label.CENTER);
        _lblSubTitle.setFont(getFont().deriveFont(32f));
        
        _lblTeam1 = new Label("Team 1");
        _lblTeam1.setFont(getFont().deriveFont(Font.BOLD));
        _lblTeam1.setAlignment(Label.CENTER);
        
        _lsTeam1Model = new DefaultListModel<Player>();
        _lsTeam1 = new JList<Player>(_lsTeam1Model);
        _lsTeam1.setBorder(BorderFactory.createMatteBorder(7, 0, 0, 0, Color.darkGray));
        _lsTeam1.setOpaque(false);
        _lsTeam1.setCellRenderer(new PlayerCellRenderer());
        
        _lblUnassigned = new Label("Unassigned");
        _lblUnassigned.setAlignment(Label.CENTER);
        
        _lsUnassignedModel = new DefaultListModel<Player>();
        _lsUnassigned = new JList<Player>(_lsUnassignedModel);
        _lsUnassigned.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.darkGray));
        _lsUnassigned.setOpaque(false);
        _lsUnassigned.setCellRenderer(new PlayerCellRenderer());
        
        _lblTeam2 = new Label("Team 2");
        _lblTeam2.setFont(getFont().deriveFont(Font.BOLD));
        _lblTeam2.setAlignment(Label.CENTER);
        
        _lsTeam2Model = new DefaultListModel<Player>();
        _lsTeam2 = new JList<Player>(_lsTeam2Model);
        _lsTeam2.setBorder(BorderFactory.createMatteBorder(7, 0, 0, 0, Color.darkGray));
        _lsTeam2.setOpaque(false);
        _lsTeam2.setCellRenderer(new PlayerCellRenderer());
        
        _leaveTeam = new JButton(">");
        _leaveTeam.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.darkGray, 1), BorderFactory.createEmptyBorder(4, 4, 4, 4)));
        _leaveTeam.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                controller.leaveTeam();
            }
        });
        
        _joinTeam1 = new JButton(">");
        _joinTeam1.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.darkGray, 1), BorderFactory.createEmptyBorder(4, 4, 4, 4)));
        _joinTeam1.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                controller.joinTeam(0);
            }
        });
        
        _joinTeam2 = new JButton(">");
        _joinTeam2.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.darkGray, 1), BorderFactory.createEmptyBorder(4, 4, 4, 4)));
        _joinTeam2.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                controller.joinTeam(1);
            }
        });
        
        _start = new JButton("Start");
        _start.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                controller.toggleReady();
            }
        });
        
        final GroupLayout layout = new GroupLayout(this);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(layout.createParallelGroup(Alignment.CENTER)
                .addComponent(_lblTitle)
                .addComponent(_lblSubTitle)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(_leaveTeam) 
                        .addGroup(layout.createParallelGroup()
                                .addComponent(_lblUnassigned)
                                .addComponent(_lsUnassigned, 150, 150, 150))
                        .addComponent(_joinTeam1) 
                        .addGroup(layout.createParallelGroup()
                                .addComponent(_lblTeam1)
                                .addComponent(_lsTeam1, 150, 150, 150))
                        .addComponent(_joinTeam2)   
                        .addGroup(layout.createParallelGroup()
                                .addComponent(_lblTeam2)
                                .addComponent(_lsTeam2, 150, 150, 150)))
                .addComponent(_start));
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(_lblTitle)
                .addComponent(_lblSubTitle)
                .addGroup(layout.createParallelGroup()
                        .addComponent(_leaveTeam) 
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(_lblUnassigned)
                                .addComponent(_lsUnassigned, 50, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE))
                        .addComponent(_joinTeam1) 
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(_lblTeam1)
                                .addComponent(_lsTeam1, 50, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE))
                        .addComponent(_joinTeam2) 
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(_lblTeam2)
                                .addComponent(_lsTeam2, 50, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)))
                                
                    .addComponent(_start));
        layout.linkSize(SwingConstants.VERTICAL, _lsTeam1, _lsUnassigned, _lsTeam2);
        this.setLayout(layout);

        controller.getEventController().addListener(new ClientGameEventListenerAdapter()
        {
            @Override
            public void onConnected(final Player player)
            {
                _lblTeam1.setText(controller.getTeamName(0));
                _lblTeam2.setText(controller.getTeamName(1));
                _lsUnassignedModel.clear();
                for (final Player p : controller.getUnassignedPlayers())
                {
                    _lsUnassignedModel.addElement(p);
                }
                _lsTeam1Model.clear();
                for (final Player p : controller.getTeamPlayers(0))
                {
                    _lsTeam1Model.addElement(p);
                }
                _lsTeam2Model.clear();
                for (final Player p : controller.getTeamPlayers(1))
                {
                    _lsTeam2Model.addElement(p);
                }
            }

            @Override
            public void onDisconnected(final Player player)
            {
                _lsUnassignedModel.clear();
                _lsTeam1Model.clear();
                _lsTeam2Model.clear();
            }

            @Override
            public void onPlayerAssigned(final Player player)
            {
                _lsUnassignedModel.removeElement(player);
            }

            @Override
            public void onPlayerUnassigned(final Player player)
            {
                _lsUnassignedModel.addElement(player);
            }

            @Override
            public void onPlayerJoinedTeam(final Player player, final int team)
            {
                if (team == 0)
                {
                    _lsTeam1Model.addElement(player);
                }
                if (team == 1)
                {
                    _lsTeam2Model.addElement(player);
                }
            }

            @Override
            public void onPlayerLeftTeam(final Player player)
            {
                _lsTeam1Model.removeElement(player);
                _lsTeam2Model.removeElement(player);
            }

            @Override
            public void onPlayerIsReadyChanged(final Player player)
            {
                int index = 0;
                index = _lsTeam1Model.indexOf(player);
                if (index != -1)
                {
                    _lsTeam1Model.set(index, player);
                    return;
                }
                index = _lsUnassignedModel.indexOf(player);
                if (index != -1)
                {
                    _lsUnassignedModel.set(index, player);
                    return;
                }
                index = _lsTeam2Model.indexOf(player);
                if (index != -1)
                {
                    _lsTeam2Model.set(index, player);
                    return;
                }
            }
        });
    }
    
    private final class PlayerCellRenderer implements ListCellRenderer<Player>
    {
        @Override
        public Component getListCellRendererComponent(JList<? extends Player> list, Player value, int index, boolean isSelected, boolean cellHasFocus)
        {
            final JLabel lblReady = new JLabel();
            lblReady.setFont(lblReady.getFont().deriveFont(12f));
            if (value.isReady())
            {
                lblReady.setText("[ready]");
            }
            
            final JLabel lblName = new JLabel();
            lblName.setFont(lblName.getFont().deriveFont(18f));
            lblName.setText(value.getName());
            lblName.setForeground(value.getColor());
            
            final JPanel panel = new JPanel();
            panel.add(lblReady);
            panel.add(lblName);
            panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            return panel;
        }
    }
}
