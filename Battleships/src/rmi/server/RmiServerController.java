package rmi.server;

import java.io.Closeable;
import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import rmi.RmiClient;
import rmi.RmiServer;
import rmi.client.IClientController;
import domain.Player;

/**
 * an abstract implementation of a java rmi battleships server
 * @author rverbist
 */
public abstract class RmiServerController implements IServerController, Closeable
{
    protected final RmiServer<IServerController> _server;
    protected final Map<Player, RmiClient<IClientController>> _clients;

    public RmiServerController(final String host, final int port) throws IOException, AlreadyBoundException
    {
        final Logger logger = Logger.getLogger(RmiServerController.class.getName());
        
        final String resource = "battleships-server";
        _server = new RmiServer<IServerController>(this, host, port, resource);
        _server.bind();
        _clients = new HashMap<Player, RmiClient<IClientController>>();

        logger.log(Level.INFO, String.format("Server created on %s", _server.getHost()));
    }
    
    /**
     * gets the client interface associated with the given player
     * @param player the player to get the client interface for
     * @return the client interface
     */
    protected final IClientController getClient(final Player player)
    {
        return _clients.get(player).getInterface();
    }
    
    /**
     * gets the server interface
     * @return the server interface
     */
    protected final IServerController getServer() 
    {
        return _server.getInterface();
    }

    /* (non-Javadoc)
     * @see rmi.server.IServerController#connect(java.lang.String, int, java.lang.String, java.lang.String)
     */
    @Override
    public void connect(final String host, final int port, final String resource, final String name)
    {
        try
        {
            final Player player = createPlayer(name);
            final RmiClient<IClientController> client = new RmiClient<IClientController>(host, port, resource);
            _clients.put(player, client);
            
            client.bind();
            client.getInterface().onConnected(player);
            
            getLogger().log(Level.INFO, String.format("%s connected.", client));
        }
        catch (RemoteException ex)
        {
            getLogger().log(Level.SEVERE, null, ex);
        }
        catch (NotBoundException ex)
        {
            getLogger().log(Level.SEVERE, null, ex);
        }
    }

    /* (non-Javadoc)
     * @see rmi.server.IServerController#disconnect(domain.Player)
     */
    @Override
    public void disconnect(final Player player)
    {
        try
        {
            final RmiClient<IClientController> client =  _clients.remove(player);
            if (client != null)
            {
                removePlayer(player);
                client.getInterface().onDisconnected(player);
                
                getLogger().log(Level.INFO, String.format("%s disconnected.", client));
            }
        }
        catch (RemoteException ex)
        {
            getLogger().log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * creates a player with the given name
     * template method
     * @param name the name of the player
     * @return the player object
     */
    protected abstract Player createPlayer(final String name);
    
    /**
     * removes a player
     * template method
     * @param player the player to remove
     */
    protected abstract void removePlayer(final Player player);
    
    protected final Logger getLogger()
    {
        return Logger.getLogger(RmiServerController.class.getName());
    }

    @Override
    public void close() throws IOException
    {
        _server.close();
    }
}
