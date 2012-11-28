import java.io.IOException;
import java.rmi.AlreadyBoundException;

import rmi.server.ServerGameController;

public class StartUpServer
{
    public static void main(String[] args) throws IOException
    {
        try
        {
            final ServerGameController controller = new ServerGameController("localhost", 1099);
        
            System.out.println("Press any key to close...");
            System.in.read();
            controller.close();
            System.exit(0);
        }
        catch (IOException e)
        {
            System.err.println("An error has occured while starting the server");
        }
        catch (AlreadyBoundException e)
        {
            System.err.println("Unable to start the server, another server is already running. Only one instance of this server may be active at any given time.");
        }
    }
}
