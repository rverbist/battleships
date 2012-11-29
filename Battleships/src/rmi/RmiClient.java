package rmi;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


/**
 * a client that utilizes java rmi
 * @author rverbist
 * @param <TInterface> the interface that the client will expose through rmi
 */
public final class RmiClient<TInterface extends Remote> extends RmiController<TInterface>
{
    private TInterface _proxy;

    /**
     * creates a new client that will connect to a java rmi service at host:port/resource
     * @param host the host name to connect to
     * @param port the port to connect to
     * @param resource the java rmi resource name to connect to
     */
    public RmiClient(final String host, final int port, final String resource)
    {
        super(host, port, resource);
    }

    /**
     * connects to the server
     * @throws RemoteException when the java rmi service could not be started
     * @throws NotBoundException when the java rmi service is not running on the host
     */
    @SuppressWarnings("unchecked")
    public void bind() throws RemoteException, NotBoundException
    {
        final Registry registry = LocateRegistry.getRegistry(getHost(), getPort());
        _proxy = (TInterface) registry.lookup(getResource());
    }
    
    /**
     * disconnects from the server
     * @deprecated
     */
    public void unbind()
    {
    }

    /* (non-Javadoc)
     * @see rmi.RmiController#getInterface()
     */
    @Override
    public TInterface getInterface()
    {
        return _proxy;
    }
}
