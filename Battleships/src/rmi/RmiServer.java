package rmi;

import java.io.Closeable;
import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * a server that utilizes java rmi
 * @author rverbist
 * @param <TInterface> the interface that the server will expose through rmi
 */
public final class RmiServer<TInterface extends Remote> extends RmiController<TInterface> implements Closeable
{
    private final TInterface _concrete;
    private final TInterface _proxy;

    /**
     * creates a new server that exposes an interface of type <TInterface> at host:port/resource.
     * @param concrete the concrete implementation of the interface
     * @param host the host name of the server
     * @param port the port of the server
     * @param resource the java rmi resource name
     * @throws IOException if the java rmi service could not be started
     * @throws AlreadyBoundException if the java rmi service is already running
     */
    @SuppressWarnings("unchecked")
    public RmiServer(final TInterface concrete, final String host, final int port, final String resource) throws IOException, AlreadyBoundException
    {
        super(host, port, resource);
        _concrete = concrete;
        _proxy = (TInterface) UnicastRemoteObject.exportObject(_concrete, 0);
    }

    /**
     * starts the server
     * @throws IOException when the java rmi service could not be started
     * @throws AlreadyBoundException when the java rmi is already running
     */
    public void bind() throws IOException, AlreadyBoundException
    {
        final Registry registry = LocateRegistry.getRegistry(getHost(), getPort());
        registry.bind(getResource(), _proxy);
    }

    /**
     * starts the server, overriding any previous servers with the same name
     * @throws IOException when the java rmi service could not be started
     */
    public void rebind() throws IOException
    {
        final Registry registry = LocateRegistry.getRegistry(getHost(), getPort());
        registry.rebind(getResource(), _proxy);
    }

    /**
     * stops the server
     * @throws IOException when the java rmi service could not be stopped
     * @throws NotBoundException when the java rmi service is not running
     */
    public void unbind() throws IOException, NotBoundException
    {
        final Registry registry = LocateRegistry.getRegistry(getHost(), getPort());
        registry.unbind(getResource());
    }

    /* (non-Javadoc)
     * @see rmi.RmiController#getInterface()
     */
    @Override
    public TInterface getInterface()
    {
        return _concrete;
    }

    /* (non-Javadoc)
     * @see java.io.Closeable#close()
     */
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
