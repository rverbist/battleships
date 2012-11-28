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

    public void connect(final String host, final int port, final String name) throws IOException, NotBoundException
    {
        _server = new RmiClient<IServerController>(host, port, "battleships-server");
        _server.bind();
        getLogger().log(Level.INFO, String.format("Client connected to server on %s", _server));
        
        _server.getInterface().connect(_client.getHost(), _client.getPort(), _client.getResource(), name);
        getLogger().log(Level.INFO, String.format("Client requesting identity %s", name));
    }

    @Override
    public void close() throws IOException
    {
        _client.close();
    }
    
    public final RmiServer<IClientController> getClient()
    {
        return _client;
    }
    
    public final RmiClient<IServerController> getServer() 
    {
        return _server;
    }
    
    protected final Logger getLogger()
    {
        return Logger.getLogger(RmiClientController.class.getName());
    }
}