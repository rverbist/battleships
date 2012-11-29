package domain;

/**
 * indicates the status of a board slot
 * @author rverbist
 */
public enum MapSlot
{
    /**
     * nothing has been done with this slot
     */
    None,
    /**
     * a missile has caused a miss on this slot
     */
    Miss,
    /**
     * a missile has caused a hit on this slot
     */
    Hit
}