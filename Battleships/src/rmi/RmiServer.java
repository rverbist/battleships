package rmi;

import java.io.Closeable;
import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public final class RmiServer<TInterface extends Remote> extends RmiController<TInterface> implements Closeable
{
    private final TInterface _concrete;
    private final TInterface _proxy;

    @SuppressWarnings("unchecked")
    public RmiServer(final TInterface concrete, final String host, final int port, final String resource) throws IOException, AlreadyBoundException
    {
        super(host, port, resource);
        _concrete = concrete;
        _proxy = (TInterface) UnicastRemoteObject.exportObject(_concrete, 0);
    }

    public void bind() throws IOException, AlreadyBoundException
    {
        final Registry registry = LocateRegistry.getRegistry(getHost(), getPort());
        registry.bind(getResource(), _proxy);
    }

    public void rebind() throws IOException
    {
        final Registry registry = LocateRegistry.getRegistry(getHost(), getPort());
        registry.rebind(getResource(), _proxy);
    }

    public void unbind() throws IOException, NotBoundException
    {
        final Registry registry = LocateRegistry.getRegistry(getHost(), getPort());
        registry.unbind(getResource());
    }

    @Override
    public TInterface getInterface()
    {
        return _concrete;
    }

    @Override
    public void close() throws IOException
    {
        try
        {
            unbind();
        }
        catch (NotBoundException e)
        {
            throw new IOException("An error has occured while closing the connection.", e);
        }
    }
}
