package rmi.client;

import java.io.Closeable;
import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import rmi.RmiClient;
import rmi.RmiServer;
import rmi.server.IServerController;

/**
 * an abstract implementation of a java rmi battleships client
 * @author rverbist
 */
public abstract class RmiClientController implements IClientController, Closeable
{
    protected final RmiServer<IClientController> _client;
    protected RmiClient<IServerController> _server;

    public RmiClientController(final String host, final int port) throws IOException, AlreadyBoundException
    {
        final String resource = String.format("battleships-client-%s", new Date().getTime());
        _client = new RmiServer<IClientController>(this, host, port, resource);
        _client.bind();
        getLogger().log(Level.INFO, String.format("Client created on %s", _client));
    }

    /**
     * connects to the server at the given host:port as a player with the given name
     * @param host the host name of the server
     * @param port the port of the server
     * @param name the name of the player to connect as
     * @throws IOException if the java rmi service could not be started
     * @throws NotBoundException if the java rmi service is not running on the server
     */
    public void connect(final String host, final int port, final String name) throws IOException, NotBoundException
    {
        _server = new RmiClient<IServerController>(host, port, "battleships-server");
        _server.bind();
        getLogger().log(Level.INFO, String.format("Client connected to server on %s", _server));
        
        _server.getInterface().connect(_client.getHost(), _client.getPort(), _client.getResource(), name);
        getLogger().log(Level.INFO, String.format("Client requesting identity %s", name));
    }
    
    /**
     * gets the client interface
     * @return the interface exposed by the client
     */
    public final RmiServer<IClientController> getClient()
    {
        return _client;
    }
    
    /**
     * gets the server interface
     * @return the interface exposed by the server
     */
    public final RmiClient<IServerController> getServer() 
    {
        return _server;
    }
    
    protected final Logger getLogger()
    {
        return Logger.getLogger(RmiClientController.class.getName());
    }

    /* (non-Javadoc)
     * @see java.io.Closeable#close()
     */
    @Override
    public void close() throws IOException
    {
        _client.close();
    }
}