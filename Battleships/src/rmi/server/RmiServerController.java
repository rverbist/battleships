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

    @Override
    public void close() throws IOException
    {
        _server.close();
    }
    
    protected final IClientController getClient(final Player player)
    {
        return _clients.get(player).getInterface();
    }
    
    protected final IServerController getServer() 
    {
        return _server.getInterface();
    }
    
    protected final Logger getLogger()
    {
        return Logger.getLogger(RmiServerController.class.getName());
    }

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
                client.unbind();
                
                getLogger().log(Level.INFO, String.format("%s disconnected.", client));
            }
        }
        catch (RemoteException ex)
        {
            getLogger().log(Level.SEVERE, null, ex);
        }
    }
    
    protected abstract Player createPlayer(final String name);
    protected abstract void removePlayer(final Player player);
}
