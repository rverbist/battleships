package gui.components.grid;

import java.awt.Color;

import domain.util.Model;

/**
 * @author rverbist
 */
public class BattleShipGridLayoutModel extends Model
{
    public BattleShipGridLayoutModel()
    {
        super();
        addProperty("GridLineColor", Color.lightGray);
        addProperty("SelectionColor", Color.red);
        addProperty("GridSelectionLineColor", Color.red);
        addProperty("HighlightColor", Color.black);
        addProperty("GridHighlightLineColor", Color.black);
        addProperty("ShowHighlight", true);
        addProperty("ShowGridLines", true);
        addProperty("ShowSelectionGridLines", false);
        addProperty("ShowHighlightGridLines", true);
    }

    public void setGridLineColor(final Color value)
    {
        setProperty("GridLineColor", value);
    }

    public void setSelectionColor(final Color value)
    {
        setProperty("SelectionColor", value);
    }

    public void setGridSelectionLineColor(final Color value)
    {
        setProperty("GridSelectionLineColor", value);
    }

    public void setHighlightColor(final Color value)
    {
        setProperty("HighlightColor", value);
    }

    public void setGridHighlightLineColor(final Color value)
    {
        setProperty("GridHighlightLineColor", value);
    }

    public void setShowHighlight(final Boolean value)
    {
        setProperty("ShowHighlight", value);
    }

    public void setShowGridLines(final Boolean value)
    {
        setProperty("ShowGridLines", value);
    }

    public void setShowSelectionGridLines(final Boolean value)
    {
        setProperty("ShowSelectionGridLines", value);
    }

    public void setShowHighlightGridLines(final Boolean value)
    {
        setProperty("ShowHighlightGridLines", value);
    }

    public Color getGridLineColor()
    {
        return getProperty("GridLineColor");
    }

    public Color getSelectionColor()
    {
        return getProperty("SelectionColor");
    }

    public Color getGridSelectionLineColor()
    {
        return getProperty("GridSelectionLineColor");
    }

    public Color getHighlightColor()
    {
        return getProperty("HighlightColor");
    }

    public Color getGridHighlightLineColor()
    {
        return getProperty("GridHighlightLineColor");
    }

    public Boolean getShowHighlight()
    {
        return getProperty("ShowHighlight");
    }

    public Boolean getShowGridLines()
    {
        return getProperty("ShowGridLines");
    }

    public Boolean getShowSelectionGridLines()
    {
        return getProperty("ShowSelectionGridLines");
    }

    public Boolean getShowHighlightGridLines()
    {
        return getProperty("ShowHighlightGridLines");
    }
}
