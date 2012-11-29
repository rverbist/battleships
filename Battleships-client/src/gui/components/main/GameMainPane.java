package gui.components.main;

import gui.components.grid.BattleShipGrid;
import gui.components.grid.BattleShipGridModel;
import gui.components.grid.BattleShipGridSelectionModel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Label;
import java.awt.Point;
import java.util.HashSet;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;

import domain.Board;
import domain.Location;
import domain.MapSlot;
import domain.PlacedShip;
import domain.Player;

import rmi.client.ClientGameController;
import rmi.client.events.ClientGameEventListenerAdapter;

/**
 * @author rverbist
 */
@SuppressWarnings("serial")
public final class GameMainPane extends JPanel implements BattleShipGridSelectionModel.Listener
{
    private final static int TEAM_COUNT = 2;

    private final ClientGameController _controller;

    private final Label[] _lblTeam;
    private final BattleShipGridModel[] _gridTeamModel;
    private final BattleShipGrid[] _gridTeam;

    public GameMainPane(final ClientGameController controller)
    {
        _controller = controller;

        _lblTeam = new Label[TEAM_COUNT];
        _gridTeamModel = new BattleShipGridModel[TEAM_COUNT];
        _gridTeam = new BattleShipGrid[TEAM_COUNT];

        for (int team = 0; team < TEAM_COUNT; team++)
        {
            _lblTeam[team] = new Label();
            _lblTeam[team].setAlignment(Label.CENTER);
            _lblTeam[team].setFont(getFont().deriveFont(16f));
            _gridTeamModel[team] = new BattleShipGridModel();
            _gridTeam[team] = new BattleShipGrid(_gridTeamModel[team]);
            _gridTeam[team].getLayoutModel().setSelectionColor(_gridTeam[team].getLayoutModel().getGridLineColor());
            _gridTeam[team].setBorder(BorderFactory.createMatteBorder(7, 1, 7, 1, Color.darkGray));
        }

        final GroupLayout layout = new GroupLayout(this);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(Alignment.CENTER).addComponent(_lblTeam[0]).addComponent(_gridTeam[0]))
                .addGroup(layout.createParallelGroup(Alignment.CENTER).addComponent(_lblTeam[1]).addComponent(_gridTeam[1])));
        layout.setVerticalGroup(layout.createParallelGroup(Alignment.CENTER).addGroup(layout.createSequentialGroup().addComponent(_lblTeam[0]).addComponent(_gridTeam[0]))
                .addGroup(layout.createSequentialGroup().addComponent(_lblTeam[1]).addComponent(_gridTeam[1])));
        layout.linkSize(_gridTeam[0], _gridTeam[1]);
        this.setLayout(layout);

        controller.getEventController().addListener(new ClientGameEventListenerAdapter()
        {
            @Override
            public void onConnected(final Player player)
            {
                for (int id = 0; id < TEAM_COUNT; id++)
                {
                    _lblTeam[id].setText(controller.getTeamName(id));
                }
            }

            @Override
            public void onGameStart(final int team, final Board board)
            {
                for (int id = 0; id < TEAM_COUNT; id++)
                {
                    _gridTeamModel[id].setWidth(board.getDimension());
                    _gridTeamModel[id].setHeight(board.getDimension());
                    _gridTeamModel[id].setShips(new HashSet<PlacedShip>());
                    _gridTeamModel[id].clearHits();
                    _gridTeamModel[id].clearMisses();
                    _gridTeamModel[id].clearSuggestions();
                    _gridTeam[id].getLayoutModel().setHighlightColor(controller.getPlayer().getColor());
                    _gridTeam[id].getLayoutModel().setGridHighlightLineColor(controller.getPlayer().getColor());
                }
                _gridTeamModel[team].setShips(board.getShips());
            }

            @Override
            public void onGameTurnStart(final int turn)
            {
                for (int id = 0; id < TEAM_COUNT; id++)
                {
                    if (turn == controller.getTeam())
                    {
                        if (turn != id)
                        {
                            _gridTeam[id].getSelectionModel().addListener(GameMainPane.this);
                            _gridTeam[id].getSelectionModel().setAllowSelection(true);
                        }
                    }
                    _lblTeam[id].setFont(getFont().deriveFont(16f));
                }
                _lblTeam[turn].setFont(getFont().deriveFont(22f).deriveFont(Font.BOLD));
            }

            @Override
            public void onPlayerAddSuggestion(final Player player, final Location location)
            {
                for (int id = 0; id < TEAM_COUNT; id++)
                {
                    if (id != controller.getTeam())
                    {
                        _gridTeamModel[id].addSuggestion(player, location);
                    }
                }
            }

            @Override
            public void onPlayerRemoveSuggestion(final Player player)
            {
                for (int id = 0; id < TEAM_COUNT; id++)
                {
                    if (id != controller.getTeam())
                    {
                        _gridTeamModel[id].removeSuggestion(player);
                    }
                }
            }

            @Override
            public void onGameTurnEnd(final int turn, final MapSlot slot, final Location location)
            {
                for (int id = 0; id < TEAM_COUNT; id++)
                {
                    _gridTeam[id].getSelectionModel().setAllowSelection(false);
                    _gridTeam[id].getSelectionModel().clearListeners();
                    if (id != turn)
                    {
                        _gridTeamModel[id].clearSuggestions();
                        if (slot == MapSlot.Hit)
                        {
                            _gridTeamModel[id].addHit(location);
                        }
                        else if (slot == MapSlot.Miss)
                        {
                            _gridTeamModel[id].addMiss(location);
                        }
                    }
                }
            }
        });
    }

    @Override
    public void selected(BattleShipGridSelectionModel model, Point point)
    {
        _controller.addSuggestion(new Location((int) point.getY(), (int) point.getX()));
    }

    @Override
    public void unselected(BattleShipGridSelectionModel model, Point point)
    {
        _controller.removeSuggestion();
    }
}
