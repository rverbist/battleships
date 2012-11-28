package gui.components.grid;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JComponent;

import domain.Location;
import domain.PlacedShip;
import domain.Player;
import domain.util.Model;
import domain.util.NotifyPropertyChangedAdapter;

@SuppressWarnings("serial")
public class BattleShipGrid extends JComponent implements MouseListener, MouseMotionListener, BattleShipGridSelectionModel.Listener
{
    private final BattleShipGridModel _model;
    private final BattleShipGridLayoutModel _layout;
    private final BattleShipGridSelectionModel _selection;
    private final int DefaultBlockSize = 50;

    public BattleShipGrid(final BattleShipGridModel model)
    {
        _model = model;
        _model.addObserver(new NotifyPropertyChangedAdapter()
        {
            @Override
            public void changed(Model model)
            {
                repaint();
            }
        });
        _layout = new BattleShipGridLayoutModel();
        _layout.addObserver(new NotifyPropertyChangedAdapter()
        {
            @Override
            public void changed(Model model)
            {
                repaint();
            }
        });
        _selection = new BattleShipGridSelectionModel();
        _selection.addListener((BattleShipGridSelectionModel.Listener) this);
        setBackground(Color.white);
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public BattleShipGridModel getModel()
    {
        return _model;
    }

    public BattleShipGridLayoutModel getLayoutModel()
    {
        return _layout;
    }

    public BattleShipGridSelectionModel getSelectionModel()
    {
        return _selection;
    }

    @Override
    public Dimension getPreferredSize()
    {
        return new Dimension(_model.getWidth() * (DefaultBlockSize), _model.getHeight() * (DefaultBlockSize));
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        // calculate some control contraints
        final Insets insets = getInsets();
        final int x = insets.left;
        final int y = insets.top;
        final int width = getWidth() - insets.left - insets.right;
        final int height = getHeight() - insets.bottom - insets.top;
        final int blockWidth = (width + _model.getWidth() - 1) / _model.getWidth();
        final int blockHeight = (height + _model.getHeight() - 1) / _model.getHeight();

        // draw the background
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());

        // draw the base grid
        g.setColor(_layout.getGridLineColor());
        for (int row = 0; row <= _model.getHeight(); row++)
        {
            g.drawLine(x, y + row * blockHeight, x + width, y + row * blockHeight);
        }
        for (int column = 0; column <= _model.getWidth(); column++)
        {
            g.drawLine(x + column * blockWidth, y, x + column * blockWidth, y + height);
        }

        // draw selected blocks, if any
        for (final Point selection : _selection.getSelection())
        {
            if (selection != InvalidPoint)
            {
                // draw selection rectangle fill
                g.setColor(_layout.getSelectionColor());
                g.drawRect(x + selection.x * blockWidth, y + selection.y * blockHeight, blockWidth, blockHeight);
                // draw selection rectangle border, if required
                if (_layout.getShowSelectionGridLines())
                {
                    g.setColor(_layout.getGridSelectionLineColor());
                    g.drawLine(x, y + selection.y * blockHeight + blockHeight / 2, x + width, y + selection.y * blockHeight + blockHeight / 2);
                    g.drawLine(x + selection.x * blockWidth + blockWidth / 2, y, x + selection.x * blockWidth + blockWidth / 2, y + height);
                }
            }
        }

        // draw ships, if any
        final int widthOffset = (int)(blockWidth / 8);
        final int heightOffset = (int)(blockHeight / 8);
        for (final PlacedShip ship : _model.getShips())
        {
            for (final Location location : ship.getLayoutMap())
            {
                g.setColor(ship.getColor());
                g.fillRect(x + location.getColumn() * blockWidth + widthOffset, 
                           y + location.getRow() * blockHeight + heightOffset, 
                           blockWidth - 2 * widthOffset, 
                           blockHeight - 2 * heightOffset);
            }
        }
        
        // draw hits, if any
        for (final Location hit : _model.getHits())
        {
            g.setColor(Color.red);
            final int depth = 10;
            g.fillRect(x + hit.getColumn() * blockWidth + widthOffset + depth, 
                       y + hit.getRow() * blockHeight + heightOffset + depth, 
                       blockWidth - 2 * widthOffset - 2 * depth, 
                       blockHeight - 2 * heightOffset - 2 * depth);
        }
        
        // draw misses, if any
        for (final Location miss : _model.getMisses())
        {
            g.setColor(Color.blue);
            final int depth = 10;
            g.fillRect(x + miss.getColumn() * blockWidth + widthOffset + depth, 
                       y + miss.getRow() * blockHeight + heightOffset + depth, 
                       blockWidth - 2 * widthOffset - 2 * depth, 
                       blockHeight - 2 * heightOffset - 2 * depth);
        }
        
        // draw suggestions, if any
        for (final Map.Entry<Player, Location> suggestion : _model.getSuggestions().entrySet())
        {
            g.setColor(suggestion.getKey().getColor());
            final int depth = 4;
            for(int d = 0; d < depth; d++)
            {
                g.drawRect(x + suggestion.getValue().getColumn() * blockWidth + d, 
                           y + suggestion.getValue().getRow() * blockHeight + d, 
                           blockWidth - 2 * d, 
                           blockHeight - 2 * d);
            }
        }

        // draw highlighted block, if any
        if (HighlightedBlock != InvalidPoint)
        {
            // draw highlight rectangle border
            g.setColor(_layout.getHighlightColor());
            g.drawRect(x + HighlightedBlock.x * blockWidth, y + HighlightedBlock.y * blockHeight, blockWidth, blockHeight);
            // draw highlight rectangle grid, if required
            if (_layout.getShowHighlightGridLines())
            {
                g.setColor(_layout.getGridHighlightLineColor());
                g.drawLine(x, y + HighlightedBlock.y * blockHeight + blockHeight / 2, x + HighlightedBlock.x * blockWidth, y + HighlightedBlock.y * blockHeight + blockHeight / 2);
                g.drawLine(x + HighlightedBlock.x * blockWidth + blockWidth, y + HighlightedBlock.y * blockHeight + blockHeight / 2, x + width, y + HighlightedBlock.y * blockHeight + blockHeight / 2);
                g.drawLine(x + HighlightedBlock.x * blockWidth + blockWidth / 2, y, x + HighlightedBlock.x * blockWidth + blockWidth / 2, y + HighlightedBlock.y * blockHeight);
                g.drawLine(x + HighlightedBlock.x * blockWidth + blockWidth / 2, y + height, x + HighlightedBlock.x * blockWidth + blockWidth / 2, y + HighlightedBlock.y * blockHeight + blockHeight);
            }
        }
    }

    private void handleHighlighting(final Point block)
    {
        if (!block.equals(HighlightedBlock))
        {
            HighlightedBlock = block;
            repaint();
        }
    }

    private void handleSelection(final Point block)
    {
        if (LastMouseButton == MouseEvent.BUTTON1)
        {
            _selection.select(block);
        }
        else if (LastMouseButton == MouseEvent.BUTTON3)
        {
            _selection.unselect(block);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
        final Point block = GetGridCoordinatesFromMouse(e.getPoint());
        handleHighlighting(block);
        LastMouseButton = e.getButton();
        handleSelection(block);
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        LastMouseButton = e.getButton();
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        LastMouseButton = 0;
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {
        final Point block = GetGridCoordinatesFromMouse(e.getPoint());
        handleHighlighting(block);
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
        final Point block = GetGridCoordinatesFromMouse(e.getPoint());
        handleHighlighting(block);
    }

    @Override
    public void mouseDragged(MouseEvent e)
    {
        final Point block = GetGridCoordinatesFromMouse(e.getPoint());
        handleHighlighting(block);
        handleSelection(block);
    }

    @Override
    public void mouseMoved(MouseEvent e)
    {
        final Point block = GetGridCoordinatesFromMouse(e.getPoint());
        handleHighlighting(block);
    }

    private Point GetGridCoordinatesFromMouse(Point point)
    {
        final Insets insets = getInsets();
        final int width = getWidth() - insets.left - insets.right;
        final int height = getHeight() - insets.bottom - insets.top;
        final int blockWidth = (width + _model.getWidth() - 1) / _model.getWidth();
        final int blockHeight = (height + _model.getHeight() - 1) / _model.getHeight();
        if (insets.left < point.x && point.x < getWidth() - insets.right)
        {
            if (insets.top < point.y && point.y < getHeight() - insets.bottom)
            {
                final int x = (point.x - insets.left) / blockWidth;
                final int y = (point.y - insets.top) / blockHeight;
                return new Point(x, y);
            }
        }
        return InvalidPoint;
    }

    public final Point InvalidPoint = new Point(-1, -1);
    public Point HighlightedBlock = InvalidPoint;
    public int LastMouseButton;

    @Override
    public void selected(BattleShipGridSelectionModel model, Point point)
    {
        repaint();
    }

    @Override
    public void unselected(BattleShipGridSelectionModel model, Point point)
    {
        repaint();
    }
}
