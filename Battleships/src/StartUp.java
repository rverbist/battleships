import domain.Board;
import domain.BoardFactory;
import domain.Location;
import domain.PlacedShip;

public class StartUp
{

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        Board board = BoardFactory.getInstance().create(10);
        board.fire(new Location(1, 2));
        board.fire(new Location(3, 2));
        board.fire(new Location(5, 5));
        
        System.out.println(board);
        for(PlacedShip ship : board.getShips())
        {
            System.out.println(ship);
        }
        
    }

}
